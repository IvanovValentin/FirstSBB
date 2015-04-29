package com.javaschool.ivanov.Filters;




import com.javaschool.ivanov.Beans.UserBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomerFilter extends AbstractFilter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Object object = ((HttpServletRequest) servletRequest).getSession().getAttribute("userBean");
        if(object == null)
        {
            ((HttpServletResponse) servletResponse).sendRedirect("index_page.xhtml?faces-redirect=true");
        }
        else if(((UserBean) object).getAcceessLevel()== EMPLOYEE_STATUS)
        {
            ((HttpServletResponse) servletResponse).sendRedirect("employee_page.xhtml?faces-redirect=true");
        }
        else if(((UserBean) object).getAcceessLevel()== CUSTOMER_STATUS)
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else
        {
            ((HttpServletResponse) servletResponse).sendRedirect("index_page.xhtml?faces-redirect=true");
        }
    }

}
