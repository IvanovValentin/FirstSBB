package com.javaschool.ivanov;


import com.javaschool.ivanov.DAO.*;
import com.javaschool.ivanov.Domain.Route;
import com.javaschool.ivanov.Domain.Station;
import com.javaschool.ivanov.Domain.Train;
import com.javaschool.ivanov.Domain.Trip;
import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Service.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeServiceTest {


    @InjectMocks
    private EmployeeService employeeServiceMock;

    @Mock
    private StationDao stationDaoMock;

    @Mock
    private RouteDao routeDaoMock;

    @Mock
    private TrainDao trainDaoMock;

    @Mock
    private ScheduleDao scheduleDaoMock;

    @Mock
    private Route routeMock;

    @Mock
    private DirectionDao directionDaoMock;

    @Mock
    private TripDao tripDaoMock;

    @Mock
    private PersonDao personDaoMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void positiveLoadAllTrains()
    {
        List<Object[]> list = new ArrayList<Object[]>();
        Object[] objects = {null, null, null};
        list.add(objects);
        Mockito.when(scheduleDaoMock.loadAllTrains()).thenReturn(list);
        Assert.assertEquals(list, employeeServiceMock.loadAllTrains());
    }

    @Test
    public void negativeLoadAllTrains()
    {
        List<Object[]> list = new ArrayList<Object[]>();
        Object[] objects = {null, null, null};
        list.add(objects);
        Mockito.when(scheduleDaoMock.loadAllTrains()).thenThrow(IndexOutOfBoundsException.class);
        Assert.assertNull(employeeServiceMock.loadAllTrains().get(0)[0]);
    }

    @Test
    public void positiveLoadAllPassengers()
    {
        String train = "train";
        List<Object[]> list = new ArrayList<Object[]>();
        Object[] objects = {null, null, null, null};
        list.add(objects);
        Mockito.when(personDaoMock.loadAllPassengers(train)).thenReturn(list);
        Assert.assertEquals(list, employeeServiceMock.loadAllPassengers(train));
    }

    @Test
    public void negativeLoadAllPassengers()
    {
        String train = "train";
        List<Object[]> list = new ArrayList<Object[]>();
        Object[] objects = {null, null, null, null};
        list.add(objects);
        Mockito.when(personDaoMock.loadAllPassengers(train)).thenThrow(IndexOutOfBoundsException.class);
        Assert.assertNull(employeeServiceMock.loadAllPassengers(train).get(0)[0]);
    }

    @Test
    public void positiveCreateStation() throws ObjectExistException
    {
        String station = "station";
        Mockito.when(stationDaoMock.find(station)).thenReturn(null);
        employeeServiceMock.createStation(station);
    }

    @Test(expected = ObjectExistException.class)
    public void negativeCreateStation() throws ObjectExistException
    {
        String station = "station";
        Mockito.when(stationDaoMock.find(station)).thenReturn(new Station());
        employeeServiceMock.createStation(station);
    }

    @Test
    public void positiveCreateTrain() throws ObjectExistException
    {
        String train = "train";
        int capacity = 1;
        Mockito.when(trainDaoMock.find(train)).thenReturn(null);
        employeeServiceMock.createTrain(train, capacity);
    }

    @Test(expected = ObjectExistException.class)
    public void negativeCreateTrain() throws ObjectExistException
    {
        String train = "train";
        int capacity = 1;
        Mockito.when(trainDaoMock.find(train)).thenReturn(new Train());
        employeeServiceMock.createTrain(train, capacity);
    }

    @Test
    public void positiveCreateTrip() throws ObjectExistException
    {
        String train = "train";
        String route = "route";
        Date date = new Date();
        Mockito.when(tripDaoMock.findTripPK(train, route, date)).thenReturn(null);
        Mockito.when(trainDaoMock.find(train)).thenReturn(new Train(train, 1));
        Mockito.when(routeDaoMock.find(route)).thenReturn(new Route(route));
        employeeServiceMock.createTrip(train, route, date);
    }

    @Test(expected = ObjectExistException.class)
    public void negativeCreateTrip() throws ObjectExistException
    {
        String train = "train";
        String route = "route";
        Date date = new Date();
        Mockito.when(tripDaoMock.findTripPK(train, route, date)).thenReturn(1);
        employeeServiceMock.createTrip(train, route, date);
    }

}
