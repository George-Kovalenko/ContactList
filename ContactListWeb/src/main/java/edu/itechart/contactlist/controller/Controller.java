package edu.itechart.contactlist.controller;

import edu.itechart.contactlist.commands.Command;
import edu.itechart.contactlist.commands.CommandException;
import edu.itechart.contactlist.commands.CommandFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    public Controller() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Command command = CommandFactory.createCommand(req);
            String page = command.execute(req, resp);
            getServletContext().getRequestDispatcher(page).forward(req, resp);
        } catch (CommandException e) {
            LOGGER.error(e);
        }
    }
}
