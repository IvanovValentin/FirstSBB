package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Exception.IncorrectDataException;
import com.javaschool.ivanov.Security.MD5;
import com.javaschool.ivanov.Service.UserService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ViewScoped
@ManagedBean(name = "authorizationBean")
public class AuthorizationBean implements Serializable {

    private String email = null;
    private String password = null;
    private int accessLevel = 0;
    private static final int EMPLOYEE_STATUS = 2;

    @EJB
    private UserService userService;

    @ManagedProperty( value = "#{userBean}")
    private UserBean userBean;

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = MD5.getHash(password);
    }

    public String authorization()
    {
        try
        {
            accessLevel = userService.authorization(email, password);
            userBean.setAcceessLevel(accessLevel);
            userBean.setEmail(email);

        }
        catch (IncorrectDataException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_WARN, "Логин и, или пароль не верен.", ""));
            return null;
        }
        if(accessLevel == EMPLOYEE_STATUS) return  "employee_page.xhtml?faces-redirect=true";
        else return  "customer_page.xhtml?faces-redirect=true";

    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index_page.xhtml?faces-redirect=true";
    }
}
