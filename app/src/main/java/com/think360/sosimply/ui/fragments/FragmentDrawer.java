package com.think360.sosimply.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.think360.sosimply.AppController;
import com.think360.sosimply.R;
import com.think360.sosimply.adapter.NavigationDrawerAdapter;
import com.think360.sosimply.model.NavDrawerItem;
import com.think360.sosimply.utils.DriverHistoryChanged;
import com.think360.sosimply.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class FragmentDrawer extends Fragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();
    private static String[] titles = null;
    private NavigationDrawerAdapter adapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private TextView tvName;
    private FragmentDrawerListener drawerListener;

    private ImageView ivUser;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FragmentDrawer() {

    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();
        int drawable[] = {R.drawable.add_availability, R.drawable.schedules, R.drawable.change_schedule, R.drawable.logout};
        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem(false, titles[i], drawable[i]);
            navItem.setTitle(titles[i]);

            data.add(navItem);
        }
        return data;
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels;
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);

        compositeDisposable.add(((AppController) getActivity().getApplication()).bus().toObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                if (o instanceof DriverHistoryChanged) {
                    ((DriverHistoryChanged) o).getworkerEditProfileModel().getData().getDriverEmail();
                    if (!TextUtils.isEmpty(((DriverHistoryChanged) o).getworkerEditProfileModel().getData().getDriverImage())) {
                        Picasso.with(getActivity()).load(((DriverHistoryChanged) o).getworkerEditProfileModel().getData().getDriverImage()).error(R.drawable.user).resize(120, 120).centerCrop().placeholder(R.drawable.user).into(ivUser);
                        tvName.setText(((DriverHistoryChanged) o).getworkerEditProfileModel().getData().getDriverName().substring(0,1).toUpperCase()+((DriverHistoryChanged) o).getworkerEditProfileModel().getData().getDriverName().substring(1));
                        //String upperString = myString.substring(0,1).toUpperCase() + myString.substring(1);

                    }
                }
            }
        }));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.navigation_drawer_layout, container, false);

        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        ivUser = (ImageView) layout.findViewById(R.id.ivUser);
        tvName = (TextView) layout.findViewById(R.id.tvName);

        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.recyclerview_divider));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }


//    void unSelectAll() {
//
//        for (int i = 0; i < recyclerView.getChildCount(); i++) {
//
//            recyclerView.getChildAt(i).setBackgroundColor(Color.parseColor("#00000000"));
//        }
//    }

    interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }

    private class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());

            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                if (rv.getChildAdapterPosition(child) == 1 || rv.getChildAdapterPosition(child) == 2 || rv.getChildAdapterPosition(child) == 5 || rv.getChildAdapterPosition(child) == 6 || rv.getChildAdapterPosition(child) == 8 || rv.getChildAdapterPosition(child) == 9) {

                    clickListener.onClick(child, rv.getChildAdapterPosition(child));


                } else {
                    clickListener.onClick(child, rv.getChildAdapterPosition(child));
                }

            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }
}
