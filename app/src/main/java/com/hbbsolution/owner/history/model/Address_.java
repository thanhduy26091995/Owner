
package com.hbbsolution.owner.history.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address_ implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("coordinates")
    @Expose
    private Coordinates_ coordinates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates_ getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates_ coordinates) {
        this.coordinates = coordinates;
    }

}
