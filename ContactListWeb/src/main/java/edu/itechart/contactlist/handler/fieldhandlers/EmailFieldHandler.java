package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;

public class EmailFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) {
        contact.setEmail(field);
    }
}
