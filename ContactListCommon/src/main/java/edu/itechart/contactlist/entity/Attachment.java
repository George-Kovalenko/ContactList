package edu.itechart.contactlist.entity;

import java.sql.Timestamp;

public class Attachment extends Entity {
    private String filePath;
    private String fileName;
    private Timestamp uploadDate;
    private String comment;

    public Attachment() {
    }

    public Attachment(long id, String filePath, String fileName, Timestamp uploadDate, String comment) {
        super(id);
        this.filePath = filePath;
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.comment = comment;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
}
