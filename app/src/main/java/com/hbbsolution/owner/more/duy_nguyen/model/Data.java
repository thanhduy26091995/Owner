
package com.hbbsolution.owner.more.duy_nguyen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("totalPrice")
    @Expose
    private long totalPrice;
    @SerializedName("task")
    @Expose
    private List<Task> task = null;
    @SerializedName("wallet")
    @Expose
    private long wallet;

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }

    public long getWallet() {
        return wallet;
    }

    public void setWallet(long wallet) {
        this.wallet = wallet;
    }

}
