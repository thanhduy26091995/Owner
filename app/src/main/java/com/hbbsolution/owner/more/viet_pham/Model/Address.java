package com.hbbsolution.owner.more.viet_pham.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 5/22/2017.
 */

public class Address {
    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;
    @SerializedName("name")
    @Expose
    private String name;

    public Address() {
    }

    public Address(Coordinates coordinates, String name) {
        this.coordinates = coordinates;
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
