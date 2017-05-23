package com.hbbsolution.owner.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.view.HistoryActivity;
import com.hbbsolution.owner.maid_near_by.view.MaidNearByActivity;
import com.hbbsolution.owner.more.viet_pham.MoreActivity;
import com.hbbsolution.owner.work_management.view.workmanager.WorkManagementActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.home_title_toothbar)
    TextView txtHome_title_toothbar;
    @BindView(R.id.lo_maid_around)
    RelativeLayout mLayout_MaidAround;
    @BindView(R.id.lo_your_tasks)
    RelativeLayout mLayout_YourTasks;
    @BindView(R.id.lo_history)
    RelativeLayout mLayout_History;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // setup toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtHome_title_toothbar.setText(getResources().getString(R.string.home_home));

        // on click
        mLayout_MaidAround.setOnClickListener(this);
        mLayout_YourTasks.setOnClickListener(this);
        mLayout_History.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actio_more) {
            transActivity(MoreActivity.class);
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
        int id = view.getId();
        switch (id) {
            case R.id.lo_maid_around:
                transActivity(MaidNearByActivity.class);
                //ShowToast("Maid Around");
                break;
            case R.id.lo_your_tasks:
                transActivity(WorkManagementActivity.class);
                break;
            case R.id.lo_history:
                transActivity(HistoryActivity.class);
                break;
        }
    }

    // Transition Activity
    private void transActivity(Class activity) {
        Intent itTransActivity = new Intent(HomeActivity.this, activity);
        startActivity(itTransActivity);
    }

    private void ShowToast(String msg) {
        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
