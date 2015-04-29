package com.javaschool.ivanov.DAO;

import com.javaschool.ivanov.Domain.User;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Stateless
public class UserDao extends GenericDaoJpaImpl<User, Integer> {

    public UserDao() {
        super(User.class);
    }


    /**
     * get user entity by login
     * @param login - login name
     * @return user entity
     */
    public User find(String login)
    {
        try {
            Query query = em.createQuery("select u from User u where u.login =:login");
            query.setParameter("login", login);
            User user = (User) query.getSingleResult();
            return user;
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

}
