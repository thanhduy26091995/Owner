
package com.hbbsolution.owner.history.model.helper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hbbsolution.owner.history.model.workhistory.Received;

import java.io.Serializable;
import java.util.List;

public class MaidHistory implements Serializable {

    @SerializedName("_id")
    @Expose
    private Received id;
    @SerializedName("times")
    @Expose
    private List<String> times = null;

    public Received getId() {
        return id;
    }

    public void setId(Received id) {
        this.id = id;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

}
