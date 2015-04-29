package com.javaschool.ivanov.DAO;


import com.javaschool.ivanov.DTOs.BuyTicketInfo;
import com.javaschool.ivanov.Domain.Trip;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Stateless
public class TripDao extends GenericDaoJpaImpl<Trip, Integer> {

    public TripDao() {
        super(Trip.class);
    }

    /**
     * get departure date by train name, route name
     * @param train - train entity name
     * @param route - route entity name
     * @return list of departure date
     */
    public List<Object> findDateByTrainAndRoute(String train, String route)
    {
        try {
            Query query = em.createNativeQuery("select trip.departure \n" +
                    "from trip \n" +
                    "join route on route.id = trip.route_id \n" +
                    "join train on train.id = trip.train_id \n" +
                    "where train.name = ? \n" +
                    "and route.name = ? ").setParameter(1, train).setParameter(2, route);
            return query.getResultList();
        }
        catch (NoResultException e)
        {
            return  null;
        }
    }

    /**
     * get trip primary key
     * @param train - train entity name
     * @param route - route entity name
     * @param date - departure date
     * @return trip entity primary key
     */
    public Integer findTripPK(String train, String route, Date date)
    {
        try {
            Query query = em.createNativeQuery("select trip.id \n" +
                    "from trip \n" +
                    "join route on route.id = trip.route_id \n" +
                    "join train on train.id = trip.train_id \n" +
                    "where train.name = ? \n" +
                    "and route.name = ? \n" +
                    "and trip.departure = ? ");
            query.setParameter(1, train);
            query.setParameter(2, route);
            query.setParameter(3, date);
            return (Integer) query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    /**
     * validate buy departure time
     * @param info - client request params
     * @return true, if now time + 10 min < departure time,
     * another false
     */
    public boolean isValidDepartureTime(BuyTicketInfo info)
    {
        Query query = em.createNativeQuery("select from_unixtime(unix_timestamp(trip.departure) + \n" +
                "SUM(CASE WHEN d1.id = d2.id THEN 0 ELSE unix_timestamp(d2.duration)END ))\n" +
                "from train\n" +
                "join trip on train.id=trip.train_id\n" +
                "join route on trip.route_id=route.id\n" +
                "join schedule s1 on s1.route_id= route.id\n" +
                "join direction d1 on d1.id= s1.id\n" +
                "join station on station.id = d1.station_from\n" +
                "join schedule s2 on s2.route_id = route.id\n" +
                "join direction d2 on d2.id = s2.direction_id\n" +
                "where s2.sequence_number <= s1.sequence_number \n" +
                "and station.name = ? \n" +
                "and train.name = ? \n" +
                "and route.name = ? \n" +
                "and trip.departure = ? \n");
        query.setParameter(1, info.getStationFrom());
        query.setParameter(2, info.getTrain());
        query.setParameter(3, info.getRoute());
        query.setParameter(4, info.getDate());
        Timestamp departureTime = (Timestamp)query.getSingleResult();
        departureTime.setTime(departureTime.getTime() + 10*60*1000);
        Timestamp currentTime = (Timestamp)em.createNativeQuery("select CURRENT_TIMESTAMP").getSingleResult();
        System.out.println(departureTime + " "+ currentTime);
        return  currentTime.before(departureTime);
    }

    /**
     *  create trip
     * @param departure - departure date
     * @param trainId - train entity primary key
     * @param routeId - route entity primary key
     */
    public void create(Date departure, int trainId, int routeId) {
        Query query = em.createNativeQuery("insert into trip(departure, train_id, route_id)\n" +
                "value (?, ?, ?)");
        query.setParameter(1, departure);
        query.setParameter(2, trainId);
        query.setParameter(3, routeId);
        query.executeUpdate();
    }

}
