
package com.hbbsolution.owner.history.model.liabilities;

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
    private List<Request> request = null;

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

    public List<Request> getRequest() {
        return request;
    }

    public void setRequest(List<Request> request) {
        this.request = request;
    }

}
