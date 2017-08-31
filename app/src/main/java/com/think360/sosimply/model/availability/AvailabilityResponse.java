package com.think360.sosimply.model.availability;

/**
 * Created by surinder on 26-Jul-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailabilityResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class Data {

        @SerializedName("availability_id")
        @Expose
        private Integer availabilityId;

        public Integer getAvailabilityId() {
            return availabilityId;
        }

        public void setAvailabilityId(Integer availabilityId) {
            this.availabilityId = availabilityId;
        }

    }
}
