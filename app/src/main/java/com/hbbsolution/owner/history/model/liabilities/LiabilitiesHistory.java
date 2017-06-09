
package com.hbbsolution.owner.history.model.liabilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hbbsolution.owner.history.model.workhistory.WorkHistory;

import java.io.Serializable;

public class LiabilitiesHistory implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("task")
    @Expose
    private WorkHistory task;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public WorkHistory getTask() {
        return task;
    }

    public void setTask(WorkHistory task) {
        this.task = task;
    }

}
