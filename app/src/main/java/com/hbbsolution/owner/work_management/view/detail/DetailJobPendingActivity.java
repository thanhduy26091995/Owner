package com.hbbsolution.owner.work_management.view.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.HelperListAdapter;
import com.hbbsolution.owner.model.Helper;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tantr on 5/14/2017.
 */

public class DetailJobPendingActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lo_clear_job_pending)
    LinearLayout lo_clear_job_pending;
    @BindView(R.id.lo_infoMaid)
    RelativeLayout lo_infoMaid;
    @BindView(R.id.txtTitleJobPending)
    TextView txtTitleJobPending;
    @BindView(R.id.txtTypeJobPending)
    TextView txtTypeJobPending;
    @BindView(R.id.txtContentJobPending)
    TextView txtContentJobPending;
    @BindView(R.id.txtPriceJobPending)
    TextView txtPriceJobPending;
    @BindView(R.id.txtDateJobPending)
    TextView txtDateJobPending;
    @BindView(R.id.txtTimeDoWrokJobPending)
    TextView txtTimeDoWrokJobPending;
    @BindView(R.id.txtAddressJobPending)
    TextView txtAddressJobPending;
    @BindView(R.id.txtNameMaid)
    TextView txtNameMaid;
    @BindView(R.id.txtAddressMaid)
    TextView txtAddressMaid;

    private Datum mDatum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_pending);
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lo_clear_job_pending.setOnClickListener(this);
        lo_infoMaid.setOnClickListener(this);

        final Intent intent = getIntent();
        mDatum = (Datum) intent.getSerializableExtra("mDatum");

//        txtNameMaid.setText(mDatum.getStakeholders().);
        txtTitleJobPending.setText(mDatum.getInfo().getTitle());
        txtTypeJobPending.setText(mDatum.getInfo().getWork().getName());
        txtContentJobPending.setText(mDatum.getInfo().getDescription());
        txtPriceJobPending.setText(String.valueOf(mDatum.getInfo().getPrice()));
        txtAddressJobPending.setText(mDatum.getInfo().getAddress().getName());
        txtDateJobPending.setText(getDateStartWork(mDatum.getHistory().getUpdateAt()));
        txtTimeDoWrokJobPending.setText(getTimerDoingWork(mDatum.getInfo().getTime().getStartAt(), mDatum.getInfo().getTime().getEndAt()));

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.lo_clear_job_pending:
                Toast.makeText(DetailJobPendingActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lo_infoMaid:
                break;
        }
    }


    private String getTimerDoingWork(String startAt, String endAt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        Date dateStartAt = new DateTime(startAt).toDate();
        Date dateEndtAt = new DateTime(endAt).toDate();
        String mDateStartAt = simpleDateFormat.format(dateStartAt);
        String mDateEndAt = simpleDateFormat.format(dateEndtAt);
        String mTimeDoing = mDateStartAt + " - " + mDateEndAt;

        return mTimeDoing;
    }

    private String getDateStartWork(String dateStartWork) {
        Date date0 = new DateTime(dateStartWork).toDate();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String mDateStartWork = df.format(date0);
        return mDateStartWork;
    }
}
