package com.hbbsolution.owner.history.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.model.Datum;
import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailWorkHistoryActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.detail_work_history_type)
    ImageView imgJobType;
    @BindView(R.id.detail_work_history_tvJob)
    TextView tvJob;
    @BindView(R.id.detail_work_history_tvWork)
    TextView tvWork;
    @BindView(R.id.detail_work_history_tvContent)
    TextView tvContent;
    @BindView(R.id.detail_work_history_tvSalary)
    TextView tvSalary;
    @BindView(R.id.detail_work_history_tvDate)
    TextView tvDate;
    @BindView(R.id.detail_work_history_tvTime)
    TextView tvTime;
    @BindView(R.id.detail_work_history_tvAddress)
    TextView tvAddress;

    @BindView(R.id.detail_helper_history_avatar)
    ImageView imgHelper;
    @BindView(R.id.detail_helper_history_helper_name)
    TextView tvNameHelper;
    @BindView(R.id.detail_work_history_helper_address)
    TextView tvAddressHelper;

    private Datum datum;
    private String date;
    private String startTime, endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_work_history);
        ButterKnife.bind(this);
        setToolbar();
        getData();
    }

    public void setToolbar()
    {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void getData()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            datum = (Datum) extras.getSerializable("work");
        }
        Picasso.with(this).load(datum.getInfo().getWork().getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(imgJobType);
        tvJob.setText(datum.getInfo().getTitle());
        tvWork.setText(datum.getInfo().getWork().getName());
        tvContent.setText(datum.getInfo().getDescription());
        tvSalary.setText(String.valueOf(datum.getInfo().getPrice()));

        tvAddress.setText(datum.getInfo().getAddress().getName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat time = new SimpleDateFormat("H:mm a", Locale.US);
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.US);
        // OVERRIDE SOME symbols WHILE RETAINING OTHERS
        symbols.setAmPmStrings(new String[] { "am", "pm" });
        time.setDateFormatSymbols(symbols);
        try {
            Date endDate = simpleDateFormat.parse(datum.getInfo().getTime().getEndAt());
            Date startDate = simpleDateFormat.parse(datum.getInfo().getTime().getStartAt());
            date = dates.format(endDate);
            startTime = time.format(startDate);
            endTime = time.format(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvDate.setText(date);
        tvTime.setText(startTime.replace(":","h") + " - "+endTime.replace(":","h"));

        Picasso.with(this).load(datum.getStakeholders().getReceived().getInfo().getImage())
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(imgHelper);
        tvNameHelper.setText(datum.getStakeholders().getReceived().getInfo().getName());
        tvAddressHelper.setText(datum.getStakeholders().getReceived().getInfo().getAddress().getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
