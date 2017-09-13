package com.hbbsolution.owner.run_face.model;

import java.io.Serializable;

/**
 * Created by buivu on 30/08/2017.
 */

public class CompareImageModel implements Serializable {
    private String imageServer;
    private String imageGallery;
    private boolean status;
    private Double confidence;
    private Boolean isIdentical;
    private String maidId;

    public CompareImageModel() {
    }

    public String getMaidId() {
        return maidId;
    }

    public void setMaidId(String maidId) {
        this.maidId = maidId;
    }

    public String getImageServer() {
        return imageServer;
    }

    public void setImageServer(String imageServer) {
        this.imageServer = imageServer;
    }

    public String getImageGallery() {
        return imageGallery;
    }

    public void setImageGallery(String imageGallery) {
        this.imageGallery = imageGallery;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public Boolean getIdentical() {
        return isIdentical;
    }

    public void setIdentical(Boolean identical) {
        isIdentical = identical;
    }
}
