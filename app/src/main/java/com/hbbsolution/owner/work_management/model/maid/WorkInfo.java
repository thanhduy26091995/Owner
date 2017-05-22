package com.hbbsolution.owner.work_management.model.maid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tantr on 5/18/2017.
 */

public class WorkInfo implements Serializable{

    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("evaluation_point")
    @Expose
    private Float evaluationPoint;
    @SerializedName("ability")
    @Expose
    private List<Object> ability = null;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Float getEvaluationPoint() {
        return evaluationPoint;
    }

    public void setEvaluationPoint(Float evaluationPoint) {
        this.evaluationPoint = evaluationPoint;
    }

    public List<Object> getAbility() {
        return ability;
    }

    public void setAbility(List<Object> ability) {
        this.ability = ability;
    }
}
