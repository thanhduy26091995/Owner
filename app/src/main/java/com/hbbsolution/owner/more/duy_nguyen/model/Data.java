
package com.hbbsolution.owner.more.duy_nguyen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("totalPrice")
    @Expose
    private Integer totalPrice;
    @SerializedName("task")
    @Expose
    private List<Task> task = null;
    @SerializedName("wallet")
    @Expose
    private Integer wallet;

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }

    public Integer getWallet() {
        return wallet;
    }

    public void setWallet(Integer wallet) {
        this.wallet = wallet;
    }

}
