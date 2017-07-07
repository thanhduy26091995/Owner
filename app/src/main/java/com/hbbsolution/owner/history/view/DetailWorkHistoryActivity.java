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

import com.bumptech.glide.Glide;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.CommentHistoryView;
import com.hbbsolution.owner.history.model.workhistory.WorkHistory;
import com.hbbsolution.owner.history.presenter.CommentHistoryPresenter;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.utils.WorkTimeValidate;

import java.text.NumberFormat;
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
        tvContentComment.setVisibility(View.GONE);
        tvComment.setVisibility(View.GONE);
        commentHistoryPresenter = new CommentHistoryPresenter(this);
        if (extras != null) {
            doc = (WorkHistory) extras.getSerializable("work");
            commentHistoryPresenter.checkComment(doc.getId());
            Glide.with(this).load(doc.getInfo().getWork().getImage())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .dontAnimate()
                    .into(imgJobType);
            tvJob.setText(doc.getInfo().getTitle());
            tvWork.setText(doc.getInfo().getWork().getName());
            tvContent.setText(doc.getInfo().getDescription());

            tvSalary.setText(formatPrice(doc.getInfo().getPrice()));

            tvAddress.setText(doc.getInfo().getAddress().getName());

            tvDate.setText(WorkTimeValidate.getDatePostHistory(doc.getInfo().getTime().getEndAt()));
            tvTime.setText(WorkTimeValidate.getTimeWorkLanguage(DetailWorkHistoryActivity.this,doc.getInfo().getTime().getStartAt()) + " - " + WorkTimeValidate.getTimeWorkLanguage(DetailWorkHistoryActivity.this,doc.getInfo().getTime().getEndAt()));

            if(!doc.getStakeholders().getReceived().getInfo().getImage().equals("")) {
                Glide.with(this).load(doc.getStakeholders().getReceived().getInfo().getImage())
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .centerCrop()
                        .dontAnimate()
                        .into(imgHelper);
            }
            tvNameHelper.setText(doc.getStakeholders().getReceived().getInfo().getName());
            tvAddressHelper.setText(doc.getStakeholders().getReceived().getInfo().getAddress().getName());
        }
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
                Bundle mbundleComment = new Bundle();
                mbundleComment.putString("idTask", doc.getId());
                mbundleComment.putString("idHelper", doc.getStakeholders().getReceived().getId());
                mbundleComment.putString("imgHelper", doc.getStakeholders().getReceived().getInfo().getImage());
                mbundleComment.putString("nameHelper", doc.getStakeholders().getReceived().getInfo().getName());
                mbundleComment.putString("addressHelper", doc.getStakeholders().getReceived().getInfo().getAddress().getName());
                intent.putExtra("mbundleComment", mbundleComment);
                startActivityForResult(intent,COMMENT);
                break;
            case R.id.rela_info:
                intent = new Intent(this, MaidProfileActivity.class);
                intent.putExtra("work", doc);
//                ActivityOptionsCompat historyOption =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(DetailWorkHistoryActivity.this, (View)v.findViewById(R.id.detail_helper_history_avatar), "icAvatar");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    startActivity(intent, historyOption.toBundle());
//                }
//                else {
                    startActivity(intent);
//                }
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
        tvComment.setVisibility(View.GONE);
        tvContentComment.setVisibility(View.VISIBLE);
        tvContentComment.setText(message);
    }

    @Override
    public void checkCommentFail() {
        tvComment.setVisibility(View.VISIBLE);
        tvContentComment.setVisibility(View.GONE);
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
                ShowAlertDialog.showAlert(getResources().getString(R.string.commentsuccess),this);
                commentHistoryPresenter.checkComment(doc.getId());
            }
        }
    }

    private String formatPrice(Integer _Price) {
        String mOutputPrice = null;
        if (_Price != null && _Price != 0) {
            mOutputPrice = String.format("%s VND", NumberFormat.getNumberInstance(Locale.GERMANY).format(_Price));
        } else if (_Price == 0) {
            mOutputPrice = getResources().getString(R.string.hourly_pay);
        }
        return mOutputPrice;
    }
}
