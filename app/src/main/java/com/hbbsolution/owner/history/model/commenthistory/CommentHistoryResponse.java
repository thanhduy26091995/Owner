
package com.hbbsolution.owner.history.model.commenthistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentHistoryResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DataComment data;

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

    public DataComment getData() {
        return data;
    }

    public void setData(DataComment data) {
        this.data = data;
    }

}
