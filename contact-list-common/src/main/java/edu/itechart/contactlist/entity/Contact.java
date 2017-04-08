package edu.itechart.contactlist.entity;

import java.sql.Date;
import java.util.ArrayList;

public class Contact extends Entity {
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private String nationality;
    private String gender;
    private Integer maritalStatus;
    private String website;
    private String email;
    private String job;
    private Address address;
    private ArrayList<Phone> phones;
    private ArrayList<Attachment> attachments;

    public Contact() {
        address = new Address();
        phones = new ArrayList<>();
        attachments = new ArrayList<>();
    }

    public Contact(long id, String firstName, String lastName, String middleName, Date birthDate, String nationality,
                   String gender, Integer maritalStatus, String website, String email, String job) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.website = website;
        this.email = email;
        this.job = job;
    }

    public Contact(long id, String firstName, String lastName, String middleName, Date birthDate, String nationality,
                   String gender, Integer maritalStatus, String website, String email, String job, Address address,
                   ArrayList<Phone> phones, ArrayList<Attachment> attachments) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.website = website;
        this.email = email;
        this.job = job;
        this.address = address;
        this.phones = phones;
        this.attachments = attachments;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
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

    public ArrayList<Phone> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<Phone> phones) {
        this.phones = phones;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthDate=" + birthDate +
                ", nationality='" + nationality + '\'' +
                ", gender='" + gender + '\'' +
                ", maritalStatus=" + maritalStatus +
                ", website='" + website + '\'' +
                ", email='" + email + '\'' +
                ", job='" + job + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                ", attachments=" + attachments +
                "} " + super.toString();
    }
}
