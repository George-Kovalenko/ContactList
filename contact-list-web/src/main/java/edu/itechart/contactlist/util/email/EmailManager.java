package edu.itechart.contactlist.util.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmailManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailManager.class);
    private static String senderEmail =
            ResourceBundle.getBundle("emailconfig").getObject("sender_name").toString();
    private static String senderPassword =
            ResourceBundle.getBundle("emailconfig").getObject("sender_password").toString();
    private Session session;

    public EmailManager() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }

    public void sendMail(String email, String subject, String text) throws MessagingException {
        LOGGER.info("Send email to {} with subject = {} and text = {}", email, subject, text);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);
    }
}
