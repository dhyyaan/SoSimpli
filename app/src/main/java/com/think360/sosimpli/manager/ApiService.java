package com.think360.sosimpli.manager;


import com.think360.sosimpli.model.ApprovedNonResponse;
import com.think360.sosimpli.model.ForgetPasswordResponse;
import com.think360.sosimpli.model.ResendOtpResponse;
import com.think360.sosimpli.model.VerifyOtpResponse;
import com.think360.sosimpli.model.WorkerEditProfileModel;
import com.think360.sosimpli.model.availability.AvailabilityResponse;
import com.think360.sosimpli.model.city.CityResponse;
import com.think360.sosimpli.model.country.CountryResponse;
import com.think360.sosimpli.model.getavailibility.GetAvaliabilityResponse;
import com.think360.sosimpli.model.logout.LogoutResponse;
import com.think360.sosimpli.model.states.StateResponse;
import com.think360.sosimpli.model.user.User;
import com.think360.sosimpli.model.user.UserProfileResponse;
import com.think360.sosimpli.model.work.WorkHistory;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


/**
 * Created by think360 on 18/04/17.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("login")
    Call<User> loginUser(@Field("email") String name,
                         @Field("password") String password);


    @FormUrlEncoded
    @POST("forget_password")
    Call<ForgetPasswordResponse> forgetPassword(@Field("mobile") String name);

    @FormUrlEncoded
    @POST("verify_otp")
    Call<VerifyOtpResponse> verifyOtp(@Field("id") String driver_id, @Field("otp") String name);

    @FormUrlEncoded
    @POST("resend_otp")
    Call<ResendOtpResponse> resendOtp(@Field("id") String driver_id);


    @POST("getcountry")
    Call<CountryResponse> getCountries();

    @FormUrlEncoded
    @POST("getState")
    Call<StateResponse> getStates(@Field("country_id") String country_id, @Field("state_id") String state_id);


    @FormUrlEncoded
    @POST("getState")
    Call<CityResponse> getCity(@Field("country_id") String country_id, @Field("state_id") String state_id);


    @FormUrlEncoded
    @POST("addavailability")
    Call<AvailabilityResponse> addAvailability(@Field("driver_id") int driver_id, @Field("start_date") String start_date, @Field("from_time") String from_time, @Field("to_time") String to_time, @Field("country_id") String country_id, @Field("state_id") String state_id, @Field("city_id") String city_id, @Field("zones") String zones);


    @FormUrlEncoded
    @POST("getavalibility")
    Call<GetAvaliabilityResponse> getAvailability(@Field("driver_id") int driver_id);

    @FormUrlEncoded
    @POST("availability_ApproveNonapprove")
    Call<ApprovedNonResponse> getAvailabilityApproveNonApprove(@Field("driver_id") int id, @Field("status") int status);


    @FormUrlEncoded
    @POST("userlogout")
    Call<LogoutResponse> logoutDriver(@Field("id") int id);

    // for RXJava
    @FormUrlEncoded
    @POST("workerprofile/")
    Observable<WorkHistory> getWorkHistory(@Field("id") int password);


    @FormUrlEncoded
    @POST("driver_history")
    Call<UserProfileResponse> getDriverProfile(@Field("id") int id);


    @Multipart
    @POST("driveredit")
    Call<WorkerEditProfileModel> editDriverProfile(@Part("id") RequestBody userid,
                                                   @Part("driver_name") RequestBody name,
                                                   @Part("password") RequestBody password,
                                                   @Part MultipartBody.Part file);



}
