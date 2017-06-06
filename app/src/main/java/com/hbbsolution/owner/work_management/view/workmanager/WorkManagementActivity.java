package com.hbbsolution.owner.work_management.view.workmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.ViewPagerAdapter;
import com.hbbsolution.owner.base.IconTextView;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.view.jobpost.JobPostActivity;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by buivu on 04/05/2017.
 */

public class WorkManagementActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.management_title_toothbar)
    TextView txtManagement_title_toothbar;
    @BindView(R.id.management_compose_toothbar)
    IconTextView txtManagement_compose_toothbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private int tabMore, mQuantityJobPost;
    private boolean isPause = false, mTab = false;
    static int mPositionTab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_management);
        ButterKnife.bind(this);

        //setupView
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtManagement_title_toothbar.setText("Quản lý công việc");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtManagement_compose_toothbar.setOnClickListener(this);


        createFragment();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tabMore = extras.getInt("tabMore");
            mViewPager.setCurrentItem(tabMore);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        setupViewPagerUser(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);
    }

    private void setupViewPagerUser(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new JobPostedFragment(), "Đã đăng");
        adapter.addFragment(new JobPendingFragment(), "Đã phân công");
        adapter.addFragment(new JobDoingFragment(), "Đang làm");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.management_compose_toothbar:
                if (mQuantityJobPost < 10) {
                    Intent intent = new Intent(WorkManagementActivity.this, JobPostActivity.class);
                    startActivity(intent);
                } else {
                    ShowAlertDialog.showAlert("Số lượng bài đăng không được vượt quá 10 bài!", WorkManagementActivity.this);
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
                Intent refresh = new Intent(this, WorkManagementActivity.class);
                startActivity(refresh);
                mViewPager.setCurrentItem(mPositionTab);
                this.finish();
                mPositionTab = 0;
                isPause = false;
                mTab = false;
            }
        }

        if (Constants.isLoadTabDoing) {
            mViewPager.setCurrentItem(2);
            Constants.isLoadTabDoing = false;
        }
        super.onResume();
    }

    public void onEventMainThread(Integer quantityJobPost) {
        mQuantityJobPost = quantityJobPost;
    }

    public void onEventMainThread(Boolean isTab) {
        mTab = isTab;
    }

    public void onEvent(Integer positionTab) {

        mPositionTab = positionTab;
        Log.d("mPosition", mPositionTab + "");
    }
}
