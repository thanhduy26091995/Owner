
package com.hbbsolution.owner.history.model.helper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Datum implements Serializable {

    @SerializedName("_id")
    @Expose
    private Id id;
    @SerializedName("times")
    @Expose
    private List<String> times = null;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

}
