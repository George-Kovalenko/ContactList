package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.Date;

public class DayFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) {
        if (StringUtils.isNotEmpty(field)) {
            try {
                int day = Integer.parseInt(field);
                if (day != 0) {
                    if (contact.getBirthDate() == null) {
                        contact.setBirthDate(new Date(0));
                    }
                    contact.setBirthDate(setDay(contact.getBirthDate(), day));
                } else {
                    contact.setBirthDate(null);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            contact.setBirthDate(null);
        }
    }

    private Date setDay(Date date, int day) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.withDayOfMonth(day);
        return new Date(dateTime.getMillis());
    }
}
