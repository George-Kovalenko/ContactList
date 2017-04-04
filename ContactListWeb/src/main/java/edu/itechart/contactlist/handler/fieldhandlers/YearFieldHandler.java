package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.FieldHandlerException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.Date;

public class YearFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) throws FieldHandlerException {
        if (StringUtils.isNotEmpty(field)) {
            try {
                int year = Integer.parseInt(field);
                if (year != 0) {
                    if (contact.getBirthDate() == null) {
                        contact.setBirthDate(new Date(0));
                    }
                    contact.setBirthDate(setYear(contact.getBirthDate(), year));
                } else {
                    contact.setBirthDate(null);
                }
            } catch (NumberFormatException e) {
                throw new FieldHandlerException("Invalid year of birth.", e);
            }
        } else {
            contact.setBirthDate(null);
        }
    }

    private Date setYear(Date date, int year) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.withYear(year);
        return new Date(dateTime.getMillis());
    }
}
