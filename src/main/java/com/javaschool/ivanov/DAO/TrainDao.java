package com.javaschool.ivanov.DAO;

import com.javaschool.ivanov.Domain.Train;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;


@Stateless
public class TrainDao extends GenericDaoJpaImpl<Train, Integer> {
    public TrainDao() {
        super(Train.class);
    }

    /**
     * get train name by route name
     * @param route - route entity name
     * @return list of trains
     */
    public List<Object> findTrainByRoute(String route)
    {
        try {
            Query query = em.createNativeQuery("select train.name \n" +
                    "from train \n" +
                    "join trip on train.id = trip.train_id \n" +
                    "join route on route.id = trip.route_id \n" +
                    "where route.name = ? " +
                    "group by train.name").setParameter(1, route);
            return query.getResultList();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }
}
