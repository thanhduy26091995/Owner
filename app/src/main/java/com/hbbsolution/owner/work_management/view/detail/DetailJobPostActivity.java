package com.hbbsolution.owner.work_management.view.detail;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.IconTextView;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;
import com.hbbsolution.owner.work_management.view.listmaid.ListUserRecruitmentActivity;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tantr on 5/14/2017.
 */

public class DetailJobPostActivity extends AppCompatActivity implements View.OnClickListener {
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
    TextView txt_edit_edit_post, txt_clear_edit_post, txt_cancel_edit_post;

    private Datum mDatum;
    private Dialog mBottomSheetDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_post);

        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtManager_post_title_toothbar.setText("Đã đăng");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtJob_post_edit_toothbar.setOnClickListener(this);
        lo_list_recruitment.setOnClickListener(this);
        lo_clear_job.setOnClickListener(this);

        final Intent intent = getIntent();
        mDatum = (Datum) intent.getSerializableExtra("mDatum");

        if (mDatum.getStakeholders().getRequest().size() > 0) {
            txtNumber_request_detail_post.setText(String.valueOf(mDatum.getStakeholders().getRequest().size()));
            txtJob_post_edit_toothbar.setVisibility(View.GONE);

        } else {
            txtNumber_request_detail_post.setVisibility(View.GONE);
            txtJob_post_edit_toothbar.setVisibility(View.VISIBLE);

        }

        txtTitle_job_detail_post.setText(mDatum.getInfo().getTitle());
        txtType_job_detail_post.setText(mDatum.getInfo().getWork().getName());
        txtContent_job_detail_psot.setText(mDatum.getInfo().getDescription());
        txtPrice_job_detail_post.setText(String.valueOf(mDatum.getInfo().getPrice()));
        txtAddress_detail_post.setText(mDatum.getInfo().getAddress().getName());
        txtDate_job_detail_post.setText(getDateStartWork(mDatum.getHistory().getUpdateAt()));
        txtTime_work_doing_detail_post.setText(getTimerDoingWork(mDatum.getInfo().getTime().getStartAt(), mDatum.getInfo().getTime().getEndAt()));
        Picasso.with(this).load(mDatum.getInfo().getWork().getImage())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(imgType_job_detail_post);
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
        switch (view.getId()) {
            case R.id.job_psot_edit_toothbar:
                if (mDatum.getStakeholders().getRequest().size() > 0) {
                    editJobPost();
                }
                break;
            case R.id.lo_list_recruitment:
                if (mDatum.getStakeholders().getRequest().size() > 0) {
                    Intent itListRecruitment = new Intent(DetailJobPostActivity.this, ListUserRecruitmentActivity.class);
                    itListRecruitment.putExtra("idTaskProcess", mDatum.getId());
                    startActivity(itListRecruitment);
                }
                break;
            case R.id.lo_clear_job:
                Toast.makeText(DetailJobPostActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txt_edit_edit_post:
                Toast.makeText(DetailJobPostActivity.this, "Chỉnh sửa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txt_clear_edit_post:
                Toast.makeText(DetailJobPostActivity.this, "Xóa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txt_cancel_edit_post:
                mBottomSheetDialog.dismiss();
                break;
        }
    }

    private void editJobPost() {
        View view = getLayoutInflater().inflate(R.layout.edit_job_post_bottom_sheet, null);
        txt_edit_edit_post = (TextView) view.findViewById(R.id.txt_edit_edit_post);
        txt_clear_edit_post = (TextView) view.findViewById(R.id.txt_clear_edit_post);
        txt_cancel_edit_post = (TextView) view.findViewById(R.id.txt_cancel_edit_post);

        mBottomSheetDialog = new Dialog(DetailJobPostActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        txt_edit_edit_post.setOnClickListener(this);
        txt_clear_edit_post.setOnClickListener(this);
        txt_cancel_edit_post.setOnClickListener(this);
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
