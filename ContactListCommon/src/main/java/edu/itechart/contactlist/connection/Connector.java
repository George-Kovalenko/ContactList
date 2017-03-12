package edu.itechart.contactlist.connection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static final Logger LOG = LogManager.getLogger(Connector.class);
    private static Connection connection;

    static {
        registerDriver();
    }

    private Connector() {
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Properties properties = new Properties();
                properties.put("user", ConnectionConfiguration.getUser());
                properties.put("password", ConnectionConfiguration.getPassword());
                connection = DriverManager.getConnection(ConnectionConfiguration.getUrl(), properties);
            }
        } catch (SQLException e) {
            LOG.error("Couldn't connect to database", e);
        }
        return connection;
    }

    private static void registerDriver() {
        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            LOG.error("Couldn't register driver", e);
        }
    }
}
