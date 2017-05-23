package com.hbbsolution.owner.more.viet_pham.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 5/22/2017.
 */

public class User {
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("info")
    @Expose
    private Info info;

    public User() {

    }

    public User(String _id, Info info) {
        this._id = _id;
        this.info = info;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
