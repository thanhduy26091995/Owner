package com.hbbsolution.owner.maid_near_by_new_version.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.ViewPagerAdapter;
import com.hbbsolution.owner.base.BaseActivity;
import com.hbbsolution.owner.home.view.HomeActivity;
import com.hbbsolution.owner.maid_near_by_new_version.filter.view.FilterActivity;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.utils.Constants;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 08/09/2017.
 */

public class MaidNearByNewActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.text_title)
    TextView mTextTitle;

    private boolean isFromSignIn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_near_by_new);
        ButterKnife.bind(this);
        setupComponents();
    }

    private void setupComponents() {
        //get intent
        isFromSignIn = getIntent().getBooleanExtra("FromSignIn", false);
        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        mTextTitle.setText(getResources().getString(R.string.home_maid_around));

        setupViewPagerUser(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);
        //event change tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.FILTER_MAID_INTENT && resultCode == RESULT_OK) {
            try {
                List<Maid> maidList = (List<Maid>) data.getSerializableExtra(Constants.MAID_LIST);
                //update data
                MaidNearByListFragment.getInstance().updateListMaid(maidList);
                MaidNearByMapFragment.getInstance().updateDataMap(maidList);
            } catch (Exception e) {

            }
        }
    }

    private void setupViewPagerUser(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MaidNearByListFragment(), getString(R.string.maid_near_by_list));
        adapter.addFragment(new MaidNearByMapFragment(), getString(R.string.maid_near_by_map));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter_maid, menu);
        menu.findItem(R.id.action_filter).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_sliders)
                        .color(R.color.home_background_history)
                        .colorRes(R.color.home_background_history)
                        .sizeDp(24)
                        .actionBarSize()
        );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isFromSignIn) {
                finish();
            } else {
                Intent intentHome = new Intent(MaidNearByNewActivity.this, HomeActivity.class);
                startActivity(intentHome);
                finish();
            }

        } else if (item.getItemId() == R.id.action_filter) {
            // if (location != null) {
            Intent intent = new Intent(MaidNearByNewActivity.this, FilterActivity.class);
//            intent.putExtra(Constants.LAT, latitude);
//            intent.putExtra(Constants.LNG, longitude);
            startActivityForResult(intent, Constants.FILTER_MAID_INTENT);
            // }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isFromSignIn) {
            finish();
        } else {
            Intent intentHome = new Intent(MaidNearByNewActivity.this, HomeActivity.class);
            startActivity(intentHome);
            finish();
        }
    }
}
