package com.think360.sosimply.model.getavailibility;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by surinder on 24-Aug-17.
 */

public class Datum implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.startDate);
        dest.writeList(this.time);
    }

    public Datum() {
    }

    protected Datum(Parcel in) {
        this.startDate = in.readString();
        this.time = new ArrayList<Time>();
        in.readList(this.time, Time.class.getClassLoader());
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
