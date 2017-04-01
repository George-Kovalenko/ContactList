package edu.itechart.contactlist.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowEmailPageCommand implements Command {
    private static final String URL_EMAIL_PAGE = "/email_page.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return URL_EMAIL_PAGE;
    }
}
