package com.hbbsolution.owner.work_management.view.workmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.ViewPagerAdapter;
import com.hbbsolution.owner.base.AuthenticationBaseActivity;
import com.hbbsolution.owner.home.view.HomeActivity;
import com.hbbsolution.owner.utils.ConnectivityReceiver;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.NonSwipeableViewPager;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.view.jobpost.JobPostActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by buivu on 04/05/2017.
 */

public class WorkManagementActivity extends AuthenticationBaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener  {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.management_title_toothbar)
    TextView txtManagement_title_toothbar;
    @BindView(R.id.management_compose_toothbar)
    LinearLayout txtManagement_compose_toothbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    NonSwipeableViewPager mViewPager;
    @BindView(R.id.imgNo_internet)
    ImageView imgNo_internet;

    private Integer tabMore, mQuantityJobPost;
    private boolean isPause = false, mTab = false;
    private int mPositionTab = -1;
    private ViewPagerAdapter adapter;

    public static Activity mWorkManagementActivity = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_management);
        mWorkManagementActivity = this;
        ButterKnife.bind(this);
        checkConnection();

        //setupView
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtManagement_compose_toothbar.setOnClickListener(this);
        imgNo_internet.setOnClickListener(this);
        createFragment();
        tabMore = getIntent().getIntExtra("tabMore", 0);
        if (tabMore != null) {
            mViewPager.setCurrentItem(tabMore);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            EventBus.getDefault().postSticky(true);
            EventBus.getDefault().postSticky("0");
            Intent intentHome = new Intent(WorkManagementActivity.this, HomeActivity.class);
            startActivity(intentHome);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    private void createFragment() {
        mViewPager = (NonSwipeableViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        setupViewPagerUser(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);

    }

    private void setupViewPagerUser(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new JobPostedFragment(), getResources().getString(R.string.posted_work));
        adapter.addFragment(new JobPendingFragment(), getResources().getString(R.string.assigned));
        adapter.addFragment(new JobDoingFragment(), getResources().getString(R.string.running_work));
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.management_compose_toothbar:
                try {
                    if (mQuantityJobPost < 10) {
                        Intent intent = new Intent(WorkManagementActivity.this, JobPostActivity.class);
                        startActivity(intent);
                    } else {
                        ShowAlertDialog.showAlert(getResources().getString(R.string.check_number_job_post), WorkManagementActivity.this);
                    }
                }catch (Exception e){
                    ShowAlertDialog.showAlert(getResources().getString(R.string.check_number_job_post), WorkManagementActivity.this);
                }
                break;

            case R.id.imgNo_internet:
                boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    imgNo_internet.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onPause() {
        isPause = true;
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (isPause) {
            if (mTab) {
                this.finish();
                Intent refresh = new Intent(this, WorkManagementActivity.class);
                startActivity(refresh);
//                adapter.clearFragment();
//                createFragment();
//                mViewPager.setCurrentItem(mPositionTab);
                mPositionTab = -1;
                isPause = false;
                mTab = false;
            }
        } else {
            if (mPositionTab == -1) {
                mViewPager.setCurrentItem(0);
            } else {
                mViewPager.setCurrentItem(mPositionTab);
                mPositionTab = -1;
            }
        }

        if (Constants.isLoadTabDoing) {
            mViewPager.setCurrentItem(2);
            Constants.isLoadTabDoing = false;
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().postSticky(true);
        EventBus.getDefault().postSticky("0");
        Intent intentHome = new Intent(WorkManagementActivity.this, HomeActivity.class);
        startActivity(intentHome);
        finish();
    }

    public void onEventMainThread(Integer quantityJobPost) {
        mQuantityJobPost = quantityJobPost;
    }

    public void onEventMainThread(Boolean isTab) {
        mTab = isTab;
    }

    public void onEventMainThread(String positionTab) {
        mPositionTab = Integer.parseInt(positionTab);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            imgNo_internet.setVisibility(View.VISIBLE);
        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            imgNo_internet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void responseCheckToken() {
        super.responseCheckToken();
    }
}
