package com.think360.sosimply.model.user;

/**
 * Created by surinder on 19-Jul-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("driver_id")
    @Expose
    private int driverId;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("driver_email")
    @Expose
    private String driverEmail;

    public String getOperator_phone() {
        return operator_phone;
    }

    @SerializedName("operator_phone")
    @Expose
    private String operator_phone;

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
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

}
