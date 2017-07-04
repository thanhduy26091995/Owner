package com.hbbsolution.owner.work_management.view.detail;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.utils.WorkTimeValidate;
import com.hbbsolution.owner.work_management.model.chekout.CheckOutResponse;
import com.hbbsolution.owner.work_management.model.workmanagerpending.DatumPending;
import com.hbbsolution.owner.work_management.presenter.CheckOutAndBillPresenter;
import com.hbbsolution.owner.work_management.view.payment.view.PaymentActivity;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 5/14/2017.
 */

public class DetailJobDoingActivity extends AppCompatActivity implements View.OnClickListener, CheckOutView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.manager_doing_title_toothbar)
    TextView txtManager_doing_title_toothbar;
    @BindView(R.id.lo_ChosenCheckin)
    RelativeLayout lo_ChosenCheckin;
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

    private CheckOutAndBillPresenter checkOutAndBillPresenter;
    public static Activity mDetailJobDoingActivity = null;


    private DatumPending mDatum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_doing);
        mDetailJobDoingActivity = this;

        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lo_ChosenCheckin.setOnClickListener(this);
        lo_infoMaidDoing.setOnClickListener(this);
        checkOutAndBillPresenter = new CheckOutAndBillPresenter(this);

        final Intent intent = getIntent();
        mDatum = (DatumPending) intent.getSerializableExtra("mDatum");

        txtNameJobDoingInfoMaid.setText(mDatum.getStakeholders().getMadi().getInfo().getName());
        txtAddressJobDoingInfoMaid.setText(mDatum.getStakeholders().getMadi().getInfo().getAddress().getName());
        Picasso.with(this).load(mDatum.getStakeholders().getMadi().getInfo().getImage())
                .error(R.drawable.avatar)
                .placeholder(R.drawable.avatar)
                .into(img_avatarJobDoingInfoMiad);

        txtTitleJobDoing.setText(mDatum.getInfo().getTitle());
        txtTypeJobDoing.setText(mDatum.getInfo().getWork().getName());
        txtContentJobDoing.setText(mDatum.getInfo().getDescription());
        txtPriceJobDoing.setText(formatPrice(mDatum.getInfo().getPrice()));
        txtAddressJobDoing.setText(mDatum.getInfo().getAddress().getName());
        txtDateJobDoing.setText(WorkTimeValidate.getDatePostHistory(mDatum.getInfo().getTime().getEndAt()));
        String mStartTime = WorkTimeValidate.getTimeWork(mDatum.getInfo().getTime().getStartAt());
        String mEndTime = WorkTimeValidate.getTimeWork(mDatum.getInfo().getTime().getEndAt());
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
            case R.id.lo_ChosenCheckin:
                checkOutAndBillPresenter.getInfoCheckOut(mDatum.getId());
                progressDetailJobDoing.setVisibility(View.VISIBLE);
                txt_lo_infoMail.setVisibility(View.VISIBLE);
//                Intent itComment = new Intent(DetailJobDoingActivity.this, PaymentActivity.class);
//                startActivity(itComment);
                break;
            case R.id.lo_infoMaidDoing:
                Intent itInfoUser = new Intent(DetailJobDoingActivity.this, MaidProfileActivity.class);
                itInfoUser.putExtra("maid",mDatum.getStakeholders().getMadi());
                startActivity(itInfoUser);
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
    public void getInfoCheckOut(final CheckOutResponse checkOutResponse) {
        progressDetailJobDoing.setVisibility(View.GONE);
        txt_lo_infoMail.setVisibility(View.GONE);

        boolean isCheckOut = checkOutResponse.getStatus();
        if(isCheckOut) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getResources().getString(R.string.notification));
            alertDialog.setMessage(getResources().getString(R.string.work_complete));
            alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent itPayment = new Intent(DetailJobDoingActivity.this, PaymentActivity.class);
                    Bundle bdPayment = new Bundle();
                    bdPayment.putSerializable("datacheckout", checkOutResponse.getData());
                    bdPayment.putSerializable("mDatum", mDatum);
                    itPayment.putExtra("databill", bdPayment);
                    startActivity(itPayment);
                    finish();
                }
            });

            alertDialog.show();
        }
    }

    @Override
    public void getErrorCheckOut(String error) {
        progressDetailJobDoing.setVisibility(View.GONE);
        txt_lo_infoMail.setVisibility(View.GONE);
        ShowAlertDialog.showAlert(error, DetailJobDoingActivity.this);
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
}
