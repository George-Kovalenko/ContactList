package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entity.DateSearchType;
import edu.itechart.contactlist.entity.SearchParameters;
import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;

public class SearchContactsCommand implements Command {
    private static final String URL_CONTACT_LIST = "/contact_list.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.setFirstName(request.getParameter("first-name"));
        searchParameters.setLastName(request.getParameter("last-name"));
        searchParameters.setMiddleName(request.getParameter("middle-name"));
        searchParameters.setNationality(request.getParameter("nationality"));
        searchParameters.setGender(request.getParameter("gender"));
        String maritalStatus = request.getParameter("marital-status");
        searchParameters.setMaritalStatus(StringUtils.isNotEmpty(maritalStatus) ? Integer.parseInt(maritalStatus) : 0);
        String day = request.getParameter("birth-date-day");
        String month = request.getParameter("birth-date-month");
        String year = request.getParameter("birth-date-year");
        if (StringUtils.isNotEmpty(day) && StringUtils.isNotEmpty(month) && StringUtils.isNotEmpty(year)) {
            searchParameters.setBirthDate(getDateFromRequest(day, month, year));
            DateSearchType type = DateSearchType.valueOf(request.getParameter("date-params").toUpperCase());
            searchParameters.setDateSearchType(type);
        }
        searchParameters.setCountry(request.getParameter("country"));
        searchParameters.setCity(request.getParameter("city"));
        searchParameters.setStreet(request.getParameter("street"));
        searchParameters.setHouseNumber(request.getParameter("house"));
        searchParameters.setFlatNumber(request.getParameter("flat"));
        searchParameters.setPostcode(request.getParameter("postcode"));
        try {
            ArrayList<Contact> contacts = ContactService.findAllByParameters(searchParameters);
            request.setAttribute("contacts", contacts);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return URL_CONTACT_LIST;
    }

    private Date getDateFromRequest(String stringDay, String stringMonth, String stringYear) {
        try {
            int day = Integer.parseInt(stringDay);
            int month = Integer.parseInt(stringMonth);
            int year = Integer.parseInt(stringYear);
            DateTime dateTime = new DateTime();
            dateTime = dateTime.withDayOfMonth(day);
            dateTime = dateTime.withMonthOfYear(month);
            dateTime = dateTime.withYear(year);
            return new Date(dateTime.getMillis());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
