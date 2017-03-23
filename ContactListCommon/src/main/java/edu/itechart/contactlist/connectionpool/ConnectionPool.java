package edu.itechart.contactlist.connectionpool;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static ConnectionPool instance;
    private static ReentrantLock locking = new ReentrantLock();
    private static AtomicBoolean created = new AtomicBoolean(false);
    private DataSource dataSource;
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private ConnectionPool() {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/contactlist");
        } catch (NamingException e) {
            LOGGER.error("Error when creating connection pool");
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

    public Connection getConnection() throws ConnectionPoolException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new ConnectionPoolException("Couldn't get connection from pool", e);
        }
    }

    public void releaseConnection(Connection connection) throws ConnectionPoolException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new ConnectionPoolException("Couldn't release connection", e);
        }
    }

    public void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            ClassLoader driverClassLoader = driver.getClass().getClassLoader();
            ClassLoader thisClassLoader = this.getClass().getClassLoader();
            if (driverClassLoader != null && thisClassLoader != null) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    LOGGER.error("Error when deregister driver " + driver.toString());
                }
            }
        }
    }
}
