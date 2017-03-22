package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.dao.ContactDAO;
import edu.itechart.contactlist.dao.DAOException;
import edu.itechart.contactlist.dao.MaritalStatusDAO;
import edu.itechart.contactlist.dao.PhoneTypeDAO;
import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entity.MaritalStatus;
import edu.itechart.contactlist.entity.PhoneType;

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
        try (ContactDAO contactDAO = new ContactDAO();
             MaritalStatusDAO maritalStatusDAO = new MaritalStatusDAO();
             PhoneTypeDAO phoneTypeDAO = new PhoneTypeDAO()) {
            long id = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME));
            Contact contact = contactDAO.findById(id);
            ArrayList<MaritalStatus> maritalStatuses = maritalStatusDAO.findAll();
            ArrayList<PhoneType> phoneTypes = phoneTypeDAO.findAll();
            request.setAttribute(REQUEST_ATTR_CONTACTS, contact);
            request.setAttribute(REQUEST_ATTR_MARITAL_STATUSES, maritalStatuses);
            request.setAttribute(REQUEST_ATTR_PHONE_TYPES, phoneTypes);
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        return URL_CONTACT;
    }
}
