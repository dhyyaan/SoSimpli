package com.think360.sosimply.notification;

/**
 * Created by surinder on 24-Aug-17.
 */

public class EventToRefresh {
    public int getBody() {
        return body;
    }

    private int body;

    public EventToRefresh(int body) {
        this.body = body;
    }
}
