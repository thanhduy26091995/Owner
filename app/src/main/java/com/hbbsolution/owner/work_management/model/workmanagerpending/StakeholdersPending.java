package com.hbbsolution.owner.work_management.model.workmanagerpending;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hbbsolution.owner.history.model.Received;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.work_management.model.maid.Request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tantr on 5/25/2017.
 */

public class StakeholdersPending implements Serializable{

    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("received")
    @Expose
    private Maid madi;

    public Maid getMadi() {
        return madi;
    }

    public void setMadi(Maid madi) {
        this.madi = madi;
    }
    //    @SerializedName("request")
//    @Expose
//    private List<Request> request = null;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


//    public List<Request> getRequest() {
//        return request;
//    }
//
//    public void setRequest(List<Request> request) {
//        this.request = request;
//    }
}
