package edu.itechart.contactlist.handler;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entity.Phone;
import edu.itechart.contactlist.handler.fieldhandlers.FieldHandler;
import edu.itechart.contactlist.util.JsonBuilder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainHandler {
    public void handleInputFields(HttpServletRequest request) {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        try {
            List<FileItem> items = servletFileUpload.parseRequest(request);
            FieldHandlerFactory factory = new FieldHandlerFactory();
            Contact contact = new Contact();
            for (FileItem item : items) {
                if (item.isFormField()) {
                    FieldHandler fieldHandler = factory.getFieldHandler(item.getFieldName());
                    if (fieldHandler != null) {
                        fieldHandler.handleInputField(contact, item.getString("UTF-8"));
                    }
                }
            }
            request.setAttribute("contact", contact);
        } catch (FileUploadException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
