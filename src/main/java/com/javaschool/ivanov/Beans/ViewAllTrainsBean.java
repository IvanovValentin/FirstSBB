package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Service.EmployeeService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

@ViewScoped
@ManagedBean
public class ViewAllTrainsBean implements Serializable{

    private List<Object[]> listTrains;

    @EJB
    EmployeeService employeeService;

    @PostConstruct
    public void init(){
        listTrains = employeeService.loadAllTrains();
    }

    public List<Object[]> getListTrains() {
        return listTrains;
    }

    public void setListTrains(List<Object[]> listTrains) {
        this.listTrains = listTrains;
    }
}
