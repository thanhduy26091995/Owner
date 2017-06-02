
package com.hbbsolution.owner.history.model.helper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WorkInfo implements Serializable {

    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("evaluation_point")
    @Expose
    private Integer evaluationPoint;
    @SerializedName("ability")
    @Expose
    private List<Object> ability = null;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getEvaluationPoint() {
        return evaluationPoint;
    }

    public void setEvaluationPoint(Integer evaluationPoint) {
        this.evaluationPoint = evaluationPoint;
    }

    public List<Object> getAbility() {
        return ability;
    }

    public void setAbility(List<Object> ability) {
        this.ability = ability;
    }

}
