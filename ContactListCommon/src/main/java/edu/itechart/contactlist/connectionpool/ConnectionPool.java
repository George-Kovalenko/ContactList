package edu.itechart.contactlist.connectionpool;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

public class ConnectionPool {
    private static ConnectionPool instance;
    private static final int POOL_CAPACITY = ConnectionConfiguration.getPoolCapacity();
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
        return instance;
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
}
