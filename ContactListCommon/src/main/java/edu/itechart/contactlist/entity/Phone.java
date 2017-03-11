package edu.itechart.contactlist.entity;

public class Phone extends Entity {
    private String countryCode;
    private String operatorCode;
    private String number;
    private char phoneType;
    private String comment;

    public Phone() {
    }

    public Phone(long id, String countryCode, String operatorCode, String number, char phoneType, String comment) {
        super(id);
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.number = number;
        this.phoneType = phoneType;
        this.comment = comment;
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

    public char getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(char phoneType) {
        this.phoneType = phoneType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
