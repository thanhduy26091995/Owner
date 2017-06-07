package com.hbbsolution.owner.more.viet_pham.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 6/5/2017.
 */

public class UpdateResponse {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private String message;
//    @SerializedName("data")
//    @Expose
//    private List<Data> data;
//
//    public UpdateResponse()
//    {
//
//    }
//
//    public UpdateResponse(boolean status, String message, List<Data> data) {
//        this.status = status;
//        this.message = message;
//        this.data = data;
//    }

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

//    public List<Data> getData() {
//        return data;
//    }
//
//    public void setData(List<Data> data) {
//        this.data = data;
//    }
}
