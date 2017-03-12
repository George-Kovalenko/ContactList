package edu.itechart.contactlist.entity;

public class Country extends Entity {
    private String name;

    public Country() {
    }

    public Country(long id, String name) {
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
