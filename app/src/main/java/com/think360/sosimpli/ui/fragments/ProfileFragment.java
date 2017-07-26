package com.think360.sosimpli.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.think360.sosimpli.AppController;
import com.think360.sosimpli.R;
import com.think360.sosimpli.databinding.EditProfileBinding;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.model.user.UserProfileResponse;
import com.think360.sosimpli.presenter.EditProfilePresenter;
import com.think360.sosimpli.utils.AppConstants;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener, EditProfilePresenter.View {
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
            Picasso.with(getActivity()).load(AppController.sharedPreferencesCompat.getString(AppConstants.WORKER_PROFILE_IMAGE_URL, "")).error(R.drawable.user).placeholder(R.drawable.user).into(editProfileBinding.ivUser);
        }
        if (!TextUtils.isEmpty(AppController.sharedPreferencesCompat.getString(AppConstants.DRIVER_NAME, ""))) {
            editProfileBinding.editName.setText(AppController.sharedPreferencesCompat.getString(AppConstants.DRIVER_NAME, ""));
            editProfileBinding.tvName.setText(AppController.sharedPreferencesCompat.getString(AppConstants.DRIVER_NAME, ""));
        }
        if (AppController.sharedPreferencesCompat.getInt(AppConstants.DRIVER_ID, 0) != 0) {
            apiService.getDriverHistory(AppController.sharedPreferencesCompat.getInt(AppConstants.DRIVER_ID, 0)).enqueue(new Callback<UserProfileResponse>() {
                @Override
                public void onResponse(Call<UserProfileResponse> call, retrofit2.Response<UserProfileResponse> response) {
                    if (response.isSuccessful()) {
                        if (!TextUtils.isEmpty(response.body().getData().getDriverImage())) {
                            Picasso.with(getActivity()).load(response.body().getData().getDriverImage()).error(R.drawable.user).placeholder(R.drawable.user).into(editProfileBinding.ivUser);
                        }
                    } else {
                        Log.d("PROFILE_RES", response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
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

                if (TextUtils.isEmpty(editProfileBinding.editName.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "Name Can't be left empty", Toast.LENGTH_SHORT).show();
                } else {
                    new EditProfilePresenter(apiService, ProfileFragment.this, editProfileBinding.editName.getText().toString().trim(), imagePath, getActivity());
                }


                break;
        }
    }

    @Override
    public void profileSavedSuccessfully() {

    }

    @Override
    public void onError(Throwable t) {

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
