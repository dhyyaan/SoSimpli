
package com.think360.sosimpli.model.schedule.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("schdule_id")
    @Expose
    private String schduleId;
    @SerializedName("TimeFrom")
    @Expose
    private String timeFrom;
    @SerializedName("TimeTo")
    @Expose
    private String timeTo;
    @SerializedName("schdule_zone")
    @Expose
    private String schduleZone;
    @SerializedName("schdule_date")
    @Expose
    private String schduleDate;
    @SerializedName("schdule_info")
    @Expose
    private String schduleInfo;
    @SerializedName("city")
    @Expose
    private String city;

    public String getSchduleId() {
        return schduleId;
    }

    public void setSchduleId(String schduleId) {
        this.schduleId = schduleId;
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

    public String getSchduleZone() {
        return schduleZone;
    }

    public void setSchduleZone(String schduleZone) {
        this.schduleZone = schduleZone;
    }

    public String getSchduleDate() {
        return schduleDate;
    }

    public void setSchduleDate(String schduleDate) {
        this.schduleDate = schduleDate;
    }

    public String getSchduleInfo() {
        return schduleInfo;
    }

    public void setSchduleInfo(String schduleInfo) {
        this.schduleInfo = schduleInfo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
