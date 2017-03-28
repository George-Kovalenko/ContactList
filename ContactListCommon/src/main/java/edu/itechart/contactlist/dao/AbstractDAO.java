package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Entity;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class AbstractDAO<Type extends Entity> {
    protected Connection connection;

    public AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract ArrayList<Type> findAll() throws DAOException;

    public abstract Type findById(long id) throws DAOException;

    public abstract void insert(Type entity) throws DAOException;

    public abstract void delete(long id) throws DAOException;

    public abstract void update(long id, Type entity) throws DAOException;
}
