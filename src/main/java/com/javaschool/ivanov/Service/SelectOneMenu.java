package com.javaschool.ivanov.Service;


import com.javaschool.ivanov.DAO.RouteDao;
import com.javaschool.ivanov.DAO.StationDao;
import com.javaschool.ivanov.DAO.TrainDao;
import com.javaschool.ivanov.DAO.TripDao;
import com.javaschool.ivanov.Domain.Route;
import com.javaschool.ivanov.Domain.Station;
import com.javaschool.ivanov.Domain.Train;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

@Stateless
public class SelectOneMenu {

    @EJB
    private StationDao stationDao;

    @EJB
    RouteDao routeDao;

    @EJB
    TrainDao trainDao;

    @EJB
    private TripDao tripDao;


    public List<String> findAllStations()
    {

        List<Station> allStations = stationDao.findAll();
        List<String> stations = new ArrayList<String>();
        for (Station station : allStations) {
            stations.add(station.getName());
        }
        return stations;
    }
    public List<String> findAllRoutes()
    {
        List<Route> allRoutes = routeDao.findAll();
        List<String> routes = new ArrayList<String>();
        for (Route route : allRoutes) {
            routes.add(route.getName());
        }
        return routes;
    }
    public List<String> findAllTrains()
    {
        List<Train> allTrains = trainDao.findAll();
        List<String> trains = new ArrayList<String>();
        for (Train train : allTrains) {
            trains.add(train.getName());
        }
        return trains;
    }
    public List<Object> findTrainsByRoute(String route)
    {
        List<Object> allTrains = trainDao.findTrainByRoute(route);
        return allTrains;
    }
    public List<Object> findDatesByTrainAndRoute(String train, String route)
    {
        List<Object> allDates = tripDao.findDateByTrainAndRoute(train, route);
        return allDates;
    }
    public List<Object> findStationsFromByRoute(String route)
    {
        List<Object> allStationsFrom = stationDao.findStationsFromByRoute(route);
        return allStationsFrom;
    }
    public List<Object> findStationsToByRoute(String route)
    {
        List<Object> allStationsFrom = stationDao.findStationsToByRoute(route);
        return allStationsFrom;
    }

}
