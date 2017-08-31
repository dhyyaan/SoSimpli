
package com.think360.sosimply.model.sos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable {


    @SerializedName("sos_id")
    private String sos_id;
    @SerializedName("text")
    private String text;
    @SerializedName("zones")
    private String zones;
    @SerializedName("sos_time")
    private String sos_time;
    @SerializedName("City")
    private String City;
    @SerializedName("TimeFrom")
    private String TimeFrom;
    @SerializedName("TimeTo")
    private String TimeTo;

    public String getSos_id() {
        return sos_id;
    }

    public void setSos_id(String sos_id) {
        this.sos_id = sos_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getZones() {

        if (zones.equals("1")) {
            return "East Zone";
        }
        if (zones.equals("2")) {
            return "West Zone";
        }
        if (zones.equals("3")) {
            return "North Zone";
        }
        if (zones.equals("4")) {
            return "South Zone";
        }

        return zones;
    }

    public void setZones(String zones) {
        this.zones = zones;
    }

    public String getSos_time() {
        return sos_time;
    }

    public void setSos_time(String sos_time) {
        this.sos_time = sos_time;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getTimeFrom() {
        return TimeFrom;
    }

    public void setTimeFrom(String TimeFrom) {
        this.TimeFrom = TimeFrom;
    }

    public String getTimeTo() {
        return TimeTo;
    }

    public void setTimeTo(String TimeTo) {
        this.TimeTo = TimeTo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sos_id);
        dest.writeString(this.text);
        dest.writeString(this.zones);
        dest.writeString(this.sos_time);
        dest.writeString(this.City);
        dest.writeString(this.TimeFrom);
        dest.writeString(this.TimeTo);
    }

    public Datum() {
    }

    protected Datum(Parcel in) {
        this.sos_id = in.readString();
        this.text = in.readString();
        this.zones = in.readString();
        this.sos_time = in.readString();
        this.City = in.readString();
        this.TimeFrom = in.readString();
        this.TimeTo = in.readString();
    }

    public static final Parcelable.Creator<Datum> CREATOR = new Parcelable.Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel source) {
            return new Datum(source);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };
}
