package com.hbbsolution.owner.work_management.model.workmanager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tantr on 5/10/2017.
 */

public class Stakeholders implements Serializable{
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("request")
    @Expose
    private List<Object> request = null;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Object> getRequest() {
        return request;
    }

    public void setRequest(List<Object> request) {
        this.request = request;
    }
}
