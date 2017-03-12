package edu.itechart.contactlist.connectionpool;

import java.util.ResourceBundle;

class ConnectionConfiguration {
    private static final String PATH_TO_PROPERTIES = "database";
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(PATH_TO_PROPERTIES);
    private static String url = getProperty("url");
    private static String user = getProperty("user");
    private static String password = getProperty("password");
    private static int poolCapacity = Integer.parseInt(getProperty("poolCapacity"));


    private ConnectionConfiguration() {
    }

    static String getUrl() {
        return url;
    }

    static String getUser() {
        return user;
    }

    static String getPassword() {
        return password;
    }

    static int getPoolCapacity() {
        return poolCapacity;
    }

    private static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
