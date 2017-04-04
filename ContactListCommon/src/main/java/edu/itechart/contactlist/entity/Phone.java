package edu.itechart.contactlist.entity;

public class Phone extends Entity {
    private String countryCode;
    private String operatorCode;
    private String number;
    private int phoneType;
    private String comment;
    private long contactID;

    public Phone() {
    }

    public Phone(long id, String countryCode, String operatorCode, String number, int phoneType, String comment,
                 long contactID) {
        super(id);
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.number = number;
        this.phoneType = phoneType;
        this.comment = comment;
        this.contactID = contactID;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(int phoneType) {
        this.phoneType = phoneType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phone)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Phone phone = (Phone) o;
        if (getPhoneType() != phone.getPhoneType()) {
            return false;
        }
        if (getContactID() != phone.getContactID()) {
            return false;
        }
        if (getCountryCode() != null ? !getCountryCode().equals(phone.getCountryCode()) :
                phone.getCountryCode() != null) {
            return false;
        }
        if (getOperatorCode() != null ? !getOperatorCode().equals(phone.getOperatorCode()) :
                phone.getOperatorCode() != null) {
            return false;
        }
        if (getNumber() != null ? !getNumber().equals(phone.getNumber()) : phone.getNumber() != null) {
            return false;
        }
        return getComment() != null ? getComment().equals(phone.getComment()) : phone.getComment() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getCountryCode() != null ? getCountryCode().hashCode() : 0);
        result = 31 * result + (getOperatorCode() != null ? getOperatorCode().hashCode() : 0);
        result = 31 * result + (getNumber() != null ? getNumber().hashCode() : 0);
        result = 31 * result + getPhoneType();
        result = 31 * result + (getComment() != null ? getComment().hashCode() : 0);
        result = 31 * result + (int) (getContactID() ^ (getContactID() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "countryCode='" + countryCode + '\'' +
                ", operatorCode='" + operatorCode + '\'' +
                ", number='" + number + '\'' +
                ", phoneType=" + phoneType +
                ", comment='" + comment + '\'' +
                ", contactID=" + contactID +
                "} " + super.toString();
    }
}
