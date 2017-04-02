package edu.itechart.contactlist.util.email;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SendEmailJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            ArrayList<Contact> contacts = getContacts();
            sendEmail(contacts);
        } catch (MessagingException e) {
            throw new JobExecutionException(e);
        }
    }

    private ArrayList<Contact> getContacts() {
        ArrayList<Contact> contactsWithBirthdayToday = new ArrayList<>();
        try {
            contactsWithBirthdayToday = ContactService.findByBirthDate();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return contactsWithBirthdayToday;
    }

    private void sendEmail(ArrayList<Contact> contacts) throws MessagingException {
        EmailManager emailManager = new EmailManager();
        String text = getEmailText(contacts);
        String email = ResourceBundle.getBundle("emailconfig").getObject("schedule_email_address").toString();
        String subject = ResourceBundle.getBundle("emailconfig").getObject("schedule_email_subject").toString();
        emailManager.sendMail(email, subject, text);
    }

    private String getEmailText(ArrayList<Contact> contacts) {
        ST st;
        String text;
        if (!contacts.isEmpty()) {
            text = getBirthdayTemplate("startLine").render() + "\n";
            for (Contact contact : contacts) {
                st = getBirthdayTemplate("contact");
                st.add("first_name", contact.getFirstName());
                st.add("last_name", contact.getLastName());
                text += st.render() + "\n";
            }
        } else {
            text = getBirthdayTemplate("noBirthday").render();
        }
        return text;
    }

    private ST getBirthdayTemplate(String templateName) {
        String path = ResourceBundle.getBundle("emailconfig").getObject("schedule_template_file_path").toString();
        STGroup stGroup;
        stGroup = new STGroupFile(path);
        return stGroup.getInstanceOf(templateName);
    }
}
