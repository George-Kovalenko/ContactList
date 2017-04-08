package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.FieldHandlerException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.sql.Date;

public class MonthFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) throws FieldHandlerException {
        if (StringUtils.isNotEmpty(field)) {
            try {
                int month = Integer.parseInt(field);
                if (month != 0) {
                    if (contact.getBirthDate() == null) {
                        contact.setBirthDate(new Date(0));
                    }
                    contact.setBirthDate(setMonth(contact.getBirthDate(), month));
                } else {
                    contact.setBirthDate(null);
                }
            } catch (NumberFormatException e) {
                throw new FieldHandlerException("Invalid month of birth.", e);
            }
        } else {
            contact.setBirthDate(null);
        }
    }

    private Date setMonth(Date date, int month) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.withMonthOfYear(month);
        return new Date(dateTime.getMillis());
    }
}
