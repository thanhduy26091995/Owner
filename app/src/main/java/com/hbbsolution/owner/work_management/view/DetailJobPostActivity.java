package com.hbbsolution.owner.work_management.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.IconTextView;

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
            case R.id.job_psot_edit_toothbar:
                Toast.makeText(DetailJobPostActivity.this, "Edit", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
