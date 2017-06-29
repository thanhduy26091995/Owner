package com.hbbsolution.owner.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by buivu on 29/06/2017.
 */

public class AnnouncementResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
