package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entity.MaritalStatus;
import edu.itechart.contactlist.entity.PhoneType;
import edu.itechart.contactlist.service.*;
import edu.itechart.contactlist.util.Validator;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowContactCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowContactCommand.class);
    private static final String REQUEST_PARAM_NAME = "contact_id";
    private static final String REQUEST_ATTR_CONTACTS = "contact";
    private static final String REQUEST_ATTR_MARITAL_STATUSES = "maritalStatuses";
    private static final String REQUEST_ATTR_PHONE_TYPES = "phoneTypes";
    private static final String REQUEST_ATTR_PHOTO = "photo";
    private static final String REQUEST_ATTR_YEAR = "year";
    private static final String REQUEST_ATTR_MONTH = "month";
    private static final String REQUEST_ATTR_DAY = "day";
    private static final String URL_CONTACT = "contact.jsp";
    private static final String REDIRECT_URL = "controller";
    private boolean needsRedirect;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String stringId = request.getParameter(REQUEST_PARAM_NAME);
            LOGGER.info("Show contact with id = {}", stringId);
            if (StringUtils.isNotEmpty(stringId)) {
                if (!Validator.isNumber(stringId)) {
                    needsRedirect = true;
                    return REDIRECT_URL;
                }
                long id = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME));
                Contact contact = ContactService.findById(id);
                if (contact.getId() == 0) {
                    needsRedirect = true;
                    return REDIRECT_URL;
                }
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

    @Override
    public boolean needsRedirect() {
        return needsRedirect;
    }
}
