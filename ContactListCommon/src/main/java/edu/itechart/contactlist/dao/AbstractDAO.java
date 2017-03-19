package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.connectionpool.ConnectionPool;
import edu.itechart.contactlist.connectionpool.ConnectionPoolException;
import edu.itechart.contactlist.connectionpool.ProxyConnection;

public abstract class AbstractDAO implements AutoCloseable {
    protected ProxyConnection connection;

    public AbstractDAO() throws DAOException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void close() {
        ConnectionPool.getInstance().releaseConnection(connection);
    }
}
