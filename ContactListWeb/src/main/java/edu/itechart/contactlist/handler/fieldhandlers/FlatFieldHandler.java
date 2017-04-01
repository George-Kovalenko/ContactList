package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Address;
import edu.itechart.contactlist.entity.Contact;

public class FlatFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) {
        Address address = contact.getAddress();
        address.setFlatNumber(field);
    }
}
