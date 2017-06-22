package com.hbbsolution.owner.work_management.model.billGv24;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hbbsolution.owner.work_management.model.chekout.DataBill;

import java.util.List;

/**
 * Created by tantr on 6/21/2017.
 */

public class BillGv24Response {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Object> data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
