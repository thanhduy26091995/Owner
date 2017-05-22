
package com.hbbsolution.owner.history.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Info_ implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("package")
    @Expose
    private Package _package;
    @SerializedName("work")
    @Expose
    private Work work;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("tools")
    @Expose
    private Boolean tools;
    @SerializedName("time")
    @Expose
    private Time time;
    @SerializedName("address")
    @Expose
    private Address_ address;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Package getPackage() {
        return _package;
    }

    public void setPackage(Package _package) {
        this._package = _package;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getTools() {
        return tools;
    }

    public void setTools(Boolean tools) {
        this.tools = tools;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Address_ getAddress() {
        return address;
    }

    public void setAddress(Address_ address) {
        this.address = address;
    }

}
