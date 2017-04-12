package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.dto.DateSearchType;
import edu.itechart.contactlist.dto.SearchParameters;
import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;
import edu.itechart.contactlist.util.SearchParametersUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

public class SearchContactsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchContactsCommand.class);
    private static final String REQUEST_ATTR_CONTACT = "contacts";
    private static final String REQUEST_ATTR_SEARCH_PARAMETERS = "searchParameters";
    private static final String URL_CONTACT_LIST = "contact_list.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.setFirstName(request.getParameter("first-name"));
        searchParameters.setLastName(request.getParameter("last-name"));
        searchParameters.setMiddleName(request.getParameter("middle-name"));
        searchParameters.setNationality(request.getParameter("nationality"));
        searchParameters.setGender(request.getParameter("gender"));
        String maritalStatus = request.getParameter("marital-status");
        searchParameters.setMaritalStatus(StringUtils.isNotEmpty(maritalStatus) ? Integer.parseInt(maritalStatus)
                : null);
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
        LOGGER.info("Search contacts with params {}", searchParameters);
        try {
            ArrayList<Contact> contacts = ContactService.findAllByParameters(searchParameters);
            Map<String, String> parametersMap = SearchParametersUtils.searchParamsToMap(searchParameters);
            request.setAttribute(REQUEST_ATTR_CONTACT, contacts);
            request.setAttribute(REQUEST_ATTR_SEARCH_PARAMETERS, parametersMap);
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
            LOGGER.error("Can't parse date from request {}", e);
        }
        return null;
    }
}
