package edu.itechart.contactlist.controller;

import edu.itechart.contactlist.commands.Command;
import edu.itechart.contactlist.commands.CommandException;
import edu.itechart.contactlist.commands.CommandFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

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
            if (command.needsRedirect()) {
                LOGGER.info("Redirect on page {}", page);
                resp.sendRedirect(page);
            } else if (StringUtils.isNotEmpty(page)) {
                LOGGER.info("Forward on page {}", page);
                getServletContext().getRequestDispatcher(page).forward(req, resp);
            }
        } catch (CommandException e) {
            LOGGER.error("Error when process request", e);
        }
    }
}
