package com.hbbsolution.owner.work_management.view.payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.model.liabilities.LiabilitiesHistory;
import com.hbbsolution.owner.history.view.CommentActivity;
import com.hbbsolution.owner.work_management.view.payment.presenter.PaymentPresenter;
import com.squareup.picasso.Picasso;

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
    @BindView(R.id.payment_timework)
    TextView payment_timework;
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

    private LiabilitiesHistory mLiabilitiesHistory;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat formatHour = new SimpleDateFormat("H");
    private Date date, time;
    private long elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds;
    private String mHourOfWork, mMinutesOFWork, mSecondOfWork, timework = "";
    private PaymentPresenter mPaymentPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_impossible);
        ButterKnife.bind(this);
        setToolbar();
        mPaymentPresenter = new PaymentPresenter(this);
        mPaymentPresenter.getWallet();
        setData();
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
        if (extras != null) {
            mLiabilitiesHistory = (LiabilitiesHistory) extras.getSerializable("liability");
        }

        if (mLiabilitiesHistory != null) {
            tvJob.setText(mLiabilitiesHistory.getTask().getInfo().getTitle());
            tvTypeJob.setText(mLiabilitiesHistory.getTask().getInfo().getWork().getName());
            payment_helper_name.setText(mLiabilitiesHistory.getTask().getStakeholders().getReceived().getInfo().getName());
            payment_helper_address.setText(mLiabilitiesHistory.getTask().getStakeholders().getReceived().getInfo().getAddress().getName());
            payment_money.setText(NumberFormat.getNumberInstance(Locale.GERMANY).format(mLiabilitiesHistory.getTask().getStakeholders().getReceived().getWorkInfo().getPrice()) + " VND/ ");
            payment_date.setText(getResources().getString(R.string.payment_date) + " " + formatDate.format(date));
            try {
                time = simpleDateFormat.parse(mLiabilitiesHistory.getPeriod());
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
            payment_timework.setText(getResources().getString(R.string.timework) + " " + timework);
            payment_total.setText(getResources().getString(R.string.totalprice) + " " + NumberFormat.getNumberInstance(Locale.GERMANY).format(mLiabilitiesHistory.getPrice()) + " VND");
            if (!mLiabilitiesHistory.getTask().getInfo().getWork().getImage().equals("")) {
                Picasso.with(PaymentActivity.this).load(mLiabilitiesHistory.getTask().getInfo().getWork().getImage())
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                        .into(img_job_type);
            }
            if (!mLiabilitiesHistory.getTask().getStakeholders().getReceived().getInfo().getImage().equals("")) {
                Picasso.with(PaymentActivity.this).load(mLiabilitiesHistory.getTask().getStakeholders().getReceived().getInfo().getImage())
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .into(payment_avatar);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case lo_Gv24:
//                confirm();
//                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirm() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(getResources().getString(R.string.notification));
        alertDialog.setMessage(getResources().getString(R.string.confirm_the_task_completed));
        alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent itPayment = new Intent(PaymentActivity.this, CommentActivity.class);
                startActivity(itPayment);
                finish();
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
        payment_money_account.setText("Số dư tài khoản GV24: " + String.valueOf(NumberFormat.getNumberInstance(Locale.GERMANY).format(wallet) + " VND"));
    }

    @Override
    public void getWalletFail() {
        payment_money_account.setText("Số dư tài khoản GV24:");
    }
}
