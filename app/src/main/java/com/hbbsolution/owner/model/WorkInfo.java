
package com.hbbsolution.owner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WorkInfo implements Serializable{

    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("evaluation_point")
    @Expose
    private Integer evaluationPoint;
//    @SerializedName("ability")
//    @Expose
//    private List<Ability> ability = null;

//    public List<Ability> getAbility() {
//        return ability;
//    }
//
//    public void setAbility(List<Ability> ability) {
//        this.ability = ability;
//    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getEvaluationPoint() {
        return evaluationPoint;
    }

    public void setEvaluationPoint(Integer evaluationPoint) {
        this.evaluationPoint = evaluationPoint;
    }

}
