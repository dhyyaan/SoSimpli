package com.think360.sosimply.model;

import android.support.annotation.IntegerRes;

public class NavDrawerItem {
    private boolean showNotify;
    private String title;

    public int getImageDrawable() {
        return imageDrawable;
    }

    public void setImageDrawable(int imageDrawable) {
        this.imageDrawable = imageDrawable;
    }

    private int imageDrawable;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, @IntegerRes int imageDrawable) {
        this.showNotify = showNotify;
        this.title = title;
        this.imageDrawable = imageDrawable;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
