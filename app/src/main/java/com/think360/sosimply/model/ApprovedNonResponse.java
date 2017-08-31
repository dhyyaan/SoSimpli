package com.think360.sosimply.model;

/**
 * Created by surinder on 28-Jul-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApprovedNonResponse {

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

    public class Datum {


        @SerializedName("schdule_id")
        private String schdule_id;
        @SerializedName("availability_id")
        private String availability_id;
        @SerializedName("Day")
        private String Day;
        @SerializedName("Month")
        private String Month;
        @SerializedName("from_time")
        private String from_time;
        @SerializedName("to_time")
        private String to_time;
        @SerializedName("zone")
        private String zone;
        @SerializedName("cityname")
        private String cityname;
        @SerializedName("avalibality_status")
        private String avalibality_status;

        public String getSchdule_id() {
            return schdule_id;
        }

        public void setSchdule_id(String schdule_id) {
            this.schdule_id = schdule_id;
        }

        public String getAvailability_id() {
            return availability_id;
        }

        public void setAvailability_id(String availability_id) {
            this.availability_id = availability_id;
        }

        public String getDay() {
            return Day;
        }

        public void setDay(String Day) {
            this.Day = Day;
        }

        public String getMonth() {
            return Month;
        }

        public void setMonth(String Month) {
            this.Month = Month;
        }

        public String getFrom_time() {
            return from_time;
        }

        public void setFrom_time(String from_time) {
            this.from_time = from_time;
        }

        public String getTo_time() {
            return to_time;
        }

        public void setTo_time(String to_time) {
            this.to_time = to_time;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getAvalibality_status() {
            return avalibality_status;
        }

        public void setAvalibality_status(String avalibality_status) {
            this.avalibality_status = avalibality_status;
        }
    }
}



