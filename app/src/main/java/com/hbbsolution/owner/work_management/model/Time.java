package com.hbbsolution.owner.work_management.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tantr on 5/10/2017.
 */

public class Time {
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
