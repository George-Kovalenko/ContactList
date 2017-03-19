package edu.itechart.contactlist.connectionpool;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static ConnectionPool instance;
    private static ReentrantLock locking = new ReentrantLock();
    private static AtomicBoolean created = new AtomicBoolean(false);
    private static final int POOL_CAPACITY = ConnectionConfiguration.getPoolCapacity();
    private static final int TIME_WAIT = ConnectionConfiguration.getTimeWait();
    private static ArrayBlockingQueue<ProxyConnection> connections;
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private ConnectionPool() {
        registerDriver();
        String url = ConnectionConfiguration.getUrl();
        String user = ConnectionConfiguration.getUser();
        String password = ConnectionConfiguration.getPassword();
        connections = new ArrayBlockingQueue<>(POOL_CAPACITY);
        for (int i = 0; i < POOL_CAPACITY; i++) {
            this.addConnectionInPool(url, user, password);
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

    public ProxyConnection getConnection() throws ConnectionPoolException {
        try {
            ProxyConnection connection = connections.poll(TIME_WAIT, TimeUnit.MILLISECONDS);
            if (connection != null) {
                return connection;
            } else {
                throw new ConnectionPoolException("Timeout waiting for connection");
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error when waiting for connection", e);
            return null;
        }
    }

    private void addConnectionInPool(String url, String user, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            connections.add(proxyConnection);
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

    private void deregisterDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.error("Couldn't deregister driver " + driver.toString(), e);
            }
        }

    }

    public void releaseConnection(ProxyConnection connection) {
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

    public void releasePool() {
        while (!connections.isEmpty()) {
            ProxyConnection connection;
            if ((connection = connections.poll()) != null) {
                try {
                    connection.trueClose();
                } catch (SQLException e) {
                    LOGGER.error("Error when releasing pool", e);
                }
            }
        }
        deregisterDriver();
    }
}
