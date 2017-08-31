package com.think360.sosimply.model.approved;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by surinder on 28-Aug-17.
 */

public class ApprovedScheduleRespponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Data> data;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
