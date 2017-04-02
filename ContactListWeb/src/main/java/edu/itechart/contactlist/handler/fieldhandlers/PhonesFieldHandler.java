package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entity.Phone;
import edu.itechart.contactlist.util.JsonBuilder;

import java.util.ArrayList;

public class PhonesFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) {
        ArrayList<Phone> phones = new JsonBuilder().getPhoneList(field);
        contact.getPhones().addAll(phones);
    }
}
