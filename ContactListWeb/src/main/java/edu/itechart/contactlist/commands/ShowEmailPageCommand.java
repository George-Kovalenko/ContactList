package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowEmailPageCommand implements Command {
    private static final String REQUEST_PARAM_NAME = "check-contact";
    private static final String URL_EMAIL_PAGE = "/email_page.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String[] items = request.getParameterValues(REQUEST_PARAM_NAME);
            ArrayList<Contact> recipients = new ArrayList<>();
            if (items != null) {
                for (String item : items) {
                    Long id = Long.parseLong(item);
                    Contact contact = ContactService.findById(id);
                    if (StringUtils.isNotEmpty(contact.getEmail())) {
                        recipients.add(contact);
                    }
                }
            }
            request.setAttribute("recipients", recipients);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return URL_EMAIL_PAGE;
    }
}
