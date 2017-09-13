package com.hbbsolution.owner.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by buivu on 13/09/2017.
 */

public class DefaultResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;

    public DefaultResponse() {
    }

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
