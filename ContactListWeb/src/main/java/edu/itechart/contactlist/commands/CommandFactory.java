package edu.itechart.contactlist.commands;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandFactory.class);
    private static final String PARAM_NAME = "command";

    public static Command createCommand(HttpServletRequest request) throws CommandException {
        String command = request.getParameter(PARAM_NAME);
        if (StringUtils.isNotEmpty(command)) {
            try {
                command = command.toUpperCase();
                LOGGER.info("Returned command {}", command);
                return CommandEnum.valueOf(command).getCommand();
            } catch (IllegalArgumentException e) {
                throw new CommandException(e);
            }
        } else {
            LOGGER.info("Returned default command {}", CommandEnum.SHOW_CONTACT_LIST);
            return new ShowContactListCommand();
        }
    }
}
