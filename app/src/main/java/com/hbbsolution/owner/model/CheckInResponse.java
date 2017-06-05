package com.hbbsolution.owner.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by buivu on 01/06/2017.
 */

public class CheckInResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private String data;

    public CheckInResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
