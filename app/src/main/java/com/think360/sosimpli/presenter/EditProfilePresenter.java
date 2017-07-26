package com.think360.sosimpli.presenter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;


import com.think360.sosimpli.AppController;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.model.WorkerEditProfileModel;
import com.think360.sosimpli.utils.AppConstants;
import com.think360.sosimpli.utils.FileUtils;

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


    public EditProfilePresenter(ApiService api, View view, String name, Uri imageUri, Context context) {
        super(context);
        this.api = api;
        this.view = view;
        saveProfile(name, imageUri, context);

    }

    private void saveProfile(String name, Uri imageuri, Context context) {

        pDialog.show();
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), AppController.sharedPreferencesCompat.getInt(AppConstants.DRIVER_ID, 0) + "");
        RequestBody etName = RequestBody.create(MediaType.parse("text/plain"), name);


        api.editWorkerProfile(userId, etName, imageuri == null ? null : prepareFilePart("profilepic", imageuri, context)).enqueue(new Callback<WorkerEditProfileModel>() {
            @Override
            public void onResponse(Call<WorkerEditProfileModel> call, Response<WorkerEditProfileModel> response) {
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
            public void onFailure(Call<WorkerEditProfileModel> call, Throwable t) {
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
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        compressedImageFile
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, compressedImageFile.getName(), requestFile);
    }


}
