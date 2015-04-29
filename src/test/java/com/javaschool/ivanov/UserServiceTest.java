package com.javaschool.ivanov;


import com.javaschool.ivanov.DAO.UserDao;

import com.javaschool.ivanov.Domain.User;
import com.javaschool.ivanov.Exception.IncorrectDataException;
import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Service.UserService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserDao userDaoMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void positiveRegistration() throws Exception
    {
        String login = "login";
        String password = "password";
        Mockito.when(userDaoMock.find(login)).thenReturn(null);
        userService.registration(login, password);
    }

    @Test(expected = ObjectExistException.class)
    public void negativeRegistration() throws Exception
    {
        String login = "login";
        String password = "password";
        Mockito.when(userDaoMock.find(login)).thenReturn(new User());
        userService.registration(login, password);
    }

    @Test
    public void positiveAuthorization() throws Exception
    {
        String login = "login";
        String password = "password";
        User user = new User(login, password, 1);
        Mockito.when(userDaoMock.find(login)).thenReturn(user);
        int accessLevel = userService.authorization(login, password);
        Assert.assertEquals(accessLevel, user.getAccessLevel());
    }

    @Test(expected = IncorrectDataException.class)
    public void UserIsNullAuthorization() throws Exception
    {
        String login = "login";
        String password = "password";
        User user = new User(login, password, 1);
        Mockito.when(userDaoMock.find(login)).thenReturn(null);
        int accessLevel = userService.authorization(login, password);
        Assert.assertEquals(accessLevel, user.getAccessLevel());
    }

    @Test(expected = IncorrectDataException.class)
    public void invalidPasswordAuthorization() throws Exception
    {
        String login = "login";
        String password = "password";
        User user = new User(login, "password2", 1);
        Mockito.when(userDaoMock.find(login)).thenReturn(user);
        int accessLevel = userService.authorization(login, password);
        Assert.assertEquals(accessLevel, user.getAccessLevel());
    }
}
