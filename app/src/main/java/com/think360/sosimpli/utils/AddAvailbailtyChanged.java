package com.think360.sosimpli.utils;

import com.think360.sosimpli.model.availability.AvailabilityResponse;
import com.think360.sosimpli.model.user.UserProfileResponse;

/**
 * Created by surinder on 08-Aug-17.
 */

public class AddAvailbailtyChanged {

    private AvailabilityResponse userProfileResponse;




    public AddAvailbailtyChanged(AvailabilityResponse workerEditProfileModel) {
        this.userProfileResponse = workerEditProfileModel;
    }



    public AvailabilityResponse getworkerEditProfileModel() {
        return userProfileResponse;
    }




}
