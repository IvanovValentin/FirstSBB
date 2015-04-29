package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Service.EmployeeService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import javax.ejb.EJB;

@ViewScoped
@ManagedBean
public class ViewAllPassengersBean {

    private String train;

    @EJB
    EmployeeService employeeService;

    private List<Object[]> listPassenges;

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public List<Object[]> getListPassenges() {
        return listPassenges;
    }

    public void setListPassenges(List<Object[]> listPassenges) {
        this.listPassenges = listPassenges;
    }

    public void loadPassengers()
    {
        listPassenges = employeeService.loadAllPassengers(train);
    }
}
