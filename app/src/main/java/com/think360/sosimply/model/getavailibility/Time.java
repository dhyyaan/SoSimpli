package com.think360.sosimply.model.getavailibility;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by surinder on 24-Aug-17.
 */

public class Time implements Parcelable {


    @SerializedName("randonno")
    public String randonno;


    @SerializedName("driver_id")
    public String driver_id;
    @SerializedName("schdule_id")
    public String schdule_id;
    @SerializedName("avalability_id")
    public String avalability_id;
    @SerializedName("avalibality_status")
    public String avalibality_status;
    @SerializedName("availabiliy_zones")
    public String availabiliy_zones;
    @SerializedName("from_time")
    public String from_time;
    @SerializedName("to_time")
    public String to_time;
    @SerializedName("city_id")
    public String city_id;
    @SerializedName("availability_city")
    public String availability_city;
    @SerializedName("schdule_city")
    public String schdule_city;
    @SerializedName("City")
    public String City;
    @SerializedName("schdule_name")
    public String schdule_name;
    @SerializedName("schdule_zones")
    public String schdule_zones;
    @SerializedName("timefromsc")
    public String timefromsc;
    @SerializedName("timetosch")
    public String timetosch;
    @SerializedName("schdule_info")
    public String schdule_info;
    @SerializedName("trip_status")
    public String trip_status;
    @SerializedName("tripid")
    public String tripid;
    @SerializedName("start_trip")
    public String start_trip;
    @SerializedName("TimeFrom")
    public String TimeFrom;
    @SerializedName("TimeTo")
    public String TimeTo;
    @SerializedName("trip_addtime")
    public String trip_addtime;
    @SerializedName("trip_start")
    public String trip_start;
    @SerializedName("Schdule_TimeFrom")
    public String Schdule_TimeFrom;
    @SerializedName("Schdule_TimeTo")
    public String Schdule_TimeTo;
    
    public String getRandonno() {
        return randonno;
    }

    public void setRandonno(String randonno) {
        this.randonno = randonno;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getSchdule_id() {
        return schdule_id;
    }

    public void setSchdule_id(String schdule_id) {
        this.schdule_id = schdule_id;
    }

    public String getAvalability_id() {
        return avalability_id;
    }

    public void setAvalability_id(String avalability_id) {
        this.avalability_id = avalability_id;
    }

    public String getAvalibality_status() {
        return avalibality_status;
    }

    public void setAvalibality_status(String avalibality_status) {
        this.avalibality_status = avalibality_status;
    }

    public String getAvailabiliy_zones() {
        return availabiliy_zones;
    }

    public void setAvailabiliy_zones(String availabiliy_zones) {
        this.availabiliy_zones = availabiliy_zones;
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

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getAvailability_city() {
        return availability_city;
    }

    public void setAvailability_city(String availability_city) {
        this.availability_city = availability_city;
    }

    public String getSchdule_city() {
        return schdule_city;
    }

    public void setSchdule_city(String schdule_city) {
        this.schdule_city = schdule_city;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getSchdule_name() {
        return schdule_name;
    }

    public void setSchdule_name(String schdule_name) {
        this.schdule_name = schdule_name;
    }

    public String getSchdule_zones() {
        return schdule_zones;
    }

    public void setSchdule_zones(String schdule_zones) {
        this.schdule_zones = schdule_zones;
    }

    public String getTimefromsc() {
        return timefromsc;
    }

    public void setTimefromsc(String timefromsc) {
        this.timefromsc = timefromsc;
    }

    public String getTimetosch() {
        return timetosch;
    }

    public void setTimetosch(String timetosch) {
        this.timetosch = timetosch;
    }

    public String getSchdule_info() {
        return schdule_info;
    }

    public void setSchdule_info(String schdule_info) {
        this.schdule_info = schdule_info;
    }

    public String getTrip_status() {
        return trip_status;
    }

    public void setTrip_status(String trip_status) {
        this.trip_status = trip_status;
    }

    public String getTripid() {
        return tripid;
    }

    public void setTripid(String tripid) {
        this.tripid = tripid;
    }

    public String getStart_trip() {
        return start_trip;
    }

    public void setStart_trip(String start_trip) {
        this.start_trip = start_trip;
    }

    public String getTimeFrom() {
        return TimeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        TimeFrom = timeFrom;
    }

    public String getTimeTo() {
        return TimeTo;
    }

    public void setTimeTo(String timeTo) {
        TimeTo = timeTo;
    }

    public String getTrip_addtime() {
        return trip_addtime;
    }

    public void setTrip_addtime(String trip_addtime) {
        this.trip_addtime = trip_addtime;
    }

    public String getTrip_start() {
        return trip_start;
    }

    public void setTrip_start(String trip_start) {
        this.trip_start = trip_start;
    }

    public String getSchdule_TimeFrom() {
        return Schdule_TimeFrom;
    }

    public void setSchdule_TimeFrom(String schdule_TimeFrom) {
        Schdule_TimeFrom = schdule_TimeFrom;
    }

    public String getSchdule_TimeTo() {
        return Schdule_TimeTo;
    }

    public void setSchdule_TimeTo(String schdule_TimeTo) {
        Schdule_TimeTo = schdule_TimeTo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.randonno);
        dest.writeString(this.driver_id);
        dest.writeString(this.schdule_id);
        dest.writeString(this.avalability_id);
        dest.writeString(this.avalibality_status);
        dest.writeString(this.availabiliy_zones);
        dest.writeString(this.from_time);
        dest.writeString(this.to_time);
        dest.writeString(this.city_id);
        dest.writeString(this.availability_city);
        dest.writeString(this.schdule_city);
        dest.writeString(this.City);
        dest.writeString(this.schdule_name);
        dest.writeString(this.schdule_zones);
        dest.writeString(this.timefromsc);
        dest.writeString(this.timetosch);
        dest.writeString(this.schdule_info);
        dest.writeString(this.trip_status);
        dest.writeString(this.tripid);
        dest.writeString(this.start_trip);
        dest.writeString(this.TimeFrom);
        dest.writeString(this.TimeTo);
        dest.writeString(this.trip_addtime);
        dest.writeString(this.trip_start);
        dest.writeString(this.Schdule_TimeFrom);
        dest.writeString(this.Schdule_TimeTo);
    }

    public Time() {
    }

    protected Time(Parcel in) {
        this.randonno = in.readString();
        this.driver_id = in.readString();
        this.schdule_id = in.readString();
        this.avalability_id = in.readString();
        this.avalibality_status = in.readString();
        this.availabiliy_zones = in.readString();
        this.from_time = in.readString();
        this.to_time = in.readString();
        this.city_id = in.readString();
        this.availability_city = in.readString();
        this.schdule_city = in.readString();
        this.City = in.readString();
        this.schdule_name = in.readString();
        this.schdule_zones = in.readString();
        this.timefromsc = in.readString();
        this.timetosch = in.readString();
        this.schdule_info = in.readString();
        this.trip_status = in.readString();
        this.tripid = in.readString();
        this.start_trip = in.readString();
        this.TimeFrom = in.readString();
        this.TimeTo = in.readString();
        this.trip_addtime = in.readString();
        this.trip_start = in.readString();
        this.Schdule_TimeFrom = in.readString();
        this.Schdule_TimeTo = in.readString();
    }

    public static final Parcelable.Creator<Time> CREATOR = new Parcelable.Creator<Time>() {
        @Override
        public Time createFromParcel(Parcel source) {
            return new Time(source);
        }

        @Override
        public Time[] newArray(int size) {
            return new Time[size];
        }
    };
}
