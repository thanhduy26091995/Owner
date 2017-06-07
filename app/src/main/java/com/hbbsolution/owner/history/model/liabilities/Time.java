
package com.hbbsolution.owner.history.model.liabilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Time implements Serializable {

    @SerializedName("startAt")
    @Expose
    private String startAt;
    @SerializedName("endAt")
    @Expose
    private String endAt;
    @SerializedName("hour")
    @Expose
    private Integer hour;

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

}
