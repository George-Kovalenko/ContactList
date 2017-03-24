package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Address;
import edu.itechart.contactlist.entity.Contact;

public class CountryFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) {
        Address address = contact.getAddress();
        address.setCountry(field);
    }
}
