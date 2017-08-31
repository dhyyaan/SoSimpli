package com.think360.sosimply.manager;


import com.think360.sosimply.model.AcceptRejectSosResponse;
import com.think360.sosimply.model.ApprovedNonResponse;
import com.think360.sosimply.model.ForgetPasswordResponse;
import com.think360.sosimply.model.ResendOtpResponse;
import com.think360.sosimply.model.VerifyOtpResponse;
import com.think360.sosimply.model.approved.ApprovedScheduleRespponse;
import com.think360.sosimply.model.availability.AvailabilityResponse;
import com.think360.sosimply.model.availability.NonApproved.NonApprovedTripDetail;
import com.think360.sosimply.model.city.CityResponse;
import com.think360.sosimply.model.country.CountryResponse;
import com.think360.sosimply.model.getavailibility.GetAvaliabilityResponse;
import com.think360.sosimply.model.logout.LogoutResponse;
import com.think360.sosimply.model.schedule.CompletedScheduleResponse;
import com.think360.sosimply.model.schedule.detail.ScheduleDetailResponse;
import com.think360.sosimply.model.schedule.pending.PedningScheduleResponse;
import com.think360.sosimply.model.sos.AllSoSResponse;
import com.think360.sosimply.model.sos.count.SosCountResponse;
import com.think360.sosimply.model.sos.detail.SoSDetailResponse;
import com.think360.sosimply.model.states.StateResponse;
import com.think360.sosimply.model.trip.TripFinishStartResponse;
import com.think360.sosimply.model.user.User;
import com.think360.sosimply.model.user.UserProfileResponse;
import com.think360.sosimply.model.work.WorkHistory;

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
    Call<User> loginUser(@Field("email") String name, @Field("password") String password, @Field("device_id") String device_id, @Field("device_type") String device_type);

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
    @POST("availablity_edit")
    Call<AvailabilityResponse> addAvailabilityEdit(@Field("driver_id") int driver_id,@Field("id") String id, @Field("start_date") String start_date, @Field("from_time") String from_time, @Field("to_time") String to_time, @Field("country_id") String country_id, @Field("state_id") String state_id, @Field("city_id") String city_id, @Field("zones") String zones);



    @FormUrlEncoded
    @POST("getavalibility")
    Call<GetAvaliabilityResponse> getAvailability(@Field("driver_id") int driver_id);

    @FormUrlEncoded
    @POST("avalibilityapprove")
    Call<ApprovedScheduleRespponse> getAvailabilityApproved(@Field("driver_id") int id);

    @FormUrlEncoded
    @POST("avalibilitynonapprove")
    Call<ApprovedNonResponse> getAvailabilityNonApproved(@Field("driver_id") int id);

    @FormUrlEncoded
    @POST("getnon_approvedavalibality")
    Call<NonApprovedTripDetail> getNonApproveAvailDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("userlogout")
    Call<LogoutResponse> logoutDriver(@Field("id") int id);

    @FormUrlEncoded
    @POST("acceptReject_sos")
    Call<AcceptRejectSosResponse> acceptRejectSos(@Field("driver_id") int driver_id, @Field("sos_id") String sos_id, @Field("Status") int status);

    @POST("get_sosall")
    Call<AllSoSResponse> getAllSoS();

    @FormUrlEncoded
    @POST("sos_details")
    Call<SoSDetailResponse> getSoSDetail(@Field("sos_id") String sos_id);

    @FormUrlEncoded
    @POST("completeschdule")
    Call<CompletedScheduleResponse> getCompleteSchdule(@Field("driver_id") int driver_id);

    @FormUrlEncoded
    @POST("schdule_details")
    Call<ScheduleDetailResponse> getSchduleDetails(@Field("schdule_id") String schdule_id);


    @FormUrlEncoded
    @POST("driver_trip")
    Call<TripFinishStartResponse> tripStart(@Field("driver_id") int driver_id, @Field("start_trip") String start_trip, @Field("avail_id") String avail_id);

    @FormUrlEncoded
    @POST("driver_tripFinish")
    Call<TripFinishStartResponse> tripFinish(@Field("driver_id") int driver_id, @Field("end_trip") String start_trip, @Field("avail_id") String avail_id, @Field("tripid") String tripid);


    @FormUrlEncoded
    @POST("contact_operator")
    Call<TripFinishStartResponse> contactToOperator(@Field("driver_id") int driver_id, @Field("schdule_id") String schdule_id, @Field("message") String message, @Field("Subject") String Subject);

    @FormUrlEncoded
    @POST("pending_schdule")
    Call<PedningScheduleResponse> getPendingSchedule(@Field("driver_id") int driver_id);

    @FormUrlEncoded
    @POST("driver_history")
    Call<UserProfileResponse> getDriverProfile(@Field("id") int id);


    @FormUrlEncoded
    @POST("sos_count")
    Call<SosCountResponse> getSosCount(@Field("id") int id);

    @FormUrlEncoded
    @POST("sos_count")
    Call<SosCountResponse> readSosCount(@Field("id") int id);

    @Multipart
    @POST("driveredit")
    Call<UserProfileResponse> editDriverProfile(@Part("id") RequestBody userid, @Part("driver_name") RequestBody name, @Part("password") RequestBody password, @Part MultipartBody.Part file);

    // for RXJava
    @FormUrlEncoded
    @POST("workerprofile/")
    Observable<WorkHistory> getWorkHistory(@Field("id") int password);


}
