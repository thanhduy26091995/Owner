
package com.hbbsolution.owner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WorkInfo implements Serializable{

    @SerializedName("price")
    @Expose
    private float price;
    @SerializedName("evaluation_point")
    @Expose
    private float evaluationPoint;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getEvaluationPoint() {
        return evaluationPoint;
    }

    public void setEvaluationPoint(float evaluationPoint) {
        this.evaluationPoint = evaluationPoint;
    }

}
