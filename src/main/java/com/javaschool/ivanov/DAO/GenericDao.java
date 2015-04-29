package com.javaschool.ivanov.DAO;

import java.io.Serializable;
import java.util.List;


public interface GenericDao<T, PK extends Serializable> {

    public T create(T t);

    public void update(T t);

    public T find(PK key);

    public T find(String name);

    public List<T> findAll();

}