package com.think360.sosimpli.ui.fragments;

/**
 * Created by surinder on 25-Jul-17.
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.think360.sosimpli.BR;
import com.think360.sosimpli.R;
import com.think360.sosimpli.adapter.RecyclerBindingAdapter;
import com.think360.sosimpli.model.Zone;
import com.think360.sosimpli.model.city.CityResponse;
import com.think360.sosimpli.model.country.Country;
import com.think360.sosimpli.model.states.StateResponse;
import com.think360.sosimpli.widgets.DividerItemDecoration;

import java.util.AbstractList;

/**
 * Created by surinder on 02-Apr-17.
 */

public class CountriesDialogFragment extends DialogFragment implements View.OnClickListener, RecyclerBindingAdapter.OnItemClickListener {


    private AbstractList<? extends Object> arrayList;
    private CallBack callBack;
    private RecyclerBindingAdapter recyclerBindingAdapter;

    public static CountriesDialogFragment newInstance() {
        return new CountriesDialogFragment();
    }

    public void setList(AbstractList<? extends Object> arrayList, CallBack callBack) {
        this.arrayList = arrayList;
        this.callBack = callBack;
    }


    public interface CallBack {
        public void callBackMethod(Object object);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_dialog, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View dialog, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(dialog, savedInstanceState);


        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.recyclerview_divider));


        if (arrayList.get(0) instanceof Country)
            recyclerBindingAdapter = new RecyclerBindingAdapter<>(R.layout.single_item_category, BR.category, arrayList);
        if (arrayList.get(0) instanceof StateResponse.State)
            recyclerBindingAdapter = new RecyclerBindingAdapter<>(R.layout.single_item_state, BR.category, arrayList);
        if (arrayList.get(0) instanceof CityResponse.City)
            recyclerBindingAdapter = new RecyclerBindingAdapter<>(R.layout.single_item_city, BR.category, arrayList);

        if(arrayList.get(0) instanceof Zone){
            recyclerBindingAdapter = new RecyclerBindingAdapter<>(R.layout.single_item_zone, BR.category, arrayList);
        }

        recyclerView.setAdapter(recyclerBindingAdapter);

        recyclerBindingAdapter.setOnItemClickListener(CountriesDialogFragment.this);


        // Call<CategoryModel> categoryModelCall = promoAnalyticsServices.getCategories();
    /*    categoryModelCall.enqueue(new Callback<CategoryModel>() {


            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                if (response.body().getStatus()) {
                    RecyclerBindingAdapter recyclerBindingAdapter = new RecyclerBindingAdapter<Datum>(R.layout.single_item_category, BR.category, response.body().getData());
                    recyclerView.setAdapter(recyclerBindingAdapter);

                    recyclerBindingAdapter.setOnItemClickListener(CountriesDialogFragment.this);

                    // fragmentDealsOnMapBinding.searchLayout.autoCategorySearch.setAdapter(mCategoryAdapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {

            }
        });*/


    }

    @Override
    public void onResume() {
        super.onResume();
        //  BusProvider.getInstance().register(this);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    @Override
    public void onPause() {
        super.onPause();
        // BusProvider.getInstance().unregister(this);
    }


   /* @Produce
    public CategoryChangeFromMap produceCategoryChangeEvent() {
        // Provide an initial value for location based on the last known position.
        return new CategoryChangeFromMap(datum);
    }*/

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(int position, Object item) {


            callBack.callBackMethod(item);
            dismiss();



        //BusProvider.getInstance().post(produceCategoryChangeEvent());

    }
}
