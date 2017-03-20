package edu.itechart.contactlist.entity;

import java.sql.Timestamp;

public class Attachment extends Entity {
    private String fileName;
    private Timestamp uploadDate;
    private String comment;
    private long contactID;

    public Attachment() {
    }

    public Attachment(long id, String fileName, Timestamp uploadDate, String comment, long contactID) {
        super(id);
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.comment = comment;
        this.contactID = contactID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getContactID() {
        return contactID;
    }

    public void setContactID(long contactID) {
        this.contactID = contactID;
    }
}
