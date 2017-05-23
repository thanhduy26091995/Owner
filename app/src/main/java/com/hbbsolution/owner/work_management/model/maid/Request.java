package com.hbbsolution.owner.work_management.model.maid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tantr on 5/18/2017.
 */

public class Request implements Serializable{
    @SerializedName("maid")
    @Expose
    private MaidInfo maid;
    @SerializedName("time")
    @Expose
    private String time;

    public MaidInfo getMaid() {
        return maid;
    }

    public void setMaid(MaidInfo maid) {
        this.maid = maid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
