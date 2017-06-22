package com.hbbsolution.owner.work_management.model.chekout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tantr on 6/21/2017.
 */

public class DataBill implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("date")
    @Expose
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
