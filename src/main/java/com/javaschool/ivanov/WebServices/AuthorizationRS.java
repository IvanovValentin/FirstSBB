package com.javaschool.ivanov.WebServices;


import com.javaschool.ivanov.DTOs.UserInfo;
import com.javaschool.ivanov.Exception.IncorrectDataException;
import com.javaschool.ivanov.Service.UserService;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/")
public class AuthorizationRS {

    private final static Logger log = Logger.getLogger(AuthorizationRS.class);
    @EJB
    private UserService userService;


    @POST
    @Path("authorization")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(final UserInfo userInfo)
    {
        log.info(userInfo.getLogin() + " Start rest 'getUser' method.");
        try
        {
            Integer accessLevel = userService.authorization(userInfo.getLogin(), userInfo.getPassword());
            return Response.ok(accessLevel).build();
        }
        catch (IncorrectDataException e)
        {
            return Response.serverError().build();
        }
    }
}
