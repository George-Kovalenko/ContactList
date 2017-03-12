package edu.itechart.contactlist.connectionpool;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool instance;
    private static final int POOL_CAPACITY = ConnectionConfiguration.getPoolCapacity();
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private ConnectionPool() {
        registerDriver();
        String url = ConnectionConfiguration.getUrl();
        String user = ConnectionConfiguration.getUser();
        String password = ConnectionConfiguration.getPassword();
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    private void registerDriver() {
        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            LOGGER.error("Couldn't register driver", e);
        }
    }
}
