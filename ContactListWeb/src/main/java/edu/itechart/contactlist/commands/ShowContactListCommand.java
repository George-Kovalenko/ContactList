package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.dao.ContactDAO;
import edu.itechart.contactlist.dao.DAOException;
import edu.itechart.contactlist.entity.Contact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowContactListCommand implements Command {
    private static final String REQUEST_ATTR_CONTACTS = "contacts";
    private static final String URL_CONTACT_LIST = "/contact_list.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ArrayList<Contact> contacts;
        try {
            ContactDAO contactDAO = new ContactDAO();
            contacts = contactDAO.findAll();
            request.setAttribute(REQUEST_ATTR_CONTACTS, contacts);
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        return URL_CONTACT_LIST;
    }
}
