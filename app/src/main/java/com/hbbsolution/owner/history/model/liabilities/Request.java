
package com.hbbsolution.owner.history.model.liabilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Request implements Serializable {

    @SerializedName("maid")
    @Expose
    private String maid;
    @SerializedName("time")
    @Expose
    private String time;

    public String getMaid() {
        return maid;
    }

    public void setMaid(String maid) {
        this.maid = maid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
