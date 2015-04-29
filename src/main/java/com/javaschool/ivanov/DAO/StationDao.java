package com.javaschool.ivanov.DAO;

import com.javaschool.ivanov.Domain.Station;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;


@Stateless
public class StationDao extends GenericDaoJpaImpl<Station, Integer> {
    public StationDao() {
        super(Station.class);
    }

    /**
     * get departure station name by route name
     * @param route - route entity name
     * @return list of stations
     */
    public List<Object> findStationsFromByRoute(String route)
    {
        Query query = em.createNativeQuery("select station.name " +
                "from station " +
                "join direction on station.id = direction.station_from " +
                "join schedule on direction.id = schedule.direction_id " +
                "join route on route.id = schedule.route_id " +
                "where route.name = ? ").setParameter(1, route);
        return query.getResultList();
    }

    /**
     * get arrival station name by route name
     * @param route - route entity name
     * @return list of stations
     */
    public List<Object> findStationsToByRoute(String route)
    {
        Query query = em.createNativeQuery("select station.name " +
                "from station " +
                "join direction on station.id = direction.station_to " +
                "join schedule on direction.id = schedule.direction_id " +
                "join route on route.id = schedule.route_id " +
                "where route.name = ? ").setParameter(1, route);
        return query.getResultList();
    }
}
