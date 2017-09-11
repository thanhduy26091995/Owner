package com.hbbsolution.owner.model;

/**
 * Created by buivu on 23/08/2017.
 */

public class Item {
    private String id;
    private String title;
    private Object object;

    public Item() {
    }

    public Item(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Item(String id, String title, Object object) {
        this.id = id;
        this.title = title;
        this.object = object;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
