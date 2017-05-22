package com.hbbsolution.owner.work_management.model.listcommentmaid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tantr on 5/22/2017.
 */

public class Doc {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("evaluation_point")
    @Expose
    private Integer evaluationPoint;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("task")
    @Expose
    private Task task;
    @SerializedName("fromId")
    @Expose
    private FromId fromId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Integer getEvaluationPoint() {
        return evaluationPoint;
    }

    public void setEvaluationPoint(Integer evaluationPoint) {
        this.evaluationPoint = evaluationPoint;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public FromId getFromId() {
        return fromId;
    }

    public void setFromId(FromId fromId) {
        this.fromId = fromId;
    }
}
