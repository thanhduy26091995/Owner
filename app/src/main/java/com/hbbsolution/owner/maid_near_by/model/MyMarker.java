package com.hbbsolution.owner.maid_near_by.model;

/**
 * Created by buivu on 18/05/2017.
 */

public class MyMarker {
    private String name;
    private String imageUrl;
    private String price;
    private Double lat, lng;
    private String maidId;

    public MyMarker() {
    }

    public MyMarker(String name, String imageUrl, String price, Double lat, Double lng, String maidId) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.lat = lat;
        this.lng = lng;
        this.maidId = maidId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getMaidId() {
        return maidId;
    }

    public void setMaidId(String maidId) {
        this.maidId = maidId;
    }
}
