
package com.think360.sosimpli.model.sos.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("TimeFrom")
    @Expose
    private String timeFrom;
    @SerializedName("TimeTo")
    @Expose
    private String timeTo;
    @SerializedName("sos_date")
    @Expose
    private String sosDate;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zones")
    @Expose
    private String zones;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getSosDate() {
        return sosDate;
    }

    public void setSosDate(String sosDate) {
        this.sosDate = sosDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZones() {
        return zones;
    }

    public void setZones(String zones) {
        this.zones = zones;
    }

}
