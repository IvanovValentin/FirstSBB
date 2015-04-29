package com.javaschool.ivanov.DAO;


import com.javaschool.ivanov.Domain.Direction;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Time;
import java.util.Date;

@Stateless
public class DirectionDao extends GenericDaoJpaImpl<Direction, Integer> {
    public DirectionDao() {
        super(Direction.class);
    }

    /**
     *
     * @param stationFrom - departure station name
     * @param stationTo
     * @param duration
     * @return
     */
    public Integer find(String stationFrom,String stationTo, Date duration)
    {
        try {
            Query query = em.createNativeQuery("select direction.id " +
                    "from direction " +
                    "where station_from = ? " +
                    "and station_to = ? " +
                    "and duration = ?");
            query.setParameter(1, stationFrom);
            query.setParameter(2, stationTo);
            query.setParameter(3, duration);
            return (Integer) query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }
}
