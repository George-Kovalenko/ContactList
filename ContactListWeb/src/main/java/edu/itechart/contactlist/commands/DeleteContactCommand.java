package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteContactCommand implements Command {
    private static final String REQUEST_PARAM_NAME = "check-contact";
    private static final String URL_CONTACT_LIST = "/controller";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String[] items = request.getParameterValues(REQUEST_PARAM_NAME);
            for (String item : items) {
                ContactService.delete(Long.parseLong(item));
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
