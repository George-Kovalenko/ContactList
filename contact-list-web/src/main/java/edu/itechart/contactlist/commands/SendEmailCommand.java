package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;
import edu.itechart.contactlist.util.email.EmailManager;
import edu.itechart.contactlist.util.email.EmailTemplateManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class SendEmailCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailCommand.class);
    private static final String PARAM_SUBJECT = "email-subject";
    private static final String PARAM_TEXT = "email-text";
    private static final String PARAM_RECIPIENTS = "recipient-id";
    private static final String PARAM_TEMPLATE_INDEX = "template-index";
    private static final String URL_CONTACT_LIST = "controller";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String subject = StringUtils.trim(request.getParameter(PARAM_SUBJECT));
            String text = StringUtils.trim(request.getParameter(PARAM_TEXT));
            int templateIndex = Integer.parseInt(request.getParameter(PARAM_TEMPLATE_INDEX));
            ArrayList<Contact> recipients = getAllRecipients(request);
            LOGGER.info("Send email to contacts with id {} with subject = {}, templateIndex = {} and text = {}",
                    request.getParameterValues(PARAM_RECIPIENTS), templateIndex, subject, text);
            EmailManager emailManager = new EmailManager();
            if (templateIndex > 0) {
                EmailTemplateManager emailTemplateManager = new EmailTemplateManager();
                for (Contact recipient : recipients) {
                    text = emailTemplateManager.makeEmailText(templateIndex, recipient.getFirstName());
                    emailManager.sendMail(recipient.getEmail(), subject, text);
                }
            } else {
                for (Contact recipient : recipients) {
                    emailManager.sendMail(recipient.getEmail(), subject, text);
                }
            }
        } catch (MessagingException | ServiceException e) {
            throw new CommandException(e);
        }
        return URL_CONTACT_LIST;
    }

    @Override
    public boolean needsRedirect() {
        return true;
    }

    private ArrayList<Contact> getAllRecipients(HttpServletRequest request) throws ServiceException {
        String[] items = request.getParameterValues(PARAM_RECIPIENTS);
        ArrayList<Contact> recipients = new ArrayList<>();
        if (items != null) {
            for (String item : items) {
                Contact contact = ContactService.findById(Long.parseLong(item));
                if (StringUtils.isNotEmpty(contact.getEmail())) {
                    recipients.add(contact);
                }
            }
        }
        return recipients;
    }
}
