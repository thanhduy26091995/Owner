package com.hbbsolution.owner.work_management.view.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.HelperListAdapter;
import com.hbbsolution.owner.model.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @BindView(R.id.lo_clear_job_pending)
    LinearLayout lo_clear_job_pending;

//    @BindView(R.id.eplHelperList)
//    ExpandableListView eplHelperList;

//    private HashMap<String, List<Helper>> mData;
//    private HelperListAdapter helperListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_pending);
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtManager_pending_title_toothbar.setText("Đã phân công");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lo_clear_job_pending.setOnClickListener(this);


//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int width = metrics.widthPixels;
//        eplHelperList.setIndicatorBounds(width - dp2px(50), width - dp2px(10));
//        setData();

    }
//    public void setData(){
//        final List<String> listHeader = new ArrayList<>();
//        listHeader.add("Danh sách ứng tuyển");
//        mData = new HashMap<>();
//        List<Helper> listShortVowel = new ArrayList<>();
//        listShortVowel.add(new Helper("dsad","Nguyễn Văn A","150.000 VND/ 1 giờ",3f));
//        listShortVowel.add(new Helper("dsadasd","Nguyễn Văn A","150.000 VND/ 1 giờ",3f));
//        listShortVowel.add(new Helper("dsadasd","Nguyễn Văn A","150.000 VND/ 1 giờ",3f));
//        listShortVowel.add(new Helper("dsadasd","Nguyễn Văn A","150.000 VND/ 1 giờ",3f));
//        listShortVowel.add(new Helper("dsadasd","Nguyễn Văn A","150.000 VND/ 1 giờ",3f));
//        mData.put(listHeader.get(0), listShortVowel);
//
//        helperListAdapter = new HelperListAdapter(this,listHeader, mData);
//        eplHelperList.setAdapter(helperListAdapter);
//    }
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
//            case R.id.lo_list_recruitment:
//                if(!eplHelperList.isGroupExpanded(0)) {
//                    eplHelperList.expandGroup(0);
//                }
//                else
//                {
//                    eplHelperList.collapseGroup(0);
//                }
//                break;
            case R.id.lo_clear_job_pending:
                Toast.makeText(DetailJobPendingActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                break;
        }
    }

//    public int dp2px(float dp) {
//        // Get the screen's density scale
//        final float density = getResources().getDisplayMetrics().density;
//        // Convert the dps to pixels, based on density scale
//        return (int) (dp * density + 0.5f);
//    }
}
