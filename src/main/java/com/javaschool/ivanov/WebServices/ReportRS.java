package com.javaschool.ivanov.WebServices;


import com.javaschool.ivanov.DAO.TicketDao;
import com.javaschool.ivanov.DTOs.ReportInfo;
import com.javaschool.ivanov.DTOs.TicketInfo;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
@Path("/")
public class ReportRS {


    private final static Logger log = Logger.getLogger(ReportRS.class);

    @EJB
    private TicketDao ticketDao;

    @GET
    @Path("getReport")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findReport(@QueryParam("date1") Date date1, @QueryParam("date2") Date date2)
    {
        log.info(date1 + " - " + date2 + " Start rest 'findReport' method.");

        List<Object[]> list = ticketDao.findReport(date1, date2);
        List<TicketInfo> result = new ArrayList<TicketInfo>(list.size());

        for(Object[] o: list)
        {
            TicketInfo ticketInfo = new TicketInfo();
            ticketInfo.setTrainName((String) o[0]);
            ticketInfo.setRouteName((String) o[1]);
            ticketInfo.setDepartureDate((Timestamp) o[2]);
            ticketInfo.setLastName((String) o[3]);
            ticketInfo.setFirstName((String) o[4]);
            ticketInfo.setBirthday((java.sql.Date) o[5]);
            ticketInfo.setDepartureStation((String) o[6]);
            ticketInfo.setArrivalStation((String) o[7]);
            result.add(ticketInfo);
        }
        ReportInfo reportInfo = new ReportInfo(result);
        return Response.ok(reportInfo).build();
    }
}
