package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Service.SelectOneMenu;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

@ViewScoped
@ManagedBean
public class SelectOneMenuBean implements Serializable {

    private List<String> stations;
    private List<String> routes;
    private List<String> trains;
    private List<String> sequenceNumber;

    @EJB
    private SelectOneMenu selectOneMenu;

    @PostConstruct
    public void init(){
        sequenceNumber = new ArrayList<String>();
        for(int i=1; i<=30; i++)
        {
            sequenceNumber.add(i+"");
        }
    }

    public List<String> getStations() {
        return stations = selectOneMenu.findAllStations();
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public List<String> getRoutes() {
        return routes = selectOneMenu.findAllRoutes();
    }

    public void setRoutes(List<String> routes) {
        this.routes = routes;
    }

    public List<String> getTrains() {
        return trains = selectOneMenu.findAllTrains();
    }

    public void setTrains(List<String> trains) {
        this.trains = trains;
    }

    public List<String> getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(List<String> sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
