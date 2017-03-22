package edu.itechart.contactlist.entity;

public class PhoneType extends Entity {
    private String name;

    public PhoneType() {
    }

    public PhoneType(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
