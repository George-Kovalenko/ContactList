package edu.itechart.contactlist.dao;

import java.sql.Connection;

public abstract class AbstractDAO {
    protected Connection connection;

    public AbstractDAO() {
    }

    public AbstractDAO(Connection connection) {
        this.connection = connection;
    }
}
