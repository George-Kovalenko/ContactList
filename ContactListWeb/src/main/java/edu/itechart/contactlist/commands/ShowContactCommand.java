package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entity.MaritalStatus;
import edu.itechart.contactlist.entity.PhoneType;
import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.MaritalStatusService;
import edu.itechart.contactlist.service.PhoneTypeService;
import edu.itechart.contactlist.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowContactCommand implements Command {
    private static final String REQUEST_PARAM_NAME = "contact_id";
    private static final String REQUEST_ATTR_CONTACTS = "contact";
    private static final String REQUEST_ATTR_MARITAL_STATUSES = "maritalStatuses";
    private static final String REQUEST_ATTR_PHONE_TYPES = "phoneTypes";
    private static final String URL_CONTACT = "/contact.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            long id = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME));
            Contact contact = ContactService.findById(id);
            ArrayList<MaritalStatus> maritalStatuses = MaritalStatusService.findAll();
            ArrayList<PhoneType> phoneTypes = PhoneTypeService.findAll();
            request.setAttribute(REQUEST_ATTR_CONTACTS, contact);
            request.setAttribute(REQUEST_ATTR_MARITAL_STATUSES, maritalStatuses);
            request.setAttribute(REQUEST_ATTR_PHONE_TYPES, phoneTypes);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return URL_CONTACT;
    }
}
