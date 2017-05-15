package com.hbbsolution.owner.work_management.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class DetailJobPendingActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.manager_pending_title_toothbar)
    TextView txtManager_pending_title_toothbar;
    @BindView(R.id.lo_list_recruitment)
    LinearLayout lo_list_recruitment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_pending);
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtManager_pending_title_toothbar.setText("Chờ duyệt");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lo_list_recruitment.setOnClickListener(this);

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
            case R.id.lo_list_recruitment:
                Intent itListRecruitment =  new Intent(DetailJobPendingActivity.this, ListUserRecruitmentActivity.class);
                startActivity(itListRecruitment);
        }
    }
}
