
package com.think360.sosimply.model.trip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("tripid")
    @Expose
    private Integer tripid;

    public Integer getTripid() {
        return tripid;
    }

    public void setTripid(Integer tripid) {
        this.tripid = tripid;
    }

}
