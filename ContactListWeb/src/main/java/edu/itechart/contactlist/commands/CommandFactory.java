package edu.itechart.contactlist.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);
    private static final String PARAM_NAME = "command";

    public static Command createCommand(HttpServletRequest request) throws CommandException {
        String command = request.getParameter(PARAM_NAME);
        if (command != null) {
            try {
                command = command.toUpperCase();
                LOGGER.info("Command " + command);
                return CommandEnum.valueOf(command).getCommand();
            } catch (IllegalArgumentException e) {
                throw new CommandException(e);
            }
        } else {
            LOGGER.info("Default command SHOW_CONTACT_LIST");
            return new ShowContactListCommand();
        }
    }
}
