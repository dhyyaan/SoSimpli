package com.think360.sosimpli.model.getavailibility;

/**
 * Created by surinder on 27-Jul-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAvaliabilityResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Time {


        @SerializedName("avalability_id")
        @Expose
        private String avalabilityId;
        @SerializedName("avalibality_status")
        @Expose
        private String avalibalityStatus;
        @SerializedName("zones")
        @Expose
        private String zones;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("from_time")
        @Expose
        private String fromTime;
        @SerializedName("to_time")
        @Expose
        private String toTime;
        @SerializedName("city_id")
        @Expose
        private String cityId;
        @SerializedName("name")
        @Expose
        private String name;

        public String getAvalabilityId() {
            return avalabilityId;
        }

        public void setAvalabilityId(String id) {
            this.avalabilityId = id;
        }


        public String getAvalibalityStatus() {
            return avalibalityStatus;
        }

        public void setAvalibalityStatus(String avalibalityStatus) {
            this.avalibalityStatus = avalibalityStatus;
        }

        public String getZones() {
            return zones;
        }

        public void setZones(String zones) {
            this.zones = zones;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getFromTime() {
            return fromTime;
        }

        public void setFromTime(String fromTime) {
            this.fromTime = fromTime;
        }

        public String getToTime() {
            return toTime;
        }

        public void setToTime(String toTime) {
            this.toTime = toTime;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public class Datum {

        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("time")
        @Expose
        private List<Time> time = null;

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public List<Time> getTime() {
            return time;
        }

        public void setTime(List<Time> time) {
            this.time = time;
        }

    }
}



