package edu.itechart.contactlist.handler;

import edu.itechart.contactlist.handler.fieldhandlers.*;

import java.util.HashMap;

public class FieldHandlerFactory {
    private HashMap<String, FieldHandler> fields;

    public FieldHandlerFactory() {
        fields = new HashMap<>();
        fields.put("first-name", new FirstNameFieldHandler());
        fields.put("last-name", new LastNameFieldHandler());
        fields.put("middle-name", new MiddleNameFiledHanlder());
        fields.put("birth-date", new BirthDateFieldHandler());
        fields.put("gender", new GenderFieldHandler());
        fields.put("nationality", new NationalityFieldHandler());
        fields.put("marital-status", new MaritalStatusFieldHandler());
        fields.put("website", new WebsiteFieldHandler());
        fields.put("email", new EmailFieldHandler());
        fields.put("job", new JobFieldHandler());
        fields.put("country", new CountryFieldHandler());
        fields.put("city", new CityFieldHandler());
        fields.put("street", new StreetFieldHandler());
        fields.put("house", new HouseFieldHandler());
        fields.put("flat", new FlatFieldHandler());
        fields.put("postcode", new PostcodeFieldHandler());
        fields.put("phones", new PhonesFieldHandler());
        fields.put("new-phones", new NewPhonesFieldHandler());
        fields.put("attachments", new AttachmentsFieldHandler());
        fields.put("new-attachments", new NewAttachmentsFieldHandler());
    }

    public FieldHandler getFieldHandler(String key) {
        return fields.get(key);
    }
}
