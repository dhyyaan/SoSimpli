package com.think360.sosimply.utils;

import com.think360.sosimply.model.availability.AvailabilityResponse;

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
