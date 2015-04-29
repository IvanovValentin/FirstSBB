package com.javaschool.ivanov.DTOs;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReportInfo extends ArrayList<TicketInfo>{

    public ReportInfo()
    {
        super();
    }

    public ReportInfo(Collection<? extends TicketInfo> c)
    {
        super(c);
    }

    public List<TicketInfo> getReportInfo()
    {
        return this;
    }
    public void setReportInfo(List<TicketInfo> reportInfo)
    {
        this.addAll(reportInfo);
    }
}
