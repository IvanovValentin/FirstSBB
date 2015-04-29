package com.javaschool.ivanov.Service;


import com.javaschool.ivanov.DAO.*;
import com.javaschool.ivanov.DTOs.CreateRouteInfo;
import com.javaschool.ivanov.Domain.*;
import com.javaschool.ivanov.Exception.ObjectExistException;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class EmployeeService {

    private final static Logger log = Logger.getLogger(EmployeeService.class);

    @EJB
    private StationDao stationDao;

    @EJB
    private RouteDao routeDao;

    @EJB
    private TrainDao trainDao;

    @EJB
    private ScheduleDao scheduleDao;


    private Route route = new Route();

    @EJB
    private DirectionDao directionDao;

    @EJB
    private TripDao tripDao;

    @EJB
    private PersonDao personDao;

    /**
     * @param name - station name
     * @throws ObjectExistException - if station already exist in data base
     */
    public void createStation(String name) throws ObjectExistException
    {
            if (stationDao.find(name) == null) {
                stationDao.create(new Station(name));
                log.info("Station '" + name + "' (Created)");
            } else throw new ObjectExistException();
    }

    /**
     * @param info - employee request params
     * @throws ObjectExistException - if route already exist in data base
     */
    public void createRoute(CreateRouteInfo info) throws ObjectExistException
    {
            route = routeDao.find(info.getName());
            if( route == null)
            {
                route = routeDao.create(new Route(info.getName()));
            }
            Integer directionId = directionDao.find(info.getStationFrom(), info.getStationTo(), info.getDuration());

            Direction direction;
            if(directionId == null)
            {
                Station stationFrom = stationDao.find(info.getStationFrom());
                Station stationTo = stationDao.find(info.getStationTo());
                Timestamp timestamp = new Timestamp(info.getDuration().getTime());
                direction = new Direction(timestamp, stationFrom, stationTo);
                direction = directionDao.create(direction);
            }
            else direction = directionDao.find(directionId);

            int sequenceNumber = Integer.parseInt(info.getSequenceNumber());
            Integer scheduleId = scheduleDao.find(sequenceNumber, route.getId(), direction.getId());
            if(scheduleId == null)
            {
                Schedule schedule = new Schedule(sequenceNumber, route, direction);
                scheduleDao.create(schedule);
                log.info("Route '" + info.getName() + "' (Created)");
            }
            else throw new ObjectExistException();
    }

    /**
     * @param name - train name
     * @param capacity - train capacity
     * @throws ObjectExistException - if train already exist in data base
     */
    public void createTrain(String name, Integer capacity) throws ObjectExistException
    {
            if(trainDao.find(name) == null)
            {
                trainDao.create(new Train(name, capacity));
                log.info("Train '" + name + "' (Created)");
            }
            else throw new ObjectExistException();
    }

    /**
     *
     * @param trainName
     * @param routeName
     * @param date - departure date
     * @throws ObjectExistException - if trip is already exist
     */

    public void createTrip(String trainName, String routeName, Date date) throws ObjectExistException
    {

            if(tripDao.findTripPK(trainName, routeName, date) == null) {
                Train train = trainDao.find(trainName);
                route = routeDao.find(routeName);
                tripDao.create(date, train.getId(), route.getId());
                log.info("Trip '" + trainName + "' (Created)");
            }
            else throw  new ObjectExistException();
    }

    /**
     * get all trains
     * @return - list of schedule
     */
    public List<Object[]> loadAllTrains()
    {
        try {
            log.info("Send list with all trains to employee.");
            return scheduleDao.loadAllTrains();


        } catch (IndexOutOfBoundsException e) {
            List<Object[]> list = new ArrayList<Object[]>();
            Object[] objects = {null, null, null};
            list.add(objects);
            return list;
        }
    }

    /**
     * get all passengers in this train
     * @param train - train name
     * @return list of passengers
     */
    public List<Object[]> loadAllPassengers(String train)
    {

        try {
            log.info("Send list with all passengers to employee.");
            return personDao.loadAllPassengers(train);

        } catch (IndexOutOfBoundsException e) {
            List<Object[]> list = new ArrayList<Object[]>();
            Object[] objects = {null, null, null, null};
            list.add(objects);
            return list;
        }
    }
}
