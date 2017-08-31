package com.think360.sosimply.presenter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.think360.sosimply.AppController;
import com.think360.sosimply.manager.ApiService;
import com.think360.sosimply.model.user.UserProfileResponse;
import com.think360.sosimply.AppConstants;
import com.think360.sosimply.utils.FileUtils;

import java.io.File;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by think360 on 26/04/17.
 */

public class EditProfilePresenter extends BasePresenter {

    private ApiService api;
    private View view;

    public interface View {
        void profileSavedSuccessfully();
        void onError(Throwable t);
    }


    public EditProfilePresenter(ApiService api, View view, String name, String password, Uri imageUri, Context context) {
        super(context);
        this.api = api;
        this.view = view;
        saveProfile(name, password, imageUri, context);

    }

    private void saveProfile(String name, String password, Uri imageuri, Context context) {

        pDialog.show();
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), AppController.sharedPreferencesCompat.getInt(AppConstants.DRIVER_ID, 0) + "");
        RequestBody etName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody etPassword = RequestBody.create(MediaType.parse("text/plain"), password);

        api.editDriverProfile(userId, etName, etPassword, imageuri == null ? null : prepareFilePart("driver_pic", imageuri, context)).enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    view.profileSavedSuccessfully();
                    alertDialog.setMessage(response.body().getMessage());
                    alertDialog.show();
                } else {
                    pDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                pDialog.dismiss();
                view.onError(t);
            }
        });

    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri, Context context) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(context, fileUri);

        File compressedImageFile = Compressor.getDefault(context).compressToFile(file);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), compressedImageFile);

        // MultipartBody.Part is used to send also the actual file zone
        return MultipartBody.Part.createFormData(partName, compressedImageFile.getName(), requestFile);
    }


}
