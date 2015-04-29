package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Service.EmployeeService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;

@ViewScoped
@ManagedBean
public class CreateTripBean implements Serializable{

    private String train;
    private String route;
    private Date date;

    @EJB
    EmployeeService employeeService;

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void createTrip()
    {
        try
        {
            employeeService.createTrip(train, route, date);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_INFO, "Поездка добавлена!", ""));
        }
        catch (ObjectExistException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_WARN, "Поездка уже существует.", ""));
        }

    }
}
