package com.think360.sosimpli.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.think360.sosimpli.AppController;
import com.think360.sosimpli.FragmentDrawer;
import com.think360.sosimpli.R;
import com.think360.sosimpli.adapter.PagerAdapter;
import com.think360.sosimpli.ui.fragments.AvailabilityFragment;
import com.think360.sosimpli.ui.fragments.ProfileFragment;
import com.think360.sosimpli.ui.fragments.ScheduleFragment;
import com.think360.sosimpli.ui.fragments.SoSFragment;
import com.think360.sosimpli.widgets.NonSwipeableViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity implements ScheduleFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener, SoSFragment.OnFragmentInteractionListener, FragmentDrawer.FragmentDrawerListener {


/*    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;*/

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.viewPager)
    protected NonSwipeableViewPager viewPager;


    @BindView(R.id.bottomBar)
    protected BottomBar bottomBar;

    private TextView title;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppController) getApplication()).getComponent().inject(this);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home1);
        ButterKnife.bind(this);

        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);


        title = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));


        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), getFragmentArrrayList()));
        viewPager.setOffscreenPageLimit(3);
        // toolbar.setTitle("Historico");
  /*      bottomBar.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(@IdRes int i, int i1, boolean b) {

                switch (i) {
                    case R.id.bbn_item1:
                        activityHomeBinding.toolbar.toolBarTitle.setText("Historico");
                        activityHomeBinding.viewPager.setCurrentItem(0);
                        break;
                    case R.id.bbn_item2:
                        activityHomeBinding.toolbar.toolBarTitle.setText("Trabajo");
                        activityHomeBinding.viewPager.setCurrentItem(1);
                        break;
                    case R.id.bbn_item3:
                        activityHomeBinding.toolbar.toolBarTitle.setText("Ajustes");
                        activityHomeBinding.viewPager.setCurrentItem(2);
                        break;

                }
            }

            @Override
            public void onMenuItemReselect(@IdRes int i, int i1, boolean b) {

            }
        });*/

        // We're doing nothing with this listener here this time. Check example usage
        // from ThreeTabsActivity on how to use it.
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId) {
                    case R.id.tab_favorites:
                        viewPager.setCurrentItem(0);
                        toolbar.setTitle("Schedule List");
                        break;
                    case R.id.tab_nearby:
                        toolbar.setTitle("Assigned Schedule");
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_friends1:
                        toolbar.setTitle("SOS Notifications");
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.tab_friend:
                        toolbar.setTitle("Profile Settings");
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_friends1) {
                    // The tab with id R.id.tab_favorites was reselected,
                    // change your content accordingly.
                    // Remove the badge when you're done with it.
                    BottomBarTab nearby = bottomBar.getTabWithId(R.id.tab_friends1);
                    nearby.removeBadge();
                }
            }
        });

        BottomBarTab nearby = bottomBar.getTabWithId(R.id.tab_friends1);
        nearby.setBadgeCount(5);



        /*bottomBar.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(@IdRes int i, int i1, boolean b) {

                switch (i) {
                    case R.id.bbn_item1:
                       *//* activityHomeBinding.toolbar.toolBarTitle.setText("Historico");
                        activityHomeBinding.viewPager.setCurrentItem(0);*//*
                        break;
                    case R.id.bbn_item2:
                      *//*  activityHomeBinding.toolbar.toolBarTitle.setText("Trabajo");
                        activityHomeBinding.viewPager.setCurrentItem(1);*//*
                        break;
                    case R.id.bbn_item3:
                    *//*    activityHomeBinding.toolbar.toolBarTitle.setText("Ajustes");
                        activityHomeBinding.viewPager.setCurrentItem(2);*//*
                        break;

                }
            }

            @Override
            public void onMenuItemReselect(@IdRes int i, int i1, boolean b) {

            }
        });*/


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private ArrayList<Fragment> getFragmentArrrayList() {
        ArrayList<Fragment> fragmentSparseArray = new ArrayList<>();
        fragmentSparseArray.add(new AvailabilityFragment());
        fragmentSparseArray.add(ScheduleFragment.newInstance("", ""));
        fragmentSparseArray.add(SoSFragment.newInstance("", ""));
        fragmentSparseArray.add(ProfileFragment.newInstance("", ""));
        return fragmentSparseArray;

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        switch (position) {
            case 0:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(HomeActivity.this, AddAvailabilityActivity.class));
                        overridePendingTransition(R.anim.zoom_exit, 0);
                    }
                }, 200);


                break;
            case 1:
                break;
            case 2:

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(HomeActivity.this, ChangeScheduleActivity.class));
                        overridePendingTransition(R.anim.zoom_exit, 0);
                    }
                }, 200);

                break;
            case 3:

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        overridePendingTransition(R.anim.zoom_exit, 0);
                        finish();
                    }
                }, 200);

                break;
        }


    }
}
