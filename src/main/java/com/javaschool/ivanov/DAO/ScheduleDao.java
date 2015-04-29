package com.javaschool.ivanov.DAO;


import com.javaschool.ivanov.Domain.Schedule;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Stateless
public class ScheduleDao extends GenericDaoJpaImpl<Schedule, Integer> {

    public ScheduleDao() {
        super(Schedule.class);
    }

    private List<Object[]> list;

    /**
     * get schedule by departure station
     * @param station - station name
     * @return list of schedule
     * @throws IndexOutOfBoundsException
     */
    public List<Object[]> loadSchedule(String station)throws IndexOutOfBoundsException
    {
        Query query = em.createNativeQuery("select train.name as trainname, route.name as routename,\n" +
                "from_unixtime(unix_timestamp(trip.departure) +\n" +
                "SUM(case when d1.id = d2.id then 0 else unix_timestamp(d2.duration)end)) as date\n" +
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
                "group by trip.departure").setParameter(1, station);
        list = query.getResultList();

        return list;

    }


    /**
     * get train, route and date by departure station, arrival station and date
     * @param stationFrom - departure station name
     * @param stationTo - arrival station name
     * @param date1 - start date
     * @param date2 - end date
     * @return list of schedule
     * @throws IndexOutOfBoundsException
     */
    public List<Object[]> findTrain(String stationFrom, String stationTo, Date date1, Date date2)throws IndexOutOfBoundsException
    {
        Query query = em.createNativeQuery("select train.name as trainname, route.name as routename, \n" +
                "from_unixtime(unix_timestamp(trip.departure) +\n" +
                "SUM(CASE WHEN d1.id = d3.id THEN 0 ELSE unix_timestamp(d3.duration)END )) as date\n" +
                "from train\n" +
                "join trip on train.id=trip.train_id\n" +
                "join route on trip.route_id=route.id\n" +
                "join schedule s1 on s1.route_id= route.id\n" +
                "join direction d1 on d1.id= s1.id\n" +
                "join station stationFrom on stationFrom.id = d1.station_from\n" +
                "join schedule s2 on s2.route_id = route.id\n" +
                "join direction d2 on d2.id = s2.direction_id\n" +
                "join station stationTo on stationTo.id = d2.station_to\n" +
                "join schedule s3 on s3.route_id = route.id\n" +
                "join direction d3 on d3.id = s3.direction_id\n" +
                "where s2.sequence_number >= s1.sequence_number \n" +
                "and s3.sequence_number <= s1.sequence_number \n" +
                "and stationFrom.name = ? \n" +
                "and stationTo.name = ? \n" +
                "group by trip.departure \n" +
                "having date > ?  \n" +
                "and date < ?  ");

        query.setParameter(1, stationFrom);
        query.setParameter(2, stationTo);
        query.setParameter(3, date1);
        query.setParameter(4, date2);
        list = query.getResultList();

        return list;

    }


    /**
     * get all train
     * @return list of all trains
     * @throws IndexOutOfBoundsException
     */
    public List<Object[]> loadAllTrains() throws IndexOutOfBoundsException
    {
        Query query = em.createNativeQuery("select train.name as trainname, route.name as routename, \n" +
                "trip.departure as tripdate \n" +
                "from train \n" +
                "join trip on train.id=trip.train_id \n" +
                "join route on trip.route_id=route.id \n" +
                "order by tripdate");

        list = query.getResultList();
        return list;
    }

    /**
     * get schedule id by sequence number, route Id and direction Id
     * @param sequenceNumber
     * @param routeId - primary key route entity
     * @param directionId - primary key direction entity
     * @return primary key schedule entity
     */
    public Integer find(int sequenceNumber, int routeId,int directionId)
    {
        try {
            Query query = em.createNativeQuery("select id " +
                    "from schedule " +
                    "where sequence_number = ? " +
                    "and route_id = ? " +
                    "and direction_id = ? ");
            query.setParameter(1, sequenceNumber);
            query.setParameter(2, routeId);
            query.setParameter(3, directionId);
            return (Integer) query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return  null;
        }
    }

}
