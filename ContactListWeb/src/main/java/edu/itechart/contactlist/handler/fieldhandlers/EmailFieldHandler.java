package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.FieldHandlerException;
import edu.itechart.contactlist.util.Validator;
import org.apache.commons.lang3.StringUtils;

public class EmailFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) throws FieldHandlerException {
        if (StringUtils.isNotEmpty(field)) {
            if (Validator.checkMaxLength(field, 45) && Validator.isEmail(field)) {
                contact.setEmail(field);
            } else {
                throw new FieldHandlerException("Invalid email.");
            }
        } else {
            contact.setEmail(null);
        }
    }
}
