package com.javaschool.ivanov.DAO;


import com.javaschool.ivanov.Domain.Route;

import javax.ejb.Stateless;

@Stateless
public class RouteDao extends GenericDaoJpaImpl<Route, Integer> {
    public RouteDao() {
        super(Route.class);
    }
}
