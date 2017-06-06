package com.hbbsolution.owner.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by buivu on 06/06/2017.
 */

public class DataCheckIn {
    @SerializedName("isIdentical")
    private boolean isIdentical;
    @SerializedName("confidence")
    private Double confidence;

    public DataCheckIn(boolean isIdentical, Double confidence) {
        this.isIdentical = isIdentical;
        this.confidence = confidence;
    }

    public boolean isIdentical() {
        return isIdentical;
    }

    public void setIdentical(boolean identical) {
        isIdentical = identical;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
}
