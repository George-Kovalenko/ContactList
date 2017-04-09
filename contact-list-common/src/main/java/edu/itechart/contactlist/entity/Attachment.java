package edu.itechart.contactlist.entity;

import java.sql.Date;

public class Attachment extends Entity {
    private String fileName;
    private Date uploadDate;
    private String comment;
    private long contactId;

    public Attachment() {
    }

    public Attachment(long id, String fileName, Date uploadDate, String comment, long contactId) {
        super(id);
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.comment = comment;
        this.contactId = contactId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachment)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Attachment that = (Attachment) o;
        if (getContactId() != that.getContactId()) {
            return false;
        }
        if (getFileName() != null ? !getFileName().equals(that.getFileName()) : that.getFileName() != null) {
            return false;
        }
        if (getUploadDate() != null ? !getUploadDate().equals(that.getUploadDate()) : that.getUploadDate() != null) {
            return false;
        }
        return getComment() != null ? getComment().equals(that.getComment()) : that.getComment() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getFileName() != null ? getFileName().hashCode() : 0);
        result = 31 * result + (getUploadDate() != null ? getUploadDate().hashCode() : 0);
        result = 31 * result + (getComment() != null ? getComment().hashCode() : 0);
        result = 31 * result + (int) (getContactId() ^ (getContactId() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "fileName='" + fileName + '\'' +
                ", uploadDate=" + uploadDate +
                ", comment='" + comment + '\'' +
                ", contactId=" + contactId +
                "} " + super.toString();
    }
}
