package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.connectionpool.ConnectionPool;
import edu.itechart.contactlist.connectionpool.ConnectionPoolException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDAO implements AutoCloseable {
    protected Connection connection;

    public AbstractDAO() throws DAOException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
