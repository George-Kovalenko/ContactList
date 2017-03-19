package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.dao.ContactDAO;
import edu.itechart.contactlist.dao.DAOException;
import edu.itechart.contactlist.entity.Contact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowContactCommand implements Command {
    private static final String REQUEST_PARAM_NAME = "contact_id";
    private static final String REQUEST_ATTR_CONTACTS = "contact";
    private static final String URL_CONTACT = "/contact.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            long id = Long.parseLong(request.getParameter(REQUEST_PARAM_NAME));
            ContactDAO contactDAO = new ContactDAO();
            Contact contact = contactDAO.findById(id);
            request.setAttribute(REQUEST_ATTR_CONTACTS, contact);
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        return URL_CONTACT;
    }
}
