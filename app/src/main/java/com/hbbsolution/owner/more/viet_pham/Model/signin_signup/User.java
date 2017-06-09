package com.hbbsolution.owner.more.viet_pham.Model.signin_signup;

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
    @SerializedName("evaluation_point")
    @Expose
    private double evaluation_point;
    @SerializedName("wallet")
    @Expose
    private double wallet;

    public User() {

    }

    public User(String _id, Info info, double evaluation_point, double wallet) {
        this._id = _id;
        this.info = info;
        this.evaluation_point = evaluation_point;
        this.wallet = wallet;
    }

    public double getEvaluation_point() {
        return evaluation_point;
    }

    public void setEvaluation_point(double evaluation_point) {
        this.evaluation_point = evaluation_point;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
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
