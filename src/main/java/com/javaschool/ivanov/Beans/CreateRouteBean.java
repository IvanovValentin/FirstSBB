package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.DTOs.CreateRouteInfo;
import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Service.EmployeeService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;

@ViewScoped
@ManagedBean
public class CreateRouteBean implements Serializable{

    private String route;
    private String stationFrom;
    private String stationTo;
    private Date duration;
    private String sequenceNumber;

    @EJB
    EmployeeService employeeService;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

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

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void createRoute()
    {
        CreateRouteInfo info = new CreateRouteInfo();
        info.setName(route);
        info.setDuration(duration);
        info.setSequenceNumber(sequenceNumber);
        info.setStationFrom(stationFrom);
        info.setStationTo(stationTo);
        try
        {
            employeeService.createRoute(info);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_INFO, "Маршрут добавлен!", ""));
        }
        catch (ObjectExistException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_WARN, "Маршрут уже существует.", ""));
        }

    }
}
