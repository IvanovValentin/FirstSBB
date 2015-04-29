package com.javaschool.ivanov.DAO;


import com.javaschool.ivanov.DTOs.BuyTicketInfo;
import com.javaschool.ivanov.Domain.Person;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class PersonDao extends GenericDaoJpaImpl<Person, Integer> {

    public PersonDao() {
        super(Person.class);
    }

    List<Object[]> list;

    /**
     * get list of passengers last name, first name and day of birth
     * @param train - train name
     * @return list of passengers, who registered on the train
     * @throws IndexOutOfBoundsException
     */
    public List<Object[]> loadAllPassengers(String train)throws IndexOutOfBoundsException
    {
        Query query = em.createNativeQuery("select person.lastname , person.firstname , \n" +
                "person.birthday , trip.departure as date\n" +
                "from person \n" +
                "join ticket on person.id = ticket.person_id \n" +
                "join trip on trip.id = ticket.trip_id \n" +
                "join train on train.id = trip.train_id \n" +
                "where train.name = ? \n" +
                "order by date").setParameter(1, train);
        list = query.getResultList();

        return list;

    }

    /**
     * get passenger id
     * @param info - client request param
     * @return Primary key person entity
     */
    public Integer findId(BuyTicketInfo info)
    {
        try {
            Query query = em.createNativeQuery("select id " +
                    "from person " +
                    "where firstname = ? " +
                    "and lastname = ? " +
                    "and birthday = ?");
            query.setParameter(1, info.getFirstname());
            query.setParameter(2, info.getLastname());
            query.setParameter(3, info.getBirthday());
            return (Integer) query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }


}
