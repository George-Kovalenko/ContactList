package edu.itechart.contactlist.entity;

public class MaritalStatus extends Entity {
    private String name;

    public MaritalStatus() {
    }

    public MaritalStatus(long id, String name) {
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
