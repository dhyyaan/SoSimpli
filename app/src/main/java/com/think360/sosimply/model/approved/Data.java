package com.think360.sosimply.model.approved;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {
    @SerializedName("availability_id")
    private String availability_id;
    @SerializedName("trip_status")
    private String trip_status;
    @SerializedName("schdule_id")
    private String schdule_id;
    @SerializedName("trip_id")
    private String trip_id;
    @SerializedName("randonno")
    private String randonno;
    @SerializedName("tripadd_time")
    private String tripadd_time;
    @SerializedName("schdule_info")
    private String schdule_info;
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
    @SerializedName("Schdule_TimeFrom")
    private String Schdule_TimeFrom;
    @SerializedName("Schdule_TimeTo")
    private String Schdule_TimeTo;
    @SerializedName("schdule_zones")
    private String schdule_zones;
    @SerializedName("schdule_city")
    private String schdule_city;
    @SerializedName("schdule_name")
    private String schdule_name;

    public String getAvailability_id() {
        return availability_id;
    }

    public void setAvailability_id(String availability_id) {
        this.availability_id = availability_id;
    }

    public String getTrip_status() {
        return trip_status;
    }

    public void setTrip_status(String trip_status) {
        this.trip_status = trip_status;
    }

    public String getSchdule_id() {
        return schdule_id;
    }

    public void setSchdule_id(String schdule_id) {
        this.schdule_id = schdule_id;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getRandonno() {
        return randonno;
    }

    public void setRandonno(String randonno) {
        this.randonno = randonno;
    }

    public String getTripadd_time() {
        return tripadd_time;
    }

    public void setTripadd_time(String tripadd_time) {
        this.tripadd_time = tripadd_time;
    }

    public String getSchdule_info() {
        return schdule_info;
    }

    public void setSchdule_info(String schdule_info) {
        this.schdule_info = schdule_info;
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

    public String getSchdule_TimeFrom() {
        return Schdule_TimeFrom;
    }

    public void setSchdule_TimeFrom(String Schdule_TimeFrom) {
        this.Schdule_TimeFrom = Schdule_TimeFrom;
    }

    public String getSchdule_TimeTo() {
        return Schdule_TimeTo;
    }

    public void setSchdule_TimeTo(String Schdule_TimeTo) {
        this.Schdule_TimeTo = Schdule_TimeTo;
    }

    public String getSchdule_zones() {
        return schdule_zones;
    }

    public void setSchdule_zones(String schdule_zones) {
        this.schdule_zones = schdule_zones;
    }

    public String getSchdule_city() {
        return schdule_city;
    }

    public void setSchdule_city(String schdule_city) {
        this.schdule_city = schdule_city;
    }

    public String getSchdule_name() {
        return schdule_name;
    }

    public void setSchdule_name(String schdule_name) {
        this.schdule_name = schdule_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.availability_id);
        dest.writeString(this.trip_status);
        dest.writeString(this.schdule_id);
        dest.writeString(this.trip_id);
        dest.writeString(this.randonno);
        dest.writeString(this.tripadd_time);
        dest.writeString(this.schdule_info);
        dest.writeString(this.Day);
        dest.writeString(this.Month);
        dest.writeString(this.from_time);
        dest.writeString(this.to_time);
        dest.writeString(this.zone);
        dest.writeString(this.cityname);
        dest.writeString(this.avalibality_status);
        dest.writeString(this.Schdule_TimeFrom);
        dest.writeString(this.Schdule_TimeTo);
        dest.writeString(this.schdule_zones);
        dest.writeString(this.schdule_city);
        dest.writeString(this.schdule_name);
    }

    public Data() {
    }

    protected Data(Parcel in) {
        this.availability_id = in.readString();
        this.trip_status = in.readString();
        this.schdule_id = in.readString();
        this.trip_id = in.readString();
        this.randonno = in.readString();
        this.tripadd_time = in.readString();
        this.schdule_info = in.readString();
        this.Day = in.readString();
        this.Month = in.readString();
        this.from_time = in.readString();
        this.to_time = in.readString();
        this.zone = in.readString();
        this.cityname = in.readString();
        this.avalibality_status = in.readString();
        this.Schdule_TimeFrom = in.readString();
        this.Schdule_TimeTo = in.readString();
        this.schdule_zones = in.readString();
        this.schdule_city = in.readString();
        this.schdule_name = in.readString();
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
