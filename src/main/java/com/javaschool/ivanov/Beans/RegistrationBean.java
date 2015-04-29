package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Security.MD5;
import com.javaschool.ivanov.Service.UserService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ViewScoped
@ManagedBean
public class RegistrationBean implements Serializable{
    private String email;
    private String password1;
    private String password2;


    @EJB
    private UserService userService;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = MD5.getHash(password1);
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = MD5.getHash(password2);
    }

    public void registration()
    {
        if(!password1.equals(password2))
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_WARN, "Пароли не совпадают.", ""));
        }
        try {
            userService.registration(email, password1);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_INFO, "Регистрация прошла успешно.", ""));
        }
        catch (ObjectExistException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_WARN, "Такой логин уже существует.", ""));
        }

    }
}
