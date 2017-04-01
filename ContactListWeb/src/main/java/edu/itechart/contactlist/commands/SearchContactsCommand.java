package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entity.DateSearchType;
import edu.itechart.contactlist.entity.SearchParameters;
import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;
import org.apache.commons.lang3.StringUtils;

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
        String birthDate = request.getParameter("birth-date");
        searchParameters.setBirthDate(StringUtils.isNotEmpty(birthDate) ? Date.valueOf(birthDate) : null);
        DateSearchType dateSearchType = DateSearchType.valueOf(request.getParameter("date-params").toUpperCase());
        searchParameters.setDateSearchType(dateSearchType);
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
}
