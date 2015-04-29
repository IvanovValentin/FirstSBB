package com.javaschool.ivanov;




import com.javaschool.ivanov.DAO.*;
import com.javaschool.ivanov.DTOs.BuyTicketInfo;
import com.javaschool.ivanov.Exception.InValidTimeException;
import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Exception.TrainFullException;
import com.javaschool.ivanov.Service.CustomerService;
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


public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private ScheduleDao scheduleDaoMock;

    @Mock
    private TicketDao ticketDaoMock;

    @Mock
    private TripDao tripDaoMock;

    @Mock
    private PersonDao personDaoMock;

    @Mock
    private StationDao stationDaoMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void positiveLoadSchedule()
    {
        String station = "station";
        List<Object[]> list = new ArrayList<Object[]>();
        Object[] objects = {null, null, null};
        list.add(objects);
        Mockito.when(scheduleDaoMock.loadSchedule(station)).thenReturn(list);
        Assert.assertEquals(list, customerService.loadSchedule(station));
    }

    @Test
    public void negativeLoadSchedule()
    {
        String station = "station";
        List<Object[]> list = new ArrayList<Object[]>();
        Object[] objects = {null, null, null};
        list.add(objects);
        Mockito.when(scheduleDaoMock.loadSchedule(station)).thenThrow(IndexOutOfBoundsException.class);
        Assert.assertNull(customerService.loadSchedule(station).get(0)[0]);
    }


    @Test
    public void positiveFindTrain()
    {
        String stationTo = "stationTo";
        String stationFrom = "stationFrom";
        Date date1 = new Date();
        Date date2 = new Date();
        List<Object[]> list = new ArrayList<Object[]>();
        Object[] objects = {null, null, null};
        list.add(objects);
        Mockito.when(scheduleDaoMock.findTrain(stationFrom, stationTo, date1, date2)).thenReturn(list);
        Assert.assertEquals(list, customerService.findTrain(stationFrom, stationTo, date1, date2));
    }

    @Test
    public void negativeFindTrain()
    {
        String stationTo = "stationTo";
        String stationFrom = "stationFrom";
        Date date1 = new Date();
        Date date2 = new Date();
        List<Object[]> list = new ArrayList<Object[]>();
        Object[] objects = {null, null, null};
        list.add(objects);
        Mockito.when(scheduleDaoMock.findTrain(stationFrom, stationTo, date1, date2)).thenThrow(IndexOutOfBoundsException.class);
        Assert.assertNull(customerService.findTrain(stationFrom, stationTo, date1, date2).get(0)[0]);
    }

    @Test
    public void NewPersonBuyTicket()  throws TrainFullException, InValidTimeException, ObjectExistException
    {
        BuyTicketInfo info = new BuyTicketInfo();
        info.setFirstname("firstName");
        info.setLastname("lastName");
        info.setBirthday(new Date());
        info.setStationFrom("stationFrom");
        info.setStationTo("stationTo");
        info.setTrain("train");
        info.setRoute("route");
        info.setDate(new Date());
        Mockito.when(tripDaoMock.findTripPK(info.getTrain(), info.getRoute(), info.getDate())).thenReturn(1);
        Mockito.when(ticketDaoMock.isExistPassenger(info.getFirstname(), info.getLastname(), info.getBirthday(), 1)).thenReturn(false);
        Mockito.when(tripDaoMock.isValidDepartureTime(info)).thenReturn(true);
        Mockito.when(ticketDaoMock.isTrainFull(info)).thenReturn(false);
        Mockito.when(personDaoMock.findId(info)).thenReturn(null);
        customerService.buyTicket(info);
    }

    @Test
    public void PersonExistBuyTicket()  throws TrainFullException, InValidTimeException, ObjectExistException
    {
        BuyTicketInfo info = new BuyTicketInfo();
        info.setFirstname("firstName");
        info.setLastname("lastName");
        info.setBirthday(new Date());
        info.setStationFrom("stationFrom");
        info.setStationTo("stationTo");
        info.setTrain("train");
        info.setRoute("route");
        info.setDate(new Date());
        Mockito.when(tripDaoMock.findTripPK(info.getTrain(), info.getRoute(), info.getDate())).thenReturn(1);
        Mockito.when(ticketDaoMock.isExistPassenger(info.getFirstname(), info.getLastname(), info.getBirthday(), 1)).thenReturn(false);
        Mockito.when(tripDaoMock.isValidDepartureTime(info)).thenReturn(true);
        Mockito.when(ticketDaoMock.isTrainFull(info)).thenReturn(false);
        Mockito.when(personDaoMock.findId(info)).thenReturn(1);
        customerService.buyTicket(info);
    }

    @Test(expected = TrainFullException.class)
    public void TrainFullBuyTicket() throws TrainFullException, InValidTimeException, ObjectExistException
    {
        BuyTicketInfo info = new BuyTicketInfo();
        info.setFirstname("firstName");
        info.setLastname("lastName");
        info.setBirthday(new Date());
        info.setStationFrom("stationFrom");
        info.setStationTo("stationTo");
        info.setTrain("train");
        info.setRoute("route");
        info.setDate(new Date());
        Mockito.when(tripDaoMock.findTripPK(info.getTrain(), info.getRoute(), info.getDate())).thenReturn(1);
        Mockito.when(ticketDaoMock.isExistPassenger(info.getFirstname(), info.getLastname(), info.getBirthday(), 1)).thenReturn(false);
        Mockito.when(tripDaoMock.isValidDepartureTime(info)).thenReturn(true);
        Mockito.when(ticketDaoMock.isTrainFull(info)).thenReturn(true);
        customerService.buyTicket(info);
    }

    @Test(expected = InValidTimeException.class)
    public void InvalidTimeBuyTicket() throws TrainFullException, InValidTimeException, ObjectExistException
    {
        BuyTicketInfo info = new BuyTicketInfo();
        info.setFirstname("firstName");
        info.setLastname("lastName");
        info.setBirthday(new Date());
        info.setStationFrom("stationFrom");
        info.setStationTo("stationTo");
        info.setTrain("train");
        info.setRoute("route");
        info.setDate(new Date());
        Mockito.when(tripDaoMock.findTripPK(info.getTrain(), info.getRoute(), info.getDate())).thenReturn(1);
        Mockito.when(ticketDaoMock.isExistPassenger(info.getFirstname(), info.getLastname(), info.getBirthday(), 1)).thenReturn(false);
        Mockito.when(tripDaoMock.isValidDepartureTime(info)).thenReturn(false);
        customerService.buyTicket(info);
    }

    @Test(expected = ObjectExistException.class)
    public void PassengerExistBuyTicket() throws TrainFullException, InValidTimeException, ObjectExistException
    {
        BuyTicketInfo info = new BuyTicketInfo();
        info.setFirstname("firstName");
        info.setLastname("lastName");
        info.setBirthday(new Date());
        info.setStationFrom("stationFrom");
        info.setStationTo("stationTo");
        info.setTrain("train");
        info.setRoute("route");
        info.setDate(new Date());
        Mockito.when(tripDaoMock.findTripPK(info.getTrain(), info.getRoute(), info.getDate())).thenReturn(1);
        Mockito.when(ticketDaoMock.isExistPassenger(info.getFirstname(), info.getLastname(), info.getBirthday(), 1)).thenReturn(true);
        customerService.buyTicket(info);
    }
}
