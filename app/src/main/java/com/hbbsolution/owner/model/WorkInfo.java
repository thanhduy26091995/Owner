
package com.hbbsolution.owner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkInfo {

    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("evaluation_point")
    @Expose
    private float evaluationPoint;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public float getEvaluationPoint() {
        return evaluationPoint;
    }

    public void setEvaluationPoint(float evaluationPoint) {
        this.evaluationPoint = evaluationPoint;
    }

}
