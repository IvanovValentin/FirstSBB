package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Service.CustomerService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

@RequestScoped
@ManagedBean
public class FindTrainBean implements Serializable{

    private String stationFrom;

    private String stationTo;

    private Date date1;

    private Date date2;

    private List<Object[]> listTrains;

    @EJB
    private CustomerService customerService;


    public String getStationFrom() {
        return stationFrom;
    }

    public void setStationFrom(String stationFrom) {
        this.stationFrom = stationFrom;
    }

    public String getStationTo() {
        return stationTo;
    }

    public void setStationTo(String stationTo) {
        this.stationTo = stationTo;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public List<Object[]> getListTrains() {
        return listTrains;
    }

    public void setListTrains(List<Object[]> listTrains) {
        this.listTrains = listTrains;
    }


    public void findTrain()
    {
        listTrains = customerService.findTrain(stationFrom, stationTo, date1, date2);
    }
}
