package com.think360.sosimpli.model;

/**
 * Created by surinder on 12-Aug-17.
 */

public class Zone {

    private int id;

    public Zone(int id, String zoneName) {
        this.id = id;
        this.zoneName = zoneName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    private String zoneName;

}
