package com.hbbsolution.owner.work_management.view.detail;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.AuthenticationBaseActivity;
import com.hbbsolution.owner.base.IconTextView;
import com.hbbsolution.owner.model.CheckInResponse;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.utils.WorkTimeValidate;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;
import com.hbbsolution.owner.work_management.presenter.DetailJobPostPresenter;
import com.hbbsolution.owner.work_management.view.jobpost.JobPostActivity;
import com.hbbsolution.owner.work_management.view.listmaid.ListUserRecruitmentActivity;
import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 5/14/2017.
 */

public class DetailJobPostActivity extends AuthenticationBaseActivity implements DetailJobPostView, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.manager_post_title_toothbar)
    TextView txtManager_post_title_toothbar;
    @BindView(R.id.job_psot_edit_toothbar)
    IconTextView txtJob_post_edit_toothbar;
    @BindView(R.id.txtNumber_request_detail_post)
    TextView txtNumber_request_detail_post;
    @BindView(R.id.txtTitle_job_detail_post)
    TextView txtTitle_job_detail_post;
    @BindView(R.id.txtType_job_detail_post)
    TextView txtType_job_detail_post;
    @BindView(R.id.txtContent_job_detail_psot)
    TextView txtContent_job_detail_psot;
    @BindView(R.id.txtPrice_job_detail_post)
    TextView txtPrice_job_detail_post;
    @BindView(R.id.txtDate_job_detail_post)
    TextView txtDate_job_detail_post;
    @BindView(R.id.txtTime_work_doing_detail_post)
    TextView txtTime_work_doing_detail_post;
    @BindView(R.id.txtAddress_detail_post)
    TextView txtAddress_detail_post;
    @BindView(R.id.imgType_job_detail_post)
    ImageView imgType_job_detail_post;
    @BindView(R.id.lo_list_recruitment)
    LinearLayout lo_list_recruitment;
    @BindView(R.id.lo_clear_job)
    LinearLayout lo_clear_job;
    @BindView(R.id.progressDetailJobPost)
    ProgressBar progressBar;
    @BindView(R.id.txtIsTools)
    TextView txtIsTools;
    @BindView(R.id.txtExpired_request_detail_post)
    TextView txtExpired_request_detail_post;

    public static Activity mDetailJobPostActivity = null;

    private Datum mDatum;
    private DetailJobPostPresenter mDetailJobPostPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_post);
        mDetailJobPostActivity = this;
        ButterKnife.bind(this);
        checkConnectionInterner();

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDetailJobPostPresenter = new DetailJobPostPresenter(this);

        txtJob_post_edit_toothbar.setOnClickListener(this);
        lo_list_recruitment.setOnClickListener(this);
        lo_clear_job.setOnClickListener(this);

        final Intent intent = getIntent();
        mDatum = (Datum) intent.getSerializableExtra("mDatum");
        if(!compareDays(mDatum.getInfo().getTime().getEndAt())){
            txtJob_post_edit_toothbar.setVisibility(View.GONE);
            txtExpired_request_detail_post.setVisibility(View.VISIBLE);
            if (mDatum.getStakeholders().getRequest().size() > 0 ) {
                txtNumber_request_detail_post.setVisibility(View.VISIBLE);
                txtNumber_request_detail_post.setText(String.valueOf(mDatum.getStakeholders().getRequest().size()));
            } else {
                lo_list_recruitment.setVisibility(View.GONE);
                txtNumber_request_detail_post.setVisibility(View.GONE);

            }
        }else {
            txtExpired_request_detail_post.setVisibility(View.GONE);
            if (mDatum.getStakeholders().getRequest().size() > 0 ) {
                txtNumber_request_detail_post.setVisibility(View.VISIBLE);
                txtNumber_request_detail_post.setText(String.valueOf(mDatum.getStakeholders().getRequest().size()));
                lo_list_recruitment.setVisibility(View.VISIBLE);
                txtJob_post_edit_toothbar.setVisibility(View.GONE);
            } else {
//                txtNumber_request_detail_post.setVisibility(View.GONE);
                lo_list_recruitment.setVisibility(View.GONE);
                txtJob_post_edit_toothbar.setVisibility(View.VISIBLE);

            }
        }

        if(mDatum.getInfo().getTools()){
            txtIsTools.setVisibility(View.VISIBLE);
        }else {
            txtIsTools.setVisibility(View.GONE);
        }

        txtTitle_job_detail_post.setText(mDatum.getInfo().getTitle());
        txtType_job_detail_post.setText(mDatum.getInfo().getWork().getName());
        txtContent_job_detail_psot.setText(mDatum.getInfo().getDescription());
        txtPrice_job_detail_post.setText(formatPrice(mDatum.getInfo().getPrice()));
        txtAddress_detail_post.setText(mDatum.getInfo().getAddress().getName());
        txtDate_job_detail_post.setText(WorkTimeValidate.getDatePostHistory(mDatum.getInfo().getTime().getEndAt()));
        String mStartTime = WorkTimeValidate.getTimeWorkLanguage(this, mDatum.getInfo().getTime().getStartAt());
        String mEndTime = WorkTimeValidate.getTimeWorkLanguage(this, mDatum.getInfo().getTime().getEndAt());
        txtTime_work_doing_detail_post.setText( mStartTime + " - " + mEndTime);
        Picasso.with(this).load(mDatum.getInfo().getWork().getImage())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(imgType_job_detail_post);
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
        EventBus.getDefault().postSticky("0");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.job_psot_edit_toothbar:
                Intent itJobPost = new Intent(DetailJobPostActivity.this, JobPostActivity.class);
                itJobPost.putExtra("infoJobPost", mDatum);
                startActivity(itJobPost);

                break;
            case R.id.lo_list_recruitment:
                if (mDatum.getStakeholders().getRequest().size() > 0) {
                    if(compareDays(mDatum.getInfo().getTime().getEndAt())) {
                        Intent itListRecruitment = new Intent(DetailJobPostActivity.this, ListUserRecruitmentActivity.class);
                        itListRecruitment.putExtra("idTaskProcess", mDatum.getId());
                        startActivity(itListRecruitment);
                    }else {
                        ShowAlertDialog.showAlert(getResources().getString(R.string.waring), DetailJobPostActivity.this);
                    }
                }
                break;
            case R.id.lo_clear_job:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setCancelable(false);
                alertDialog.setTitle(getResources().getString(R.string.notification));
                alertDialog.setMessage(getResources().getString(R.string.delete_work_post));
                alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        progressBar.setVisibility(View.VISIBLE);
                        showProgress();
                        mDetailJobPostPresenter.deleteJob(mDatum.getId(), mDatum.getStakeholders().getOwner());
                    }
                });
                alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
                break;

        }
    }

    @Override
    public void displayNotifyJobPost(boolean isJobPost) {
//        progressBar.setVisibility(View.GONE);
        hideProgress();
        if (isJobPost) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getResources().getString(R.string.notification));
            alertDialog.setMessage(getResources().getString(R.string.notification__pass_del_job_post));
            alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EventBus.getDefault().postSticky(true);
                    finish();

                }
            });

            alertDialog.show();
        } else {
            ShowAlertDialog.showAlert(getResources().getString(R.string.thatbai), DetailJobPostActivity.this);
        }

    }

    @Override
    public void displayError(String error) {
        hideProgress();
    }

    @Override
    public void checkIn(CheckInResponse checkInResponse) {

    }

    @Override
    public void checkInFail(String error) {
        hideProgress();
    }

    private boolean compareDays(String timeEndWork) {
        long time = System.currentTimeMillis();
        DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
        Date date = parser.parseDateTime(timeEndWork).toDate();
        long millisecond = date.getTime();
        long timer = (millisecond - time);
        if(timer < 0) {
            return false;
        }
        return true;
    }

    private String formatPrice(Integer _Price) {
        String mOutputPrice = null;
        if (_Price != null && _Price != 0) {
            mOutputPrice =  String.format("%s VND", NumberFormat.getNumberInstance(Locale.GERMANY).format(_Price));
        } else if(_Price == 0){
            mOutputPrice = getResources().getString(R.string.hourly_pay);
        }
        return mOutputPrice;
    }

    @Override
    public void connectServerFail() {
        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), this);
    }
}
