package com.hbbsolution.owner.model;

/**
 * Created by Administrator on 18/05/2017.
 */

public class Helper {
    public String img;
    public String name;
    public String salary;
    public float rating;

    public Helper() {
    }

    public Helper(String img, String name, String salary, float rating) {
        this.img = img;
        this.name = name;
        this.salary = salary;
        this.rating = rating;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
