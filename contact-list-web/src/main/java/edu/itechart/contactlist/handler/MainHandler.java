package edu.itechart.contactlist.handler;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.fieldhandlers.FieldHandler;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainHandler.class);

    public void handleInputFields(HttpServletRequest request) {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        ServletContext servletContext = request.getServletContext();
        File tempDir = new File(String.valueOf(servletContext.getAttribute("javax.servlet.context.tempdir")));
        diskFileItemFactory.setRepository(tempDir);
        try {
            List<FileItem> items = servletFileUpload.parseRequest(request);
            ArrayList<FileItem> attachmentForUpload = new ArrayList<>();
            FieldHandlerFactory factory = new FieldHandlerFactory();
            Contact contact = new Contact();
            for (FileItem item : items) {
                if (item.isFormField()) {
                    FieldHandler fieldHandler = factory.getFieldHandler(item.getFieldName());
                    if (fieldHandler != null) {
                        try {
                            fieldHandler.handleInputField(contact, StringUtils.trim(item.getString("UTF-8")));
                        } catch (FieldHandlerException | UnsupportedEncodingException e) {
                            LOGGER.error("Can't handle field {}", item.getFieldName(), e);
                        }
                    }
                } else if (StringUtils.startsWith(item.getFieldName(), "new-attachment-input-")) {
                    attachmentForUpload.add(item);
                } else if (StringUtils.equals(item.getFieldName(), "photo-field")) {
                    request.setAttribute("photoForUpload", item);
                } else if (StringUtils.equals(item.getFieldName(), "photo-field-delete")) {
                    request.setAttribute("photoForUpload", item);
                }
            }
            request.setAttribute("contact", contact);
            request.setAttribute("attachmentsForUpload", attachmentForUpload);
        } catch (FileUploadException e) {
            LOGGER.error("Can't parse request", e);
        }
    }
}
