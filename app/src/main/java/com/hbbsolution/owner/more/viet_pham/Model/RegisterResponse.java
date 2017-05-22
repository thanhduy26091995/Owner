package com.hbbsolution.owner.more.viet_pham.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 5/22/2017.
 */

public class RegisterResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public RegisterResponse()
    {

    }

    public RegisterResponse(String status, Data data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
