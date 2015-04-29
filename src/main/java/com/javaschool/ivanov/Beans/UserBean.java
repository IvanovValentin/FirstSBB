package com.javaschool.ivanov.Beans;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@SessionScoped
@ManagedBean(name = "userBean")
public class UserBean implements Serializable{

    private String email;
    private int acceessLevel=0;

    public int getAcceessLevel() {
        return acceessLevel;
    }

    public void setAcceessLevel(int acceessLevel) {
        this.acceessLevel = acceessLevel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
