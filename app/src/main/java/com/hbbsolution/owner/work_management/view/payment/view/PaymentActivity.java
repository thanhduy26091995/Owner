package com.hbbsolution.owner.work_management.view.payment.view;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.model.liabilities.LiabilitiesHistory;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.paymentonline.ui.activity.PaymentOnlineActivity;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.billGv24.BillGv24Response;
import com.hbbsolution.owner.work_management.model.chekout.DataBill;
import com.hbbsolution.owner.work_management.model.workmanagerpending.DatumPending;
import com.hbbsolution.owner.work_management.view.detail.DetailJobDoingActivity;
import com.hbbsolution.owner.work_management.view.payment.presenter.PaymentPresenter;
import com.hbbsolution.owner.work_management.view.workmanager.WorkManagementActivity;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tantr on 5/19/2017.
 */

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, PaymentView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvJob)
    TextView tvJob;
    @BindView(R.id.tvTypeJob)
    TextView tvTypeJob;
    @BindView(R.id.payment_helper_name)
    TextView payment_helper_name;
    @BindView(R.id.payment_helper_address)
    TextView payment_helper_address;
    @BindView(R.id.payment_money)
    TextView payment_money;
//    @BindView(R.id.payment_timework)
//    TextView payment_timework;
    @BindView(R.id.payment_date)
    TextView payment_date;
    @BindView(R.id.payment_money_account)
    TextView payment_money_account;
    @BindView(R.id.payment_total)
    TextView payment_total;
    @BindView(R.id.img_job_type)
    CircleImageView img_job_type;
    @BindView(R.id.payment_avatar)
    CircleImageView payment_avatar;
    @BindView(R.id.rela_info)
    RelativeLayout rela_info;
    @BindView(R.id.lo_payment_online)
    LinearLayout lo_payment_online;
    @BindView(R.id.lo_Gv24)
    LinearLayout lo_Gv24;
    @BindView(R.id.txt_lo_payment)
    TextView txt_lo_payment;
    @BindView(R.id.progressPayment)
    ProgressBar progressPayment;
    @BindView(R.id.lo_paymentbymoney)
    LinearLayout lo_paymentbymoney;
    @BindView(R.id.txtSeriBill)
    TextView txtSeriBill;

    private LiabilitiesHistory mLiabilitiesHistory;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat formatHour = new SimpleDateFormat("H");
    private Date date, time;
    private long elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds;
    private String mHourOfWork, mMinutesOFWork, mSecondOfWork, timework = "";
    private PaymentPresenter mPaymentPresenter;
    private DatumPending mDatum;
    private DataBill mDataBill;

    public static Activity mPaymentActivity = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_impossible);
        mPaymentActivity = this;
        ButterKnife.bind(this);
        setToolbar();
        mPaymentPresenter = new PaymentPresenter(this);
        mPaymentPresenter.getWallet();
        setData();
        setEventClick();
//        lo_Gv24.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    public void setToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setData() {
        date = new Date();
        Bundle extras = getIntent().getExtras();
        Bundle bdDataBill = getIntent().getBundleExtra("databill");
        mLiabilitiesHistory = (LiabilitiesHistory) extras.getSerializable("liability");

        if (mLiabilitiesHistory != null) {
            tvJob.setText(mLiabilitiesHistory.getTask().getInfo().getTitle());
            tvTypeJob.setText(mLiabilitiesHistory.getTask().getInfo().getWork().getName());
            txtSeriBill.setText(mLiabilitiesHistory.getId());
            payment_helper_name.setText(mLiabilitiesHistory.getTask().getStakeholders().getReceived().getInfo().getName());
            payment_helper_address.setText(mLiabilitiesHistory.getTask().getStakeholders().getReceived().getInfo().getAddress().getName());
            payment_money.setText(NumberFormat.getNumberInstance(Locale.GERMANY).format(mLiabilitiesHistory.getTask().getStakeholders().getReceived().getWorkInfo().getPrice())+" ");
            payment_date.setText(getResources().getString(R.string.payment_date) + " " + formatDate.format(date));
//            payment_timework.setText(getResources().getString(R.string.timework) + " " + getTimeDoWork(mLiabilitiesHistory.getPeriod()));
            payment_total.setText(getResources().getString(R.string.totalprice) + " " + NumberFormat.getNumberInstance(Locale.GERMANY).format(mLiabilitiesHistory.getPrice()) + " VND");
            if (!mLiabilitiesHistory.getTask().getInfo().getWork().getImage().equals("")) {
                Glide.with(PaymentActivity.this).load(mLiabilitiesHistory.getTask().getInfo().getWork().getImage())
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                        .centerCrop()
                        .dontAnimate()
                        .into(img_job_type);
            }
            if (!mLiabilitiesHistory.getTask().getStakeholders().getReceived().getInfo().getImage().equals("")) {
                Glide.with(PaymentActivity.this).load(mLiabilitiesHistory.getTask().getStakeholders().getReceived().getInfo().getImage())
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .centerCrop()
                        .dontAnimate()
                        .into(payment_avatar);
            }
        } else if (bdDataBill != null) {
            mDatum = (DatumPending) bdDataBill.getSerializable("mDatum");
            mDataBill = (DataBill) bdDataBill.getSerializable("datacheckout");
            tvJob.setText(mDatum.getInfo().getTitle());
            tvTypeJob.setText(mDatum.getInfo().getWork().getName());
            payment_helper_name.setText(mDatum.getStakeholders().getMadi().getInfo().getName());
            payment_helper_address.setText(mDatum.getStakeholders().getMadi().getInfo().getAddress().getName());
            payment_money.setText(NumberFormat.getNumberInstance(Locale.GERMANY).format(mDatum.getStakeholders().getMadi().getWorkInfo().getPrice())+" ");
            payment_date.setText(getResources().getString(R.string.payment_date) + " " + formatDate.format(date));
            txtSeriBill.setText(mDataBill.getId());
//            payment_timework.setText(getResources().getString(R.string.timework) + " " + getTimeDoWork(mDataBill.getPeriod()));
            payment_total.setText(getResources().getString(R.string.totalprice) + " " + NumberFormat.getNumberInstance(Locale.GERMANY).format(mDataBill.getPrice()) + " VND");
            Glide.with(PaymentActivity.this).load(mDatum.getInfo().getWork().getImage())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .dontAnimate()
                    .into(img_job_type);
            Glide.with(PaymentActivity.this).load(mDatum.getStakeholders().getMadi().getInfo().getImage())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .centerCrop()
                    .dontAnimate()
                    .into(payment_avatar);
        }
    }

    private void setEventClick() {
        rela_info.setOnClickListener(this);
        lo_payment_online.setOnClickListener(this);
        lo_Gv24.setOnClickListener(this);
        lo_paymentbymoney.setOnClickListener(this);
    }

    private String getTimeDoWork(String _timeDoWork) {
        try {
//            time = simpleDateFormat.parse(mLiabilitiesHistory.getPeriod());
            time = simpleDateFormat.parse(_timeDoWork);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(time);

        elapsedHours = calendar.get(Calendar.HOUR);

        elapsedMinutes = calendar.get(Calendar.MINUTE);

        elapsedSeconds = calendar.get(Calendar.SECOND);

        mHourOfWork = String.valueOf(elapsedHours);
        mMinutesOFWork = String.valueOf(elapsedMinutes);
        mSecondOfWork = String.valueOf(elapsedSeconds);
        if (elapsedHours == 0) {
            mHourOfWork = "";
        } else {
            timework += mHourOfWork + " " + getResources().getQuantityString(R.plurals.hour, (int) elapsedHours) + " ";
        }
        if (elapsedMinutes == 0) {
            mMinutesOFWork = "";
        } else {
            timework += mMinutesOFWork + " " + getResources().getQuantityString(R.plurals.minute, (int) elapsedMinutes) + " ";
        }
        if (elapsedSeconds == 0) {
            mSecondOfWork = "";
        } else {
            timework += mSecondOfWork + " " + getResources().getQuantityString(R.plurals.second, (int) elapsedSeconds);
        }
        return timework;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.lo_Gv24:
                confirm(1);
                break;
            case R.id.rela_info:
                intent = new Intent(PaymentActivity.this, MaidProfileActivity.class);
                intent.putExtra("work", mLiabilitiesHistory.getTask());
//                ActivityOptionsCompat historyOption =
//                        ActivityOptionsCompat
//                                .makeSceneTransitionAnimation(PaymentActivity.this, (View)findViewById(R.id.payment_avatar), "icAvatar");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    startActivity(intent, historyOption.toBundle());
//                }
//                else {
                startActivity(intent);
//                }
                break;
            case R.id.lo_payment_online:
                confirm(3);
//                Intent itPaymentOnline = new Intent(PaymentActivity.this, PaymentOnlineActivity.class);
//                startActivity(itPaymentOnline);
                break;
            case R.id.lo_paymentbymoney:
                confirm(2);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirm(final int formPayment) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(getResources().getString(R.string.notification));
        alertDialog.setMessage(getResources().getString(R.string.confirm_the_task_completed));
        alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressPayment.setVisibility(View.VISIBLE);
                txt_lo_payment.setVisibility(View.VISIBLE);
                switch (formPayment) {
                    case 1:
                        mPaymentPresenter.getInfoPaymentBill24h(mDataBill.getId());
                        break;
                    case 2:
                        mPaymentPresenter.getInfoPaymnetByMoney(mDataBill.getId());
                        break;
                    case 3:
                        mPaymentPresenter.getInfoPaymnetByOnline(mDataBill.getId());
                        break;
                }
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }


    @Override
    public void getWalletSuccess(int wallet) {
        payment_money_account.setText(getResources().getString(R.string.accountbalance)+": " + String.valueOf(NumberFormat.getNumberInstance(Locale.GERMANY).format(wallet) + " VND"));
    }

    @Override
    public void getWalletFail() {
    }

    @Override
    public void getInfoBill24h(BillGv24Response billGv24Response) {
        progressPayment.setVisibility(View.GONE);
        txt_lo_payment.setVisibility(View.GONE);
        if (billGv24Response.getStatus()) {
            PaymentSuccess();
        } else {
            ShowAlertDialog.showAlert("Thất bại", PaymentActivity.this);
        }
    }

    @Override
    public void getErrorBill24h(String error) {
        progressPayment.setVisibility(View.GONE);
        txt_lo_payment.setVisibility(View.GONE);
        Toast.makeText(PaymentActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getInfoPaymentBymoney(BillGv24Response billGv24Response) {
        progressPayment.setVisibility(View.GONE);
        txt_lo_payment.setVisibility(View.GONE);
        if (billGv24Response.getStatus()) {
            PaymentSuccess();
        } else {
            ShowAlertDialog.showAlert("Thất bại", PaymentActivity.this);
        }
    }

    @Override
    public void getErrorPaymentBymoney(String error) {

    }

    @Override
    public void getInfoPaymentByOnline(BillGv24Response billGv24Response) {
        progressPayment.setVisibility(View.GONE);
        txt_lo_payment.setVisibility(View.GONE);
        if(billGv24Response.getStatus()){
            Intent itPaymentOnline = new Intent(PaymentActivity.this, PaymentOnlineActivity.class);
            startActivity(itPaymentOnline);
        }else {
            ShowAlertDialog.showAlert("Thất bại", PaymentActivity.this);
        }
    }

    private void PaymentSuccess() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(getResources().getString(R.string.notification));
        alertDialog.setMessage("Thanh toán thành công");
        alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mPaymentActivity != null) {
                    PaymentActivity.mPaymentActivity.finish();
                    try {
                        if (DetailJobDoingActivity.mDetailJobDoingActivity != null) {
                            DetailJobDoingActivity.mDetailJobDoingActivity.finish();
                        }
                        if (WorkManagementActivity.mWorkManagementActivity != null) {
                            WorkManagementActivity.mWorkManagementActivity.finish();
                        }
                    } catch (Exception e) {

                    }
                }
            }
        });
        alertDialog.show();
    }

}
