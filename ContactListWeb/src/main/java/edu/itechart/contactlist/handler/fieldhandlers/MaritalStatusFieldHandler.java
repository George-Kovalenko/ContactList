package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.FieldHandlerException;

public class MaritalStatusFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) throws FieldHandlerException {
        try {
            Integer id = Integer.parseInt(field);
            if (id != 0) {
                contact.setMaritalStatus(id);
            } else {
                contact.setMaritalStatus(null);
            }
        } catch (NumberFormatException e) {
            throw new FieldHandlerException("Invalid marital status", e);
        }
    }
}
