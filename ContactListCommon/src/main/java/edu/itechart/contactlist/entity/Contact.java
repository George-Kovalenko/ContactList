package edu.itechart.contactlist.entity;

import java.sql.Date;
import java.util.List;

public class Contact extends Entity {
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private String nationality;
    private char gender;
    private int martialStatus;
    private String website;
    private String email;
    private String job;
    private Address address;
    private List<Phone> phones;

    public Contact() {
    }

    public Contact(long id, String firstName, String lastName, String middleName, Date birthDate, String nationality,
                   char gender, int martialStatus, String website, String email, String job, Address address,
                   List<Phone> phones) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.gender = gender;
        this.martialStatus = martialStatus;
        this.website = website;
        this.email = email;
        this.job = job;
        this.address = address;
        this.phones = phones;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(int martialStatus) {
        this.martialStatus = martialStatus;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
}
