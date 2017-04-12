package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;
import edu.itechart.contactlist.util.email.EmailTemplateManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

public class ShowEmailPageCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowEmailPageCommand.class);
    private static final String REQUEST_PARAM_NAME = "check-contact";
    private static final String REQUEST_ATTR_RECIPIENTS = "recipients";
    private static final String REQUEST_ATTR_TEMPLATES = "templates";
    private static final String URL_EMAIL_PAGE = "email_page.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String[] items = request.getParameterValues(REQUEST_PARAM_NAME);
            LOGGER.info("Show page for sending email to contacts with id = {}", items);
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
            Map<String, ST> templates = EmailTemplateManager.getTemplates(EmailTemplateManager.TEMPLATES_PATH);
            Map<String, String> genericTemplates = EmailTemplateManager.getGenericTemplates(templates);
            System.out.println(genericTemplates);
            request.setAttribute(REQUEST_ATTR_RECIPIENTS, recipients);
            request.setAttribute(REQUEST_ATTR_TEMPLATES, genericTemplates);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return URL_EMAIL_PAGE;
    }
}
