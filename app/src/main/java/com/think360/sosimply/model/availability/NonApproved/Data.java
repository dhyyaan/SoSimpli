package com.think360.sosimply.model.availability.NonApproved;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("availability_id")
    private int availability_id;
    @SerializedName("city_id")
    private int city_id;
    @SerializedName("city_name")
    private String city_name;
    @SerializedName("country_id")
    private int country_id;
    @SerializedName("country_name")
    private String country_name;
    @SerializedName("from_time")
    private String from_time;
    @SerializedName("start_date")
    private String start_date;
    @SerializedName("state_id")
    private int state_id;
    @SerializedName("state_name")
    private String state_name;
    @SerializedName("to_time")
    private String to_time;
    @SerializedName("zone")
    private String zone;

    public int getAvailability_id() {
        return availability_id;
    }

    public void setAvailability_id(int availability_id) {
        this.availability_id = availability_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
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
}
