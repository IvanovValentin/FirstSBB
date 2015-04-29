package com.javaschool.ivanov.Service;


import com.javaschool.ivanov.DAO.*;
import com.javaschool.ivanov.DTOs.BuyTicketInfo;
import com.javaschool.ivanov.Domain.Person;
import com.javaschool.ivanov.Exception.InValidTimeException;
import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Exception.TrainFullException;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class CustomerService {

    private final static Logger log = Logger.getLogger(UserService.class);

    @EJB
    private ScheduleDao scheduleDao;

    @EJB
    private TicketDao ticketDao;

    @EJB
    private TripDao tripDao;

    @EJB
    private PersonDao personDao;

    @EJB
    private StationDao stationDao;

    /**
     * get schedule by station name
     * @param station - station name
     * @return list of schedule
     */
    public List<Object[]> loadSchedule(String station) {
        try {
            log.info("Send list with schedule to client.");
            return scheduleDao.loadSchedule(station);

        } catch (IndexOutOfBoundsException e) {
            List<Object[]> list = new ArrayList<Object[]>();
            Object[] objects = {null, null, null};
            list.add(objects);
            return list;
        }
    }

    /**
     * get train
     * @param stationFrom - departure station name
     * @param stationTo - arrival station name
     * @param date1
     * @param date2
     * @return list of schedule
     */
    public List<Object[]> findTrain(String stationFrom, String stationTo, Date date1, Date date2) {
        try {
            log.info("Send list with trains to client.");
            return scheduleDao.findTrain(stationFrom, stationTo, date1, date2);

        } catch (IndexOutOfBoundsException e) {
            List<Object[]> list = new ArrayList<Object[]>();
            Object[] objects = {null, null, null};
            list.add(objects);
            return list;
        }
    }

    /**
     * @param info - client request params
     * @throws TrainFullException - if free places < 1
     * @throws InValidTimeException - if now time + 10 min > departure time
     * @throws ObjectExistException - if passenger already exist on trip
     */
    public void buyTicket(BuyTicketInfo info) throws TrainFullException, InValidTimeException, ObjectExistException {

        int tripId = tripDao.findTripPK(info.getTrain(), info.getRoute(), info.getDate());

        if(!ticketDao.isExistPassenger(info.getFirstname(), info.getLastname(), info.getBirthday(), tripId))
        {
            if(tripDao.isValidDepartureTime(info))
            {
                if(!ticketDao.isTrainFull(info))
                {
                    Integer personId = personDao.findId(info);
                    if(personId == null)
                    {
                        java.sql.Date date = new java.sql.Date(info.getBirthday().getTime());
                        Person person = new Person(info.getFirstname(), info.getLastname(), date);
                        personId = personDao.create(person).getId();
                    }
                    int stationFrom = stationDao.find(info.getStationFrom()).getId();
                    int stationTo = stationDao.find(info.getStationTo()).getId();
                    ticketDao.create(personId, tripId, stationFrom, stationTo);
                    log.info(info.getLastname() + " " + info.getFirstname() + " buy ticket.");
                }
                else throw new TrainFullException();
            }
            else throw new InValidTimeException();
        }
        else throw new ObjectExistException();
    }
}
