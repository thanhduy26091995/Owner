
package com.hbbsolution.owner.history.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Stakeholders implements Serializable {

    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("received")
    @Expose
    private Received received;
    @SerializedName("request")
    @Expose
    private List<Object> request = null;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Received getReceived() {
        return received;
    }

    public void setReceived(Received received) {
        this.received = received;
    }

    public List<Object> getRequest() {
        return request;
    }

    public void setRequest(List<Object> request) {
        this.request = request;
    }

}
