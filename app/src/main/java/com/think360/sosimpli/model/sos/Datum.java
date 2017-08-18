
package com.think360.sosimpli.model.sos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable {

    @SerializedName("sos_id")
    @Expose
    private String sosId;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("zones")
    @Expose
    private String zones;
    @SerializedName("sos_time")
    @Expose
    private String sosTime;
    @SerializedName("state")
    @Expose
    private String state;

    public String getSosId() {
        return sosId;
    }

    public void setSosId(String sosId) {
        this.sosId = sosId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getZones() {
        return zones;
    }

    public void setZones(String zones) {
        this.zones = zones;
    }

    public String getSosTime() {
        return sosTime;
    }

    public void setSosTime(String sosTime) {
        this.sosTime = sosTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sosId);
        dest.writeString(this.text);
        dest.writeString(this.zones);
        dest.writeString(this.sosTime);
        dest.writeString(this.state);
    }

    public Datum() {
    }

    protected Datum(Parcel in) {
        this.sosId = in.readString();
        this.text = in.readString();
        this.zones = in.readString();
        this.sosTime = in.readString();
        this.state = in.readString();
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
