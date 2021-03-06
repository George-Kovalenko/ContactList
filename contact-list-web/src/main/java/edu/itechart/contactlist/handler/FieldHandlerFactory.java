package edu.itechart.contactlist.handler;

import edu.itechart.contactlist.handler.fieldhandlers.*;

import java.util.HashMap;

public class FieldHandlerFactory {
    private HashMap<String, FieldHandler> fields;

    public FieldHandlerFactory() {
        fields = new HashMap<>();
        fields.put("first-name", new FirstNameFieldHandler());
        fields.put("last-name", new LastNameFieldHandler());
        fields.put("middle-name", new MiddleNameFiledHandler());
        fields.put("birth-date-day", new DayFieldHandler());
        fields.put("birth-date-month", new MonthFieldHandler());
        fields.put("birth-date-year", new YearFieldHandler());
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
        fields.put("new-phones", new PhonesFieldHandler());
        fields.put("attachments", new AttachmentsFieldHandler());
        fields.put("new-attachments", new AttachmentsFieldHandler());
    }

    public FieldHandler getFieldHandler(String key) {
        return fields.get(key);
    }
}
