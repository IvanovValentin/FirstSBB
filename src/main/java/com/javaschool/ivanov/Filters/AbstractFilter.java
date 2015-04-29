package com.javaschool.ivanov.Filters;


import javax.servlet.*;

public abstract class AbstractFilter implements Filter{

    protected static final int EMPLOYEE_STATUS = 2;
    protected static final int CUSTOMER_STATUS = 1;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
