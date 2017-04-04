package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.FieldHandlerException;
import edu.itechart.contactlist.util.Validator;
import org.apache.commons.lang3.StringUtils;

public class FirstNameFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) throws FieldHandlerException {
        if (StringUtils.isNotEmpty(field)) {
            if (Validator.checkMaxLength(field, 30) && Validator.containsLettersHyphen(field)) {
                contact.setFirstName(field);
            } else {
                throw new FieldHandlerException("Invalid first name.");
            }
        } else {
            throw new FieldHandlerException("Empty first name.");
        }
    }
}
