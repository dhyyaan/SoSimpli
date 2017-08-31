package com.think360.sosimply.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.think360.sosimply.AppConstants;
import com.think360.sosimply.AppController;
import com.think360.sosimply.R;
import com.think360.sosimply.databinding.EditProfileBinding;
import com.think360.sosimply.manager.ApiService;
import com.think360.sosimply.model.user.UserProfileResponse;
import com.think360.sosimply.presenter.EditProfilePresenter;
import com.think360.sosimply.ui.activities.login.LoginActivity;
import com.think360.sosimply.utils.DriverHistoryChanged;
import com.think360.sosimply.utils.FileUtils;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;

import javax.inject.Inject;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends RootFragment implements View.OnClickListener, EditProfilePresenter.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Inject
    ApiService apiService;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditProfileBinding editProfileBinding;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    private Uri imagePath;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppController) getActivity().getApplication()).getComponent().inject(this);


        editProfileBinding = DataBindingUtil.inflate(inflater, R.layout.edit_profile, container, false);

        if (!TextUtils.isEmpty(AppController.sharedPreferencesCompat.getString(AppConstants.WORKER_PROFILE_IMAGE_URL, ""))) {
            Picasso.with(getActivity()).load(AppController.sharedPreferencesCompat.getString(AppConstants.WORKER_PROFILE_IMAGE_URL, "")).resize(120, 120).centerCrop().error(R.drawable.user).placeholder(R.drawable.user).into(editProfileBinding.ivUser);
        }
        if (!TextUtils.isEmpty(AppController.sharedPreferencesCompat.getString(AppConstants.DRIVER_NAME, ""))) {
            editProfileBinding.editName.setText(AppController.sharedPreferencesCompat.getString(AppConstants.DRIVER_NAME, ""));
            editProfileBinding.tvName.setText(AppController.sharedPreferencesCompat.getString(AppConstants.DRIVER_NAME, ""));
        }
        if (AppController.sharedPreferencesCompat.getInt(AppConstants.DRIVER_ID, 0) != 0) {
            apiService.getDriverProfile(AppController.sharedPreferencesCompat.getInt(AppConstants.DRIVER_ID, 0)).enqueue(new Callback<UserProfileResponse>() {
                @Override
                public void onResponse(Call<UserProfileResponse> call, retrofit2.Response<UserProfileResponse> response) {
                    if (response.isSuccessful() && response.body().getStatus()) {
                        ((AppController) getActivity().getApplication()).bus().send(new DriverHistoryChanged(response.body()));
                        if (!TextUtils.isEmpty(response.body().getData().getDriverImage())) {
                            Picasso.with(getActivity()).load(response.body().getData().getDriverImage()).resize(120, 120).centerCrop().error(R.drawable.user).placeholder(R.drawable.user).into(editProfileBinding.ivUser);

                        }
                        editProfileBinding.editName.setText(response.body().getData().getDriverName());
                        editProfileBinding.tvName.setText(response.body().getData().getDriverName());

                    } else {
                        Log.d("PROFILE_RES", response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }


        return editProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        editProfileBinding.fabImagePickup.setOnClickListener(this);
        editProfileBinding.btnSave.setOnClickListener(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabImagePickup:

                PickSetup setup = new PickSetup().setTitle("Choose")
                        .setTitleColor(R.color.colorAccent)
                        .setSystemDialog(false);

                PickImageDialog.build(setup, new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {


                        Log.e("RESULT", r.getPath());
                        if (r.getError() == null) {

                            r.getBitmap();
                            r.getError();


                            imagePath = r.getUri();
                            //If you want the Uri.
                            //Mandatory to refresh image from Uri.
                            //getImageView().setImageURI(null);

                            //Setting the real returned image.
                            //getImageView().setImageURI(r.getUri());

                            Log.e("RESULT", r.getPath());
                            //If you want the Bitmap.
                            editProfileBinding.ivUser.setImageBitmap(r.getBitmap());


                        } else {
                            //Handle possible errors
                            Toast.makeText(getActivity(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }).show(getFragmentManager());


                break;
            case R.id.btnSave:

                if (TextUtils.isEmpty(editProfileBinding.editName.getText())) {

                    showMessageInSnackBar("Your zone can't be empty");

                } else {

                    if (!TextUtils.isEmpty(editProfileBinding.editPassword.getText()) || !TextUtils.isEmpty(editProfileBinding.EditReEnterPassword.getText())) {
                        if (!editProfileBinding.editPassword.getText().toString().trim().equals(editProfileBinding.EditReEnterPassword.getText().toString().trim())) {
                            showMessageInSnackBar("Your password doesn't match");
                        } else {
                            showProgressBarWithMessage("Saving your profile");
                            savingProfile(editProfileBinding.editName.getText().toString().trim(), editProfileBinding.editPassword.getText().toString(), imagePath);
                        }

                    } else {
                        showProgressBarWithMessage("Saving your profile");
                        savingProfile(editProfileBinding.editName.getText().toString().trim(), null, imagePath);

                    }

                }



              /*  if (TextUtils.isEmpty(editProfileBinding.editName.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "Name Can't be left empty", Toast.LENGTH_SHORT).show();
                } else {
                    new EditProfilePresenter(apiService, ProfileFragment.this, editProfileBinding.editName.getText().toString().trim(), imagePath, getActivity());
                }*/


                break;
        }
    }

    private void savingProfile(String name, String password2, Uri imagePath) {


        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), AppController.sharedPreferencesCompat.getInt(AppConstants.DRIVER_ID, 0)+"");
        RequestBody etName = RequestBody.create(MediaType.parse("text/plain"), name);

        Call<UserProfileResponse> call = apiService.editDriverProfile(userId, etName, password2 == null ? null : RequestBody.create(MediaType.parse("text/plain"), password2), imagePath == null ? null : prepareFilePart("driver_pic", imagePath));

        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    if (response.body().getStatus()) {
                        ((AppController) getActivity().getApplication()).bus().send(new DriverHistoryChanged(response.body()));
                        showMessageInSnackBar(response.body().getMessage());
                        AppController.getSharedPrefEditor().putString(AppConstants.DRIVER_NAME, response.body().getData().getDriverName()).apply();
                        editProfileBinding.editName.setText(response.body().getData().getDriverName());
                        editProfileBinding.tvName.setText(response.body().getData().getDriverName());
                        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), AppController.sharedPreferencesCompat.getString(AppConstants.IMAGE_URL, ""));
                    }
                }else{
                    pDialog.dismiss();
                    showMessageInSnackBar(response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                pDialog.dismiss();
                showMessageInSnackBar(t.getMessage());
            }
        });
    }

    @Override
    public void profileSavedSuccessfully() {
        pDialog.dismiss();
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and zone
        void onFragmentInteraction(Uri uri);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(getActivity(), fileUri);

        File compressedImageFile = Compressor.getDefault(getActivity()).compressToFile(file);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        compressedImageFile
                );

        // MultipartBody.Part is used to send also the actual file zone
        return MultipartBody.Part.createFormData(partName, compressedImageFile.getName(), requestFile);
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }
}
