package com.hbbsolution.owner.work_management.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tantr on 5/14/2017.
 */

public class DetailJobDoingActivity extends AppCompatActivity{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.manager_doing_title_toothbar)
    TextView txtManager_doing_title_toothbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_doing);

        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtManager_doing_title_toothbar.setText("Đang làm");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
