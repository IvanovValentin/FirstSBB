package com.javaschool.ivanov.Beans;


import com.javaschool.ivanov.Exception.IncorrectDataException;
import com.javaschool.ivanov.Service.UserService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import java.io.Serializable;

@ViewScoped
@ManagedBean
public class SendPasswordBean implements Serializable{

    private String email;

    @EJB
    private UserService userService;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void sendPassword()
    {
        try {
            userService.sendPassword(email);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_INFO, "Пароль успешно восстановлен!", ""));
        }
        catch (IncorrectDataException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_ERROR, "Пользователя с таким адрессом нет!", ""));
        }
        catch (MessagingException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_ERROR, "Ошибка при отправке сообщения!", ""));
        }
    }
}
