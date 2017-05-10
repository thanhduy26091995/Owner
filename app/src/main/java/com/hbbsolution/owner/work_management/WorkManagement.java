package com.hbbsolution.owner.work_management;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 04/05/2017.
 */

public class WorkManagement extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.management_title_toothbar)
    TextView txtManagement_title_toothbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private int tabMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_management);

        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtManagement_title_toothbar.setText("Quản lý công việc");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    private void createFragment() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        setupViewPagerUser(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);
    }
    private void setupViewPagerUser(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new JobPostedFragment(), "Cv đã đăng");
        adapter.addFragment(new JobPendingFragment(), "Cv chờ duyệt");
        adapter.addFragment(new JobDoingFragment(), "Cv đang làm");
        viewPager.setAdapter(adapter);
    }
}
