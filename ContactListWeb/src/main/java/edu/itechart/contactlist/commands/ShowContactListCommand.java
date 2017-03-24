package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowContactListCommand implements Command {
    private static final String REQUEST_ATTR_CONTACTS = "contacts";
    private static final String URL_CONTACT_LIST = "/contact_list.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            ArrayList<Contact> contacts = ContactService.findAll();
            request.setAttribute(REQUEST_ATTR_CONTACTS, contacts);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return URL_CONTACT_LIST;
    }
}
