package edu.itechart.contactlist.util.email;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.stringtemplate.v4.ST;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

public class SendEmailJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            ArrayList<Contact> contacts = ContactService.findByBirthDate();
            sendEmail(contacts);
        } catch (ServiceException | MessagingException e) {
            throw new JobExecutionException(e);
        }
    }

    private void sendEmail(ArrayList<Contact> contacts) throws MessagingException {
        EmailManager emailManager = new EmailManager();
        String text = getEmailText(contacts);
        String email = ResourceBundle.getBundle("emailconfig").getObject("schedule_email_address").toString();
        String subject = ResourceBundle.getBundle("emailconfig").getObject("schedule_email_subject").toString();
        emailManager.sendMail(email, subject, text);
    }

    private String getEmailText(ArrayList<Contact> contacts) {
        Map<String, ST> templates = EmailTemplateManager.getTemplates(EmailTemplateManager.SCHEDULE_TEMPLATES_PATH);
        StringBuilder text = new StringBuilder();
        if (CollectionUtils.isNotEmpty(contacts)) {
            text.append(templates.get("startLine").render());
            Collection<String> templateValues
                    = EmailTemplateManager.getFinalTemplates(EmailTemplateManager.SCHEDULE_TEMPLATES_PATH, "contact",
                    contacts).values();
            templateValues.forEach(text::append);
        } else {
            text.append(templates.get("noBirthday").render());
        }
        return text.toString();
    }
}
