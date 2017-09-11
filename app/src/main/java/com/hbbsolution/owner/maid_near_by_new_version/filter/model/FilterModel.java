package com.hbbsolution.owner.maid_near_by_new_version.filter.model;

/**
 * Created by buivu on 11/09/2017.
 */

public class FilterModel {
    private Double lat;
    private Double lng;
    private String address;
    private Integer distance;
    private Integer priceMin, priceMax;
    private String workId, workName;
    private Integer gender;
    private Integer ageMax, ageMin;

    public FilterModel() {
    }

    public FilterModel(Double lat, Double lng, String address, Integer distance, Integer priceMin, Integer priceMax, String workId, String workName, Integer gender, Integer ageMax, Integer ageMin) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.distance = distance;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.workId = workId;
        this.workName = workName;
        this.gender = gender;
        this.ageMax = ageMax;
        this.ageMin = ageMin;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Integer priceMin) {
        this.priceMin = priceMin;
    }

    public Integer getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Integer priceMax) {
        this.priceMax = priceMax;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(Integer ageMax) {
        this.ageMax = ageMax;
    }

    public Integer getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }
}
