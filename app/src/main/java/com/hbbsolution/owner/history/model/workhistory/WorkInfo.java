
package com.hbbsolution.owner.history.model.workhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hbbsolution.owner.model.Ability;

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
    private List<Ability> ability = null;

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

    public List<Ability> getAbility() {
        return ability;
    }

    public void setAbility(List<Ability> ability) {
        this.ability = ability;
    }

}
