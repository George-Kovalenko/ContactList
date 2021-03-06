package edu.itechart.contactlist.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;

    default boolean needsRedirect() {
        return false;
    }
}
