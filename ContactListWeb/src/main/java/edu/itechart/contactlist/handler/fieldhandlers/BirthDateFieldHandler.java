package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;

import java.sql.Date;

public class BirthDateFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) {
        Date date = Date.valueOf(field);
        contact.setBirthDate(date);
    }
}
