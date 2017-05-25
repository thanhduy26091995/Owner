package com.hbbsolution.owner.work_management.model.workmanagerpending;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hbbsolution.owner.model.InfoMaid;
import com.hbbsolution.owner.model.WorkInfo;
import com.hbbsolution.owner.work_management.model.workmanager.Info;

import java.io.Serializable;

/**
 * Created by tantr on 5/25/2017.
 */

public class ReceivedPending implements Serializable {

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
