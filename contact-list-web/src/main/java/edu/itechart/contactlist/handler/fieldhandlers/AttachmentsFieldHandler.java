package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Attachment;
import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.handler.FieldHandlerException;
import edu.itechart.contactlist.util.JsonBuilder;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class AttachmentsFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) throws FieldHandlerException {
        try {
            ArrayList<Attachment> attachments = new JsonBuilder().getAttachmentList(field);
            contact.getAttachments().addAll(attachments);
        } catch (ParseException e) {
            throw new FieldHandlerException("Couldn't parse attachment list from json.", e);
        }
    }
}
