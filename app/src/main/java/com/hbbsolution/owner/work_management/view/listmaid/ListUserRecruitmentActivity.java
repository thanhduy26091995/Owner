package com.hbbsolution.owner.work_management.view.listmaid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.ListUserRecruitmentAdapter;
import com.hbbsolution.owner.history.adapter.HistoryHelperAdapter;
import com.hbbsolution.owner.work_management.model.maid.ListMaidResponse;
import com.hbbsolution.owner.work_management.model.maid.Request;
import com.hbbsolution.owner.work_management.presenter.ListMaidPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tantr on 5/15/2017.
 */

public class ListUserRecruitmentActivity extends AppCompatActivity implements ListMaidView {

    private String token = "0eb910010d0252eb04296d7dc32e657b402290755a85367e8b7a806c7e8bd14b0902e541763a67ef41f2dfb3b9b4919869b609e34dbf6bace4525fa6731d1046";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recruitment_title_toothbar)
    TextView txtRecruitment_title_toothbar;

    private ListMaidPresenter mListMaidPresenter;
    private List<Request> mListMaid = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private ListUserRecruitmentAdapter listUserRecruitmentAdapter;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_recruitment);
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtRecruitment_title_toothbar.setText("Danh sách ứng tuyển");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListMaidPresenter = new ListMaidPresenter(this);
        String idTaskProcess = getIntent().getStringExtra("idTaskProcess");

        if(!idTaskProcess.isEmpty()) {
            mListMaidPresenter.getInfoListMaid(token, idTaskProcess);
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

        int size = mListMaidRespose.getData().size();
        for(int i = 0; i < size; i++){
            mListMaid.addAll(mListMaidRespose.getData().get(i).getRequest());

        }
        listUserRecruitmentAdapter = new ListUserRecruitmentAdapter(this, mListMaid, false);
        mRecycler = (RecyclerView) findViewById(R.id.recycler_listmaid);
        layoutManager = new LinearLayoutManager(this);
        listUserRecruitmentAdapter.notifyDataSetChanged();
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(listUserRecruitmentAdapter);
    }

    @Override
    public void getError() {

    }
}
