package com.hbbsolution.owner.work_management.model.listcommentmaid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hbbsolution.owner.history.model.Address;
import com.hbbsolution.owner.work_management.model.workmanager.Time;

/**
 * Created by tantr on 5/22/2017.
 */

public class InfoTask {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("package")
    @Expose
    private String _package;
    @SerializedName("work")
    @Expose
    private String work;
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
    private Address address;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPackage() {
        return _package;
    }

    public void setPackage(String _package) {
        this._package = _package;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
