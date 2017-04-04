package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entity.Phone;
import edu.itechart.contactlist.handler.FieldHandlerException;
import edu.itechart.contactlist.util.JsonBuilder;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class PhonesFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) throws FieldHandlerException {
        try {
            ArrayList<Phone> phones = new JsonBuilder().getPhoneList(field);
            contact.getPhones().addAll(phones);
        } catch (ParseException e) {
            throw new FieldHandlerException("Couldn't parse phone list from json.", e);
        }
    }
}
