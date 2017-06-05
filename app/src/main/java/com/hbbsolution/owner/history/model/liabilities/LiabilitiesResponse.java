
package com.hbbsolution.owner.history.model.liabilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hbbsolution.owner.history.model.workhistory.WorkHistory;

import java.util.List;

public class LiabilitiesResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<WorkHistory> data = null;

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

    public List<WorkHistory> getData() {
        return data;
    }

    public void setData(List<WorkHistory> data) {
        this.data = data;
    }

}
