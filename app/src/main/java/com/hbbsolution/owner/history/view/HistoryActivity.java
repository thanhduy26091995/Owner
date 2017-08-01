package com.hbbsolution.owner.history.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.AuthenticationBaseActivity;
import com.hbbsolution.owner.history.fragment.HistoryViewPagerFragment;
import com.hbbsolution.owner.home.view.HomeActivity;
import com.hbbsolution.owner.home.view.HomeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class HistoryActivity extends AuthenticationBaseActivity implements HomeView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout_history)
    TabLayout tabLayoutHistory;
    @BindView(R.id.view_pager_history)
    ViewPager viewPagerHistory;
    @BindView(R.id.txtNumber_Liabilities)
    TextView txtNumber_Liabilities;
    private HistoryViewPagerFragment historyViewPagerFragment;
    private Integer tab = null;

    public static boolean changeUnpaid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        setToolbar();

       // checkConnectionInterner();

        changeUnpaid =false;

        historyViewPagerFragment = new HistoryViewPagerFragment(getSupportFragmentManager(), this, 3);
        viewPagerHistory.setAdapter(historyViewPagerFragment);
        viewPagerHistory.setOffscreenPageLimit(3);
        tabLayoutHistory.setupWithViewPager(viewPagerHistory);
        tab = getIntent().getIntExtra("tab", 0);
        if (tab != null) {
            viewPagerHistory.setCurrentItem(tab);
        }

    }

    public void setToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intentHome = new Intent(HistoryActivity.this, HomeActivity.class);
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

    public void onEvent(Integer numberLiabilities) {
        if (numberLiabilities > 0) {
            txtNumber_Liabilities.setVisibility(View.VISIBLE);
            txtNumber_Liabilities.setText(String.valueOf(numberLiabilities));
        } else {
            txtNumber_Liabilities.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(changeUnpaid)
        {
            historyViewPagerFragment = new HistoryViewPagerFragment(getSupportFragmentManager(), this, 3);
            viewPagerHistory.setAdapter(historyViewPagerFragment);
            viewPagerHistory.setOffscreenPageLimit(3);
            tabLayoutHistory.setupWithViewPager(viewPagerHistory);
            viewPagerHistory.setCurrentItem(2);
            changeUnpaid=false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentHome = new Intent(HistoryActivity.this, HomeActivity.class);
        startActivity(intentHome);
        finish();
    }

    @Override
    public void responseCheckToken() {
        super.responseCheckToken();
    }
}
