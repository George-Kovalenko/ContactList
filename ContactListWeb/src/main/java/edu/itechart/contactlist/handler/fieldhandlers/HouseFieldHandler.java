package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Address;
import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.FieldHandlerException;
import edu.itechart.contactlist.util.Validator;
import org.apache.commons.lang3.StringUtils;

public class HouseFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) throws FieldHandlerException {
        Address address = contact.getAddress();
        if (StringUtils.isNotEmpty(field)) {
            if (Validator.checkMaxLength(field, 10) && Validator.isNumber(field)) {
                address.setHouseNumber(field);
            } else {
                throw new FieldHandlerException("Invalid house number.");
            }
        } else {
            address.setHouseNumber(null);
        }
    }
}
