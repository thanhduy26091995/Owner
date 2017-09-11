package com.hbbsolution.owner.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by buivu on 08/09/2017.
 */

public class Dist implements Serializable {
    @SerializedName("calculated")
    private Double calculated;

    public Double getCalculated() {
        return calculated;
    }

    public void setCalculated(Double calculated) {
        this.calculated = calculated;
    }
}
