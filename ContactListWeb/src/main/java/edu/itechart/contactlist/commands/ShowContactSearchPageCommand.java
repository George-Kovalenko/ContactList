package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.MaritalStatus;
import edu.itechart.contactlist.service.MaritalStatusService;
import edu.itechart.contactlist.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowContactSearchPageCommand implements Command {
    private static final String REQUEST_ATTR__MARITAL_STATUSES = "maritalStatuses";
    private static final String URL_CONTACT_SEARCH = "/contact_search.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            ArrayList<MaritalStatus> maritalStatuses = MaritalStatusService.findAll();
            request.setAttribute(REQUEST_ATTR__MARITAL_STATUSES, maritalStatuses);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return URL_CONTACT_SEARCH;
    }
}
