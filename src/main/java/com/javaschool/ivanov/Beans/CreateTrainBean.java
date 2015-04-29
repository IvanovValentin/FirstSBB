package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Service.EmployeeService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import javax.ejb.EJB;

@ViewScoped
@ManagedBean
public class CreateTrainBean implements Serializable{

    private String train;
    private Integer capacity;

    @EJB
    EmployeeService employeeService;

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void createTrain()
    {
        try
        {
            employeeService.createTrain(train, capacity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_INFO, "Поезд добавлен!", ""));
        }
        catch (ObjectExistException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_WARN, "Поезд уже существует.", ""));
        }
    }
}
