package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Address;
import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.FieldHandlerException;
import edu.itechart.contactlist.util.Validator;
import org.apache.commons.lang3.StringUtils;

public class CityFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) throws FieldHandlerException {
        Address address = contact.getAddress();
        if (StringUtils.isNotEmpty(field)) {
            if (Validator.checkMaxLength(field, 45) && Validator.containsLettersHyphenSpace(field)) {
                address.setCity(field);
            } else {
                throw new FieldHandlerException("Invalid city name.");
            }
        } else {
            address.setCity(null);
        }
    }
}
