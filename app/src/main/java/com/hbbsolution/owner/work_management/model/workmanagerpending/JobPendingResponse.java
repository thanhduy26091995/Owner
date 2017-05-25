package com.hbbsolution.owner.work_management.model.workmanagerpending;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;

import java.util.List;

/**
 * Created by tantr on 5/25/2017.
 */

public class JobPendingResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<DatumPending> data = null;

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

    public List<DatumPending> getData() {
        return data;
    }

    public void setData(List<DatumPending> data) {
        this.data = data;
    }
}
