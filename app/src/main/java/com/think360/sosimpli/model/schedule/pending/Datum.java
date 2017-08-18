
package com.think360.sosimpli.model.schedule.pending;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("schdule_name")
    @Expose
    private String schduleName;
    @SerializedName("schdule_id")
    @Expose
    private String schduleId;

    public String getSchduleName() {
        return schduleName;
    }

    public void setSchduleName(String schduleName) {
        this.schduleName = schduleName;
    }

    public String getSchduleId() {
        return schduleId;
    }

    public void setSchduleId(String schduleId) {
        this.schduleId = schduleId;
    }

}
