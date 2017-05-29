
package com.hbbsolution.owner.history.model.commenthistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataComment {

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

}
