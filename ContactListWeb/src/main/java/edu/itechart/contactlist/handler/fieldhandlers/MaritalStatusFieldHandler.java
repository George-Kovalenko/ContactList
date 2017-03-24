package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;

public class MaritalStatusFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) {
        Integer id = Integer.parseInt(field);
        if (id != 0) {
            contact.setMaritalStatus(id);
        } else {
            contact.setMaritalStatus(null);
        }
    }
}
