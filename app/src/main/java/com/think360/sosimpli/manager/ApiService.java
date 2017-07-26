package com.think360.sosimpli.manager;


import com.think360.sosimpli.model.WorkerEditProfileModel;
import com.think360.sosimpli.model.city.CityResponse;
import com.think360.sosimpli.model.country.CountryResponse;
import com.think360.sosimpli.model.states.StateResponse;
import com.think360.sosimpli.model.user.User;
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
    @POST("login/")
    Call<User> loginUser(@Field("email") String name,
                         @Field("password") String password);


    @POST("getcountry/")
    Call<CountryResponse> getCountries();

    @FormUrlEncoded
    @POST("getState ")
    Call<StateResponse> getStates(@Field("country_id") String country_id, @Field("state_id") String state_id);


    @FormUrlEncoded
    @POST("getState ")
    Call<CityResponse> getCity(@Field("country_id") String country_id, @Field("state_id") String state_id);

    @FormUrlEncoded
    @POST("workerprofile")
    Call<WorkHistory> getWorkHistoryWithCall(@Field("id") int id);


    @FormUrlEncoded
    @POST("workerlogout")
    Call<WorkerEditProfileModel> logoutWorker(@Field("id") int id);

    // for RXJava
    @FormUrlEncoded
    @POST("workerprofile/")
    Observable<WorkHistory> getWorkHistory(@Field("id") int password);


    @Multipart
    @POST("workeredit/")
    Call<WorkerEditProfileModel> editWorkerProfile(@Part("id") RequestBody userid,
                                                   @Part("firstname") RequestBody name,
                                                   @Part MultipartBody.Part file);


}
