package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.dao.ContactDAO;
import edu.itechart.contactlist.dao.DAOException;
import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.MainHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateContactCommand implements Command {
    private static final String REQUEST_PARAM_ID = "id";
    private static final String REQUEST_ATTR_CONTACT = "contact";
    private static final String URL_CONTACT= "/controller?command=show_contact&contact_id=";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int id = Integer.parseInt(request.getParameter(REQUEST_PARAM_ID));
        MainHandler mainHandler = new MainHandler();
        mainHandler.handleInputFields(request);
        try (ContactDAO contactDAO = new ContactDAO()) {
            Contact contact = (Contact) request.getAttribute(REQUEST_ATTR_CONTACT);
            contactDAO.update(id, contact);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return URL_CONTACT + id;
    }

    @Override
    public boolean needsRedirect() {
        return true;
    }
}
