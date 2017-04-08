package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.FieldHandlerException;

public interface FieldHandler {
    void handleInputField(Contact contact, String field) throws FieldHandlerException;
}
