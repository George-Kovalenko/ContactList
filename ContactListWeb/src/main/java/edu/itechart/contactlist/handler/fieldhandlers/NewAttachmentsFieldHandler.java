package edu.itechart.contactlist.handler.fieldhandlers;

import edu.itechart.contactlist.entity.Attachment;
import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.util.JsonBuilder;

import java.util.ArrayList;

public class NewAttachmentsFieldHandler implements FieldHandler {
    @Override
    public void handleInputField(Contact contact, String field) {
        ArrayList<Attachment> attachments = new JsonBuilder().getAttachmentList(field);
        contact.getAttachments().addAll(attachments);
    }
}
