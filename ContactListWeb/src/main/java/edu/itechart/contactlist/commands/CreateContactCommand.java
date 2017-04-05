package edu.itechart.contactlist.commands;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.MainHandler;
import edu.itechart.contactlist.service.ContactService;
import edu.itechart.contactlist.service.ServiceException;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class CreateContactCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateContactCommand.class);
    private static final String REQUEST_ATTR_CONTACT = "contact";
    private static final String REQUEST_ATTR_ATTACHMENTS = "attachmentsForUpload";
    private static final String REQUEST_ATTR_PHOTO = "photoForUpload";
    private static final String URL_CONTACT_LIST = "controller?command=show_contact_list";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        MainHandler mainHandler = new MainHandler();
        mainHandler.handleInputFields(request);
        try {
            Contact contact = (Contact) request.getAttribute(REQUEST_ATTR_CONTACT);
            ArrayList<FileItem> fileItems = (ArrayList<FileItem>) request.getAttribute(REQUEST_ATTR_ATTACHMENTS);
            FileItem photo = (FileItem) request.getAttribute(REQUEST_ATTR_PHOTO);
            LOGGER.info("Creating contact with params {}", contact);
            ContactService.insert(contact, fileItems, photo);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return URL_CONTACT_LIST;
    }

    @Override
    public boolean needsRedirect() {
        return true;
    }
}
