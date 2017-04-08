package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;
import edu.itechart.contactlist.util.pagination.Pagination;
import edu.itechart.contactlist.util.pagination.PaginationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowContactListCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowContactListCommand.class);
    private static final String REQUEST_PARAM_PAGE = "page";
    private static final String REQUEST_ATTR_CONTACTS = "contacts";
    private static final String REQUEST_ATTR_PAGINATION = "pagination";
    private static final String URL_CONTACT_LIST = "contact_list.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String page = request.getParameter(REQUEST_PARAM_PAGE);
            LOGGER.info("Show contact list with page = {}", page);
            int count = ContactService.getContactCount();
            PaginationManager paginationManager = new PaginationManager(page, count);
            Pagination pagination = paginationManager.getPagination();
            int offset = paginationManager.getOffset();
            int limit = paginationManager.getLimit();
            ArrayList<Contact> contacts = ContactService.findByOffset(offset, limit);
            request.setAttribute(REQUEST_ATTR_CONTACTS, contacts);
            request.setAttribute(REQUEST_ATTR_PAGINATION, pagination);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return URL_CONTACT_LIST;
    }
}
