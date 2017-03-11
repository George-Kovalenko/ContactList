package edu.itechart.contactlist.entity;

public abstract class Entity {
    protected long id;

    public Entity() {}

    public Entity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
