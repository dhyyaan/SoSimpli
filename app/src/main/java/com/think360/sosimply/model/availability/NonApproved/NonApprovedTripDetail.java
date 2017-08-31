package com.think360.sosimply.model.availability.NonApproved;

import com.google.gson.annotations.SerializedName;

/**
 * Created by surinder on 24-Aug-17.
 */

public class NonApprovedTripDetail {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
