package com.think360.sosimpli.model.country;

/**
 * Created by surinder on 24-Jul-17.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("country")
    @Expose
    private List<Country> country = null;

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }

}
