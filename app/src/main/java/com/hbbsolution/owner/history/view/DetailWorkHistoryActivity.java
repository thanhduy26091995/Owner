package com.hbbsolution.owner.history.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.CommentHistoryView;
import com.hbbsolution.owner.history.model.workhistory.WorkHistory;
import com.hbbsolution.owner.history.presenter.CommentHistoryPresenter;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailWorkHistoryActivity extends AppCompatActivity implements View.OnClickListener, CommentHistoryView {
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

    @BindView(R.id.txt_history_comment)
    TextView tvComment;
    @BindView(R.id.rela_info)
    RelativeLayout rlInfo;
    @BindView(R.id.tvContentComment)
    TextView tvContentComment;
    @BindView(R.id.v_line)
    View vLine;

    private WorkHistory doc;
    private String date;
    private String startTime, endTime;
    private CommentHistoryPresenter commentHistoryPresenter;
    private int idTask;
    public static Activity detailWorkHistory;
    private static final int COMMENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_work_history);
        ButterKnife.bind(this);
        detailWorkHistory = this;
        setToolbar();
        getData();
        setEventClick();
    }

    public void setEventClick() {
        tvComment.setOnClickListener(this);
        rlInfo.setOnClickListener(this);
    }

    public void setToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            doc = (WorkHistory) extras.getSerializable("work");
        }
        tvContentComment.setVisibility(View.GONE);
        tvComment.setVisibility(View.GONE);
        commentHistoryPresenter = new CommentHistoryPresenter(this);
        commentHistoryPresenter.checkComment(doc.getId());
        Picasso.with(this).load(doc.getInfo().getWork().getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(imgJobType);
        tvJob.setText(doc.getInfo().getTitle());
        tvWork.setText(doc.getInfo().getWork().getName());
        tvContent.setText(doc.getInfo().getDescription());
        tvSalary.setText(String.valueOf(doc.getInfo().getPrice()));

        tvAddress.setText(doc.getInfo().getAddress().getName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat time = new SimpleDateFormat("H:mm a", Locale.US);
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.US);
        // OVERRIDE SOME symbols WHILE RETAINING OTHERS
        symbols.setAmPmStrings(new String[]{"am", "pm"});
        time.setDateFormatSymbols(symbols);
        try {
            Date endDate = simpleDateFormat.parse(doc.getInfo().getTime().getEndAt());
            Date startDate = simpleDateFormat.parse(doc.getInfo().getTime().getStartAt());
            date = dates.format(endDate);
            startTime = time.format(startDate);
            endTime = time.format(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvDate.setText(date);
        tvTime.setText(startTime.replace(":", "h") + " - " + endTime.replace(":", "h"));

        Picasso.with(this).load(doc.getStakeholders().getReceived().getInfo().getImage())
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(imgHelper);
        tvNameHelper.setText(doc.getStakeholders().getReceived().getInfo().getName());
        tvAddressHelper.setText(doc.getStakeholders().getReceived().getInfo().getAddress().getName());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.txt_history_comment:
                intent = new Intent(this, CommentActivity.class);
                intent.putExtra("idTask", doc.getId());
                intent.putExtra("idHelper", doc.getStakeholders().getReceived().getId());
                intent.putExtra("imgHelper", doc.getStakeholders().getReceived().getInfo().getImage());
                intent.putExtra("nameHelper", doc.getStakeholders().getReceived().getInfo().getName());
                intent.putExtra("addressHelper", doc.getStakeholders().getReceived().getInfo().getAddress());
                startActivityForResult(intent,COMMENT);
                break;
            case R.id.rela_info:
                intent = new Intent(this, MaidProfileActivity.class);
                intent.putExtra("work", doc);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void checkCommentSuccess(String message) {
        tvContentComment.setVisibility(View.VISIBLE);
        tvContentComment.setText(message);
    }

    @Override
    public void checkCommentFail() {
        tvComment.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==COMMENT)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                ShowAlertDialog.showAlert(data.getStringExtra("message"),this);
                commentHistoryPresenter.checkComment(doc.getId());
            }
        }
    }
}
