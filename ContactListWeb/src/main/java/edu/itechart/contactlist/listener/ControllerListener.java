package edu.itechart.contactlist.listener;

import edu.itechart.contactlist.connectionpool.ConnectionPool;
import edu.itechart.contactlist.util.email.EmailScheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ControllerListener implements ServletContextListener {
    private EmailScheduler emailScheduler;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance();
        emailScheduler = new EmailScheduler();
        emailScheduler.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        emailScheduler.shutdown();
        ConnectionPool.getInstance().deregisterDrivers();
    }
}
