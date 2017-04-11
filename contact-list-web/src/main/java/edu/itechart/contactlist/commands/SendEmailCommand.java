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
import java.util.Map;

public class SendEmailCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailCommand.class);
    private static final String PARAM_SUBJECT = "email-subject";
    private static final String PARAM_TEXT = "email-text";
    private static final String PARAM_RECIPIENTS = "recipient-id";
    private static final String PARAM_TEMPLATE_NAME = "email-template";
    private static final String URL_CONTACT_LIST = "controller";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String subject = StringUtils.trim(request.getParameter(PARAM_SUBJECT));
            String text = StringUtils.trim(request.getParameter(PARAM_TEXT));
            String templateName = request.getParameter(PARAM_TEMPLATE_NAME);
            ArrayList<Contact> recipients = getAllRecipients(request);
            LOGGER.info("Send email to contacts with id {} with subject = {}, template = {} and text = {}",
                    request.getParameterValues(PARAM_RECIPIENTS), subject, templateName, text);
            EmailManager emailManager = new EmailManager();
            if (StringUtils.isNotEmpty(templateName)) {
                Map<Long, String> templates = EmailTemplateManager.getFinalTemplates(EmailTemplateManager.TEMPLATES_PATH,
                        templateName, recipients);
                for (Contact recipient : recipients) {
                    emailManager.sendMail(recipient.getEmail(), subject, templates.get(recipient.getId()));
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
