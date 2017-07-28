package com.hbbsolution.owner.more.viet_pham.Model.signin_signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 7/28/2017.
 */

public class CheckUsernameEmailResponse {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Data> data;

    public CheckUsernameEmailResponse(boolean status, String message, List<Data> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public CheckUsernameEmailResponse(){}

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
