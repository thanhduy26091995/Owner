package com.hbbsolution.owner.work_management.view.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.HelperListAdapter;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.model.Helper;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;
import com.hbbsolution.owner.work_management.model.workmanagerpending.DatumPending;
import com.hbbsolution.owner.work_management.presenter.DetailJobPostPresenter;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 5/14/2017.
 */

public class DetailJobPendingActivity extends AppCompatActivity implements DetailJobPostView,View.OnClickListener{
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
    @BindView(R.id.img_avatarMaid)
    ImageView img_avatarMaid;
    @BindView(R.id.img_TypeJob)
    ImageView img_TypeJob;
    @BindView(R.id.progressDetailJobPending)
    ProgressBar progressBar;

    private DatumPending mDatum;
    private DetailJobPostPresenter mDetailJobPostPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_pending);
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDetailJobPostPresenter = new DetailJobPostPresenter(this);

        lo_clear_job_pending.setOnClickListener(this);
        lo_infoMaid.setOnClickListener(this);

        final Intent intent = getIntent();
        mDatum = (DatumPending) intent.getSerializableExtra("mDatum");

        txtNameMaid.setText(mDatum.getStakeholders().getMadi().getInfo().getName());
        txtAddressMaid.setText(mDatum.getStakeholders().getMadi().getInfo().getAddress().getName());

        Picasso.with(this).load(mDatum.getStakeholders().getMadi().getInfo().getImage())
                .error(R.drawable.avatar)
                .placeholder(R.drawable.avatar)
                .into(img_avatarMaid);

        txtTitleJobPending.setText(mDatum.getInfo().getTitle());
        txtTypeJobPending.setText(mDatum.getInfo().getWork().getName());
        txtContentJobPending.setText(mDatum.getInfo().getDescription());
        txtPriceJobPending.setText(String.valueOf(mDatum.getInfo().getPrice()));
        txtAddressJobPending.setText(mDatum.getInfo().getAddress().getName());
        txtDateJobPending.setText(getDateStartWork(mDatum.getHistory().getUpdateAt()));
        txtTimeDoWrokJobPending.setText(getTimerDoingWork(mDatum.getInfo().getTime().getStartAt(), mDatum.getInfo().getTime().getEndAt()));
        Picasso.with(this).load(mDatum.getInfo().getWork().getImage())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(img_TypeJob);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            EventBus.getDefault().postSticky(false);
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
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().postSticky(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lo_clear_job_pending:
                String id = mDatum.getId();
                String idOwner = mDatum.getStakeholders().getOwner();
                Log.d("idrequset", id + " - " + idOwner);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setCancelable(false);
                alertDialog.setTitle("Thông báo");
                alertDialog.setMessage("Bạn có chắc muốn xóa bài đăng này !");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressBar.setVisibility(View.VISIBLE);
                        mDetailJobPostPresenter.deleteJob(mDatum.getId(), mDatum.getStakeholders().getOwner());
                    }
                });
                alertDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
                break;
            case R.id.lo_infoMaid:
                Intent itInfoUser = new Intent(DetailJobPendingActivity.this, MaidProfileActivity.class);
                itInfoUser.putExtra("maid",mDatum.getStakeholders().getMadi());
                startActivity(itInfoUser);
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

    @Override
    public void displayNotifyJobPost(boolean isJobPost) {

    }

    @Override
    public void displayError(String error) {

    }
}
