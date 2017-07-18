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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.AuthenticationBaseActivity;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.model.CheckInResponse;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.utils.WorkTimeValidate;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;
import com.hbbsolution.owner.work_management.presenter.DetailJobPostPresenter;
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
 * Created by tantr on 6/29/2017.
 */

public class DetailJobSentRequestActivity extends AuthenticationBaseActivity implements DetailJobPostView, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.manager_doing_title_toothbar)
    TextView txtManager_doing_title_toothbar;
    @BindView(R.id.txtNameJobDoingInfoMaid)
    TextView txtNameJobDoingInfoMaid;
    @BindView(R.id.txtAddressJobDoingInfoMaid)
    TextView txtAddressJobDoingInfoMaid;
    @BindView(R.id.img_avatarJobDoingInfoMiad)
    ImageView img_avatarJobDoingInfoMiad;
    @BindView(R.id.img_job_type)
    ImageView img_job_type;
    @BindView(R.id.txtTitleJobDoing)
    TextView txtTitleJobDoing;
    @BindView(R.id.txtTypeJobDoing)
    TextView txtTypeJobDoing;
    @BindView(R.id.txtContentJobDoing)
    TextView txtContentJobDoing;
    @BindView(R.id.txtPriceJobDoing)
    TextView txtPriceJobDoing;
    @BindView(R.id.txtDateJobDoing)
    TextView txtDateJobDoing;
    @BindView(R.id.txtTimeDoWrokJobDoing)
    TextView txtTimeDoWrokJobDoing;
    @BindView(R.id.txtAddressJobDoing)
    TextView txtAddressJobDoing;
    @BindView(R.id.lo_infoMaidDoing)
    RelativeLayout lo_infoMaidDoing;
    @BindView(R.id.txt_lo_infoMail)
    TextView txt_lo_infoMail;
    @BindView(R.id.progressDetailJobDoing)
    ProgressBar progressDetailJobDoing;
    @BindView(R.id.lo_clear_job_request)
    LinearLayout lo_clear_job_request;
    @BindView(R.id.txtExpired_request_detail_post)
    TextView txtExpired_request_detail_post;

    public static Activity mDetailJobDoingActivity = null;
    private DetailJobPostPresenter mDetailJobPostPresenter;


    private Datum mDatum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_sent_request);
        mDetailJobDoingActivity = this;

        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lo_infoMaidDoing.setOnClickListener(this);
        lo_clear_job_request.setOnClickListener(this);

        mDetailJobPostPresenter = new DetailJobPostPresenter(this);
        final Intent intent = getIntent();
        mDatum = (Datum) intent.getSerializableExtra("mDatum");

        if(!compareDays(mDatum.getInfo().getTime().getEndAt())){
            txtExpired_request_detail_post.setVisibility(View.VISIBLE);

        }else {
            txtExpired_request_detail_post.setVisibility(View.GONE);

        }

        txtNameJobDoingInfoMaid.setText(mDatum.getStakeholders().getMaid().getInfo().getName());
        txtAddressJobDoingInfoMaid.setText(mDatum.getStakeholders().getMaid().getInfo().getAddress().getName());
        Picasso.with(this).load(mDatum.getStakeholders().getMaid().getInfo().getImage())
                .error(R.drawable.avatar)
                .placeholder(R.drawable.avatar)
                .into(img_avatarJobDoingInfoMiad);

        txtTitleJobDoing.setText(mDatum.getInfo().getTitle());
        txtTypeJobDoing.setText(mDatum.getInfo().getWork().getName());
        txtContentJobDoing.setText(mDatum.getInfo().getDescription());
        txtPriceJobDoing.setText(String.format("%s VND", NumberFormat.getNumberInstance(Locale.GERMANY).format(mDatum.getInfo().getPrice())));
        txtAddressJobDoing.setText(mDatum.getInfo().getAddress().getName());
        txtDateJobDoing.setText(WorkTimeValidate.getDatePostHistory(mDatum.getInfo().getTime().getEndAt()));
        String mStartTime = WorkTimeValidate.getTimeWorkLanguage(DetailJobSentRequestActivity.this,mDatum.getInfo().getTime().getStartAt());
        String mEndTime = WorkTimeValidate.getTimeWorkLanguage(DetailJobSentRequestActivity.this,mDatum.getInfo().getTime().getEndAt());
        txtTimeDoWrokJobDoing.setText( mStartTime + " - " + mEndTime);
//        txtTimeDoWrokJobDoing.setText(getTimerDoingWork(mDatum.getInfo().getTime().getStartAt(), mDatum.getInfo().getTime().getEndAt()));
        Picasso.with(this).load(mDatum.getInfo().getWork().getImage())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(img_job_type);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            EventBus.getDefault().postSticky(false);
            EventBus.getDefault().postSticky("2");
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
            case R.id.lo_infoMaidDoing:
                Intent itInfoUser = new Intent(DetailJobSentRequestActivity.this, MaidProfileActivity.class);
                itInfoUser.putExtra("maid",mDatum.getStakeholders().getMaid());
                startActivity(itInfoUser);
                break;
            case R.id.lo_clear_job_request:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setCancelable(false);
                alertDialog.setTitle(getResources().getString(R.string.notification));
                alertDialog.setMessage(getResources().getString(R.string.notification_del_job_post));
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
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().postSticky(false);
        EventBus.getDefault().postSticky("2");
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
            ShowAlertDialog.showAlert(getResources().getString(R.string.thatbai), DetailJobSentRequestActivity.this);
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
        hideProgress();
        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), this);
    }
}
