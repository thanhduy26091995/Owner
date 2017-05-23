
package com.hbbsolution.owner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Maid implements Serializable{

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("work_info")
    @Expose
    private WorkInfo workInfo;

    @SerializedName("info")
    @Expose
    private InfoMaid info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WorkInfo getWorkInfo() {
        return workInfo;
    }

    public void setWorkInfo(WorkInfo workInfo) {
        this.workInfo = workInfo;
    }

    public InfoMaid getInfo() {
        return info;
    }

    public void setInfo(InfoMaid info) {
        this.info = info;
    }

}
