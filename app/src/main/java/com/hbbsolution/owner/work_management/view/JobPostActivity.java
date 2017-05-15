package com.hbbsolution.owner.work_management.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tantr on 5/14/2017.
 */

public class JobPostActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.job_post_title_toothbar)
    TextView txtJob_post_title_toothbar;
    @BindView(R.id.job_post_txtType_job)
    EditText txtType_job;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post);
        ButterKnife.bind(this);

        //setup view
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtJob_post_title_toothbar.setText("Đăng bài");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtType_job.setOnClickListener(this);

    }

    private void eventClick() {
        View view = getLayoutInflater().inflate(R.layout.job_post_bottom_sheet, null);
        TextView txtBackup = (TextView) view.findViewById(R.id.txt_backup);
        TextView txtDetail = (TextView) view.findViewById(R.id.txt_detail);
        TextView txtOpen = (TextView) view.findViewById(R.id.txt_open);
        TextView txtCancle = (TextView) view.findViewById(R.id.txt_cancel);
        TextView txtUninstall = (TextView) view.findViewById(R.id.txt_uninstall);
        TextView txtMore = (TextView) view.findViewById(R.id.txt_More);

        final Dialog mBottomSheetDialog = new Dialog(JobPostActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        txtBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobPostActivity.this, "Dọn dẹp", Toast.LENGTH_SHORT).show();
                txtType_job.setText("Dọn dẹp");
                mBottomSheetDialog.dismiss();
            }
        });

        txtDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobPostActivity.this, "Nấu ăn", Toast.LENGTH_SHORT).show();
                txtType_job.setText("Nấu ăn");
                mBottomSheetDialog.dismiss();
            }
        });

        txtOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobPostActivity.this, "Trông em bé", Toast.LENGTH_SHORT).show();
                txtType_job.setText("Trông em bé");
                mBottomSheetDialog.dismiss();
            }
        });

        txtUninstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobPostActivity.this, "Chăm sóc thú cưng", Toast.LENGTH_SHORT).show();
                txtType_job.setText("Chăm sóc thú cưng");
                mBottomSheetDialog.dismiss();
            }
        });

        txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobPostActivity.this, "Khác", Toast.LENGTH_SHORT).show();
                txtType_job.setText("Khác");
                mBottomSheetDialog.dismiss();
            }
        });


        txtCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobPostActivity.this, "Hủy", Toast.LENGTH_SHORT).show();
                mBottomSheetDialog.dismiss();
            }
        });
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
        switch (view.getId()){
            case R.id.job_post_txtType_job:
                eventClick();
                break;
        }

    }
}
