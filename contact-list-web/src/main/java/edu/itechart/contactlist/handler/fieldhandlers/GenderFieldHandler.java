package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.util.Validator;

public class GenderFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) {
        if (Validator.isGender(field)) {
            contact.setGender(field);
        } else {
            contact.setGender(null);
        }
    }
}
