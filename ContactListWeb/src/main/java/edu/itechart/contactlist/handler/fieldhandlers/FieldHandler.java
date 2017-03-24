package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;

public interface FieldHandler {
    void handleInputField(Contact contact, String field);
}
