package com.hbbsolution.owner.report.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.model.Maid;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 22/05/2017.
 */

public class ReportMaidActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_maid_name)
    TextView mTextMaidName;
    @BindView(R.id.text_maid_address)
    TextView mTextMaidAddress;

    private Maid mMaidInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        //init toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        //get intent
        mMaidInfo = (Maid) getIntent().getSerializableExtra("maid");
        //load data
        loadData();
    }

    private void loadData() {
        if (mMaidInfo != null) {
            mTextMaidName.setText(mMaidInfo.getInfo().getUsername());
            mTextMaidAddress.setText(mMaidInfo.getInfo().getAddress().getName());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter_done, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
