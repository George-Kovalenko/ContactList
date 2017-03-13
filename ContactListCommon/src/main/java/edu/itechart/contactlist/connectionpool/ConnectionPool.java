package edu.itechart.contactlist.connectionpool;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static ConnectionPool instance;
    private static ReentrantLock locking = new ReentrantLock();
    private static AtomicBoolean created = new AtomicBoolean(false);
    private static AtomicBoolean released = new AtomicBoolean(false);
    private static final int POOL_CAPACITY = ConnectionConfiguration.getPoolCapacity();
    private static final int TIME_WAIT = ConnectionConfiguration.getTimeWait();
    private static ArrayBlockingQueue<Connection> connections;
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private ConnectionPool() {
        registerDriver();
        String url = ConnectionConfiguration.getUrl();
        String user = ConnectionConfiguration.getUser();
        String password = ConnectionConfiguration.getPassword();
        connections = new ArrayBlockingQueue<>(POOL_CAPACITY);
        for (int i = 0; i < connections.size(); i++) {
            addConnectionInPool(url, user, password);
        }
    }

    public static ConnectionPool getInstance() {
        if (!created.get()) {
            locking.lock();
            if (!created.get()) {
                try {
                    instance = new ConnectionPool();
                    created.set(true);
                } finally {
                    locking.unlock();
                }
            }
        }
        return instance;
    }

    public static Connection getConnection() throws ConnectionPoolException {
        if (released.get()) {
            throw new ConnectionPoolException("Couldn't get connection when pool released");
        }
        try {
            Connection connection = connections.poll(TIME_WAIT, TimeUnit.MILLISECONDS);
            if (connection != null)  {
                return connection;
            } else {
                throw new ConnectionPoolException("Timeout waiting for connection");
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error when waiting for connection", e);
            return null;
        }
    }

    private static void addConnectionInPool(String url, String user, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            connections.add(connection);
        } catch (SQLException e) {
            LOGGER.error("Couldn't connect to database", e);
        }
    }

    private void registerDriver() {
        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            LOGGER.error("Couldn't register driver", e);
        }
    }

    public static void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.getAutoCommit()) {
                    connection.rollback();
                }
                connections.add(connection);
            } catch (SQLException e) {
                LOGGER.error("Error when realising connection", e);
            }
        }
    }
}
