package com.hbbsolution.owner.more.viet_pham.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 5/22/2017.
 */

public class Info {
    @SerializedName("gender")
    @Expose
    private int gender;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("image")
    @Expose
    private String image;

    public Info() {

    }

    public Info(int gender, Address address, String name, String phone, String email, String username, String image) {
        this.gender = gender;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.image = image;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
