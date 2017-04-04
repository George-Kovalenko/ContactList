package edu.itechart.contactlist.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandFactory.class);
    private static final String PARAM_NAME = "command";

    public static Command createCommand(HttpServletRequest request) throws CommandException {
        String command = request.getParameter(PARAM_NAME);
        if (command != null) {
            try {
                command = command.toUpperCase();
                LOGGER.info("Command {}", command);
                return CommandEnum.valueOf(command).getCommand();
            } catch (IllegalArgumentException e) {
                throw new CommandException(e);
            }
        } else {
            LOGGER.info("Default command {}", command);
            return new ShowContactListCommand();
        }
    }
}
