package com.hbbsolution.owner.work_management.model.maid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tantr on 5/18/2017.
 */

public class Maid implements Serializable{
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("work_info")
    @Expose
    private WorkInfo workInfo;
    @SerializedName("info")
    @Expose
    private InfoMaid infoMaid;

    public InfoMaid getInfoMaid() {
        return infoMaid;
    }

    public void setInfoMaid(InfoMaid infoMaid) {
        this.infoMaid = infoMaid;
    }

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

}
