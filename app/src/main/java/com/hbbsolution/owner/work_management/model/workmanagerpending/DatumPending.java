package com.hbbsolution.owner.work_management.model.workmanagerpending;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hbbsolution.owner.work_management.model.workmanager.History;
import com.hbbsolution.owner.work_management.model.workmanager.Info;
import com.hbbsolution.owner.work_management.model.workmanager.Process;

import java.io.Serializable;

/**
 * Created by tantr on 5/25/2017.
 */

public class DatumPending implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("process")
    @Expose
    private Process process;
    @SerializedName("history")
    @Expose
    private History history;
    @SerializedName("stakeholders")
    @Expose
    private StakeholdersPending stakeholders;
    @SerializedName("info")
    @Expose
    private Info info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public StakeholdersPending getStakeholders() {
        return stakeholders;
    }

    public void setStakeholders(StakeholdersPending stakeholders) {
        this.stakeholders = stakeholders;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
