
package com.think360.sosimpli.model.schedule;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.schduleId);
        dest.writeString(this.timeFrom);
        dest.writeString(this.timeTo);
        dest.writeString(this.schduleZone);
        dest.writeString(this.schduleDate);
    }

    public Datum() {
    }

    protected Datum(Parcel in) {
        this.schduleId = in.readString();
        this.timeFrom = in.readString();
        this.timeTo = in.readString();
        this.schduleZone = in.readString();
        this.schduleDate = in.readString();
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
