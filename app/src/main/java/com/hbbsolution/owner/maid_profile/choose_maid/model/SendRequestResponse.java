package com.hbbsolution.owner.maid_profile.choose_maid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by buivu on 30/05/2017.
 */

public class SendRequestResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;

    public SendRequestResponse() {
    }

    public SendRequestResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
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
