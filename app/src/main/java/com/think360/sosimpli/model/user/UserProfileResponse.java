package com.think360.sosimpli.model.user;

/**
 * Created by surinder on 26-Jul-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileResponse {

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

        @SerializedName("driver_id")
        @Expose
        private Integer driverId;
        @SerializedName("driver_name")
        @Expose
        private String driverName;
        @SerializedName("driver_email")
        @Expose
        private String driverEmail;
        @SerializedName("driver_image")
        @Expose
        private String driverImage;

        public Integer getDriverId() {
            return driverId;
        }

        public void setDriverId(Integer driverId) {
            this.driverId = driverId;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getDriverEmail() {
            return driverEmail;
        }

        public void setDriverEmail(String driverEmail) {
            this.driverEmail = driverEmail;
        }

        public String getDriverImage() {
            return driverImage;
        }

        public void setDriverImage(String driverImage) {
            this.driverImage = driverImage;
        }

    }

}

