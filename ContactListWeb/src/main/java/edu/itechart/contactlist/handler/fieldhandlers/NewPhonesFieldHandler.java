package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entity.Phone;
import edu.itechart.contactlist.util.JsonBuilder;

import java.util.ArrayList;

public class NewPhonesFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) {
        ArrayList<Phone> newPhones = new JsonBuilder().getPhoneList(field);
        if (newPhones != null) {
            contact.getPhones().addAll(newPhones);
        }
    }
}
