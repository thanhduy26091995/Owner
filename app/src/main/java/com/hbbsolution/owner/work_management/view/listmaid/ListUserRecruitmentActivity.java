package com.hbbsolution.owner.work_management.view.listmaid;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.ListUserRecruitmentAdapter;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.jobpost.JobPostResponse;
import com.hbbsolution.owner.work_management.model.maid.ListMaidResponse;
import com.hbbsolution.owner.work_management.model.maid.Request;
import com.hbbsolution.owner.work_management.presenter.ListMaidPresenter;
import com.hbbsolution.owner.work_management.view.detail.DetailJobPostActivity;
import com.hbbsolution.owner.work_management.view.jobpost.JobPostActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 5/15/2017.
 */

public class ListUserRecruitmentActivity extends AppCompatActivity implements ListMaidView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recruitment_title_toothbar)
    TextView txtRecruitment_title_toothbar;
    @BindView(R.id.progressListRecruitment)
    ProgressBar progressListRecruitment;

    private ListMaidPresenter mListMaidPresenter;
    private List<Request> mListMaid = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private ListUserRecruitmentAdapter listUserRecruitmentAdapter;
    private RecyclerView mRecycler;
    private String idTaskProcess;
    public static Activity mListUserRecruitmentActivity = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_recruitment);
        mListUserRecruitmentActivity = this;
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtRecruitment_title_toothbar.setText(getResources().getString(R.string.list_recruitment));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListMaidPresenter = new ListMaidPresenter(this);
        idTaskProcess = getIntent().getStringExtra("idTaskProcess");

        if (!idTaskProcess.isEmpty()) {
            progressListRecruitment.setVisibility(View.VISIBLE);
            mListMaidPresenter.getInfoListMaid(idTaskProcess);
        }
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
    public void getInfoListMaid(ListMaidResponse mListMaidRespose) {
        progressListRecruitment.setVisibility(View.GONE);
        int size = mListMaidRespose.getData().size();
        for (int i = 0; i < size; i++) {
            mListMaid.addAll(mListMaidRespose.getData().get(i).getRequest());

        }
        listUserRecruitmentAdapter = new ListUserRecruitmentAdapter(this, mListMaid, false);
        mRecycler = (RecyclerView) findViewById(R.id.recycler_listmaid);
        layoutManager = new LinearLayoutManager(this);
        listUserRecruitmentAdapter.notifyDataSetChanged();
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(listUserRecruitmentAdapter);
        listUserRecruitmentAdapter.setCallback(new ListUserRecruitmentAdapter.Callback() {
            @Override
            public void onItemClick(Maid mMaid) {
                Intent itInfoUser = new Intent(ListUserRecruitmentActivity.this, MaidProfileActivity.class);
                itInfoUser.putExtra("maid", mMaid);
                itInfoUser.putExtra("idTaskProcess", idTaskProcess);
                itInfoUser.putExtra("chosenMaidFromListRecruitment", true);
                startActivity(itInfoUser);
            }
        });

        listUserRecruitmentAdapter.setCallbackChosenMaid(new ListUserRecruitmentAdapter.CallbackChosenMaid() {
            @Override
            public void onItemClickChosenMaid(Maid mMaid) {
//                Toast.makeText(ListUserRecruitmentActivity.this, "Đã Chọn", Toast.LENGTH_SHORT).show();
                mListMaidPresenter.sentRequestChosenMaid(idTaskProcess, mMaid.getId());
            }
        });
    }

    @Override
    public void responseChosenMaid(JobPostResponse isResponseChosenMaid) {
        if (isResponseChosenMaid.getStatus()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getResources().getString(R.string.notification));
            alertDialog.setMessage(getResources().getString(R.string.chon_nguoi_giup_viec_thanh_cong));
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EventBus.getDefault().postSticky(true);
                    EventBus.getDefault().postSticky("1");
                    if (mListUserRecruitmentActivity != null) {
                        ListUserRecruitmentActivity.mListUserRecruitmentActivity.finish();
                        try {
                            if (DetailJobPostActivity.mDetailJobPostActivity != null) {
                                DetailJobPostActivity.mDetailJobPostActivity.finish();
                            }
                        } catch (Exception e) {

                        }
                    }
                }
            });

            alertDialog.show();
        } else {
            ShowAlertDialog.showAlert(getResources().getString(R.string.thatbai), ListUserRecruitmentActivity.this);
        }
    }

    @Override
    public void getError() {
        progressListRecruitment.setVisibility(View.GONE);
    }
}
