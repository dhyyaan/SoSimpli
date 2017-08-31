package com.think360.sosimply.utils;

import com.think360.sosimply.model.user.UserProfileResponse;

/**
 * Created by surinder on 08-Aug-17.
 */

public class DriverHistoryChanged {

    private UserProfileResponse userProfileResponse;


    public DriverHistoryChanged(UserProfileResponse workerEditProfileModel) {
        this.userProfileResponse = workerEditProfileModel;
    }

    public UserProfileResponse getworkerEditProfileModel() {
        return userProfileResponse;
    }


}
