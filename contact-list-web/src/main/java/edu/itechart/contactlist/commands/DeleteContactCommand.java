package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteContactCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteContactCommand.class);
    private static final String REQUEST_PARAM_NAME = "check-contact";
    private static final String URL_CONTACT_LIST = "controller";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String[] items = request.getParameterValues(REQUEST_PARAM_NAME);
            if (items != null) {
                for (String item : items) {
                    long id = Long.parseLong(item);
                    LOGGER.info("Delete contact with id = {}", id);
                    ContactService.delete(id);
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return URL_CONTACT_LIST;
    }

    @Override
    public boolean needsRedirect() {
        return true;
    }
}
