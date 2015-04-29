package com.javaschool.ivanov.DAO;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Stateless
public class GenericDaoJpaImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    @PersistenceContext(unitName = "sbb")
    protected EntityManager em;

    private Class<T> type;

    public GenericDaoJpaImpl(){}
    public GenericDaoJpaImpl(Class<T> type){
        this.type = type;
    }


    /**
     *
     * @return type entity
     */
    public Class<T> getType() {
        return type;
    }

    /**
     *
     * @param t - entity
     * @return entity
     */
    @Override
    public T create(final T t){
        em.persist(t);
        return t;
    }

    @Override
    public  void update(final T t){
        em.merge(t);
    }

    /**
     *
     * @param key - Primary key
     * @return entity
     */
    @Override
    public T find(final PK key){
        T t = em.find(getType(), key);
        return t;
    }

    /**
     *
     * @return list entity
     */
    @Override
    public List<T> findAll(){
        List<T> list = em.createQuery("select x from " + getType().getSimpleName() + " x").getResultList();
        return list;
    }

    /**
     * method for route, train, station entity
     * @param name - name field entity
     * @return entity
     */
    @Override
    public T find(String name) {
        try {
            Query query = em.createQuery("select x from " + getType().getSimpleName() + " x where x.name =:name");
            query.setParameter("name", name);
            T t = (T) query.getSingleResult();
            return t;
        }
        catch (NoResultException e)
        {
            return null;
        }
    }
}