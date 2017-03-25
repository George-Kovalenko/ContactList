package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.MainHandler;
import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateContactCommand implements Command {
    private static final String REQUEST_PARAM_ID = "id";
    private static final String REQUEST_ATTR_CONTACT = "contact";
    private static final String URL_CONTACT = "/controller?command=show_contact&contact_id=";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int id = Integer.parseInt(request.getParameter(REQUEST_PARAM_ID));
        MainHandler mainHandler = new MainHandler();
        mainHandler.handleInputFields(request);
        try {
            Contact contact = (Contact) request.getAttribute(REQUEST_ATTR_CONTACT);
            ContactService.update(id, contact);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return URL_CONTACT + id;
    }

    @Override
    public boolean needsRedirect() {
        return true;
    }
}
