package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entity.MaritalStatus;
import edu.itechart.contactlist.entity.PhoneType;
import edu.itechart.contactlist.service.*;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowContactCommand implements Command {
    private static final String REQUEST_PARAM_NAME = "contact_id";
    private static final String REQUEST_ATTR_CONTACTS = "contact";
    private static final String REQUEST_ATTR_MARITAL_STATUSES = "maritalStatuses";
    private static final String REQUEST_ATTR_PHONE_TYPES = "phoneTypes";
    private static final String REQUEST_ATTR_PHOTO = "photo";
    private static final String REQUEST_ATTR_YEAR = "year";
    private static final String REQUEST_ATTR_MONTH = "month";
    private static final String REQUEST_ATTR_DAY = "day";
    private static final String URL_CONTACT = "/contact.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            if (request.getParameter(REQUEST_PARAM_NAME) != null) {
                long id = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME));
                Contact contact = ContactService.findById(id);
                if (contact.getBirthDate() != null) {
                    DateTime dateTime = new DateTime(contact.getBirthDate().getTime());
                    request.setAttribute(REQUEST_ATTR_YEAR, dateTime.getYear());
                    request.setAttribute(REQUEST_ATTR_MONTH, dateTime.getMonthOfYear());
                    request.setAttribute(REQUEST_ATTR_DAY, dateTime.getDayOfMonth());
                }
                String pathToPhoto = AttachmentFileService.getPathToPhoto(id);
                request.setAttribute(REQUEST_ATTR_CONTACTS, contact);
                request.setAttribute(REQUEST_ATTR_PHOTO, pathToPhoto);
            }
            ArrayList<MaritalStatus> maritalStatuses = MaritalStatusService.findAll();
            ArrayList<PhoneType> phoneTypes = PhoneTypeService.findAll();
            request.setAttribute(REQUEST_ATTR_MARITAL_STATUSES, maritalStatuses);
            request.setAttribute(REQUEST_ATTR_PHONE_TYPES, phoneTypes);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return URL_CONTACT;
    }
}
