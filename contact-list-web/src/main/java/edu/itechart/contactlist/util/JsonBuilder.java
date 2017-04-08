package edu.itechart.contactlist.util;

import edu.itechart.contactlist.entity.Attachment;
import edu.itechart.contactlist.entity.Phone;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Date;
import java.util.ArrayList;

public class JsonBuilder {
    public ArrayList<Phone> getPhoneList(String jsonPhones) throws ParseException {
        ArrayList<Phone> phones = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(jsonPhones);
        for (Object object : array) {
            JSONObject jsonObject = (JSONObject) object;
            Phone phone = new Phone();
            phone.setId(Long.parseLong(jsonObject.get("id").toString()));
            phone.setCountryCode((String) jsonObject.get("countryCode"));
            phone.setOperatorCode((String) jsonObject.get("operatorCode"));
            phone.setNumber((String) jsonObject.get("number"));
            phone.setPhoneType(Integer.parseInt(jsonObject.get("type").toString()));
            phone.setComment((String) jsonObject.get("comment"));
            phone.setContactID(Long.parseLong(jsonObject.get("contactId").toString()));
            phones.add(phone);
        }
        return phones;
    }

    public ArrayList<Attachment> getAttachmentList(String jsonAttachment) throws ParseException {
        ArrayList<Attachment> attachments = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(jsonAttachment);
        for (Object object : array) {
            JSONObject jsonObject = (JSONObject) object;
            Attachment attachment = new Attachment();
            attachment.setId((Long.parseLong(jsonObject.get("id").toString())));
            attachment.setFileName((String) jsonObject.get("fileName"));
            attachment.setUploadDate(Date.valueOf(jsonObject.get("uploadDate").toString()));
            attachment.setComment((String) jsonObject.get("comment"));
            attachment.setContactID(Long.parseLong(jsonObject.get("contactId").toString()));
            attachments.add(attachment);
        }

        return attachments;
    }
}
