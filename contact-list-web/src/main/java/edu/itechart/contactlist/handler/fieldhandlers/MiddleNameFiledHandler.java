package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.FieldHandlerException;
import edu.itechart.contactlist.util.Validator;
import org.apache.commons.lang3.StringUtils;

public class MiddleNameFiledHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) throws FieldHandlerException {
        if (StringUtils.isNotEmpty(field)) {
            if (Validator.checkMaxLength(field, 30) && Validator.containsLettersHyphenSpace(field)) {
                contact.setMiddleName(field);
            } else {
                throw new FieldHandlerException("Invalid middle name.");
            }
        } else {
            contact.setMiddleName(null);
        }
    }
}
