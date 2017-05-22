package com.hbbsolution.owner.work_management.model.maid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tantr on 5/18/2017.
 */

public class DatumMaid implements Serializable{
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("request")
    @Expose
    private List<Request> request = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Request> getRequest() {
        return request;
    }

    public void setRequest(List<Request> request) {
        this.request = request;
    }
}
