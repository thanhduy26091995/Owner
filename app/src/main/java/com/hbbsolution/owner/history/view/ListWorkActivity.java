package com.hbbsolution.owner.history.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.BaseActivity;
import com.hbbsolution.owner.history.ListWorkView;
import com.hbbsolution.owner.history.adapter.HistoryJobAdapter;
import com.hbbsolution.owner.history.model.workhistory.WorkHistory;
import com.hbbsolution.owner.history.presenter.ListWorkPresenter;
import com.hbbsolution.owner.utils.EndlessRecyclerViewScrollListener;
import com.hbbsolution.owner.utils.ShowAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListWorkActivity extends BaseActivity implements ListWorkView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private HistoryJobAdapter historyJobAdapter;
    private ListWorkPresenter mListWorkPresenter;
    private int currentPage;
    private EndlessRecyclerViewScrollListener scrollListener;
    private List<WorkHistory> mDocList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar progressBar;
    private LinearLayout lnNoData;
    private String idMaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_work);
        ButterKnife.bind(this);

        checkConnectionInterner();

        setToolbar();
        Bundle extras = getIntent().getExtras();

        progressBar = (ProgressBar) findViewById(R.id.progressPost);
        progressBar.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_history_job);
        lnNoData = (LinearLayout) findViewById(R.id.lnNoData);
        layoutManager = new LinearLayoutManager(ListWorkActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        view = findViewById(R.id.view);
        view.setVisibility(View.INVISIBLE);

        currentPage = 1;

        mListWorkPresenter = new ListWorkPresenter(this);
        if (extras != null) {
            idMaid = extras.getString("idMaid");
            mListWorkPresenter.getInfoListWorkHistory(idMaid, currentPage);

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentPage = 1;
                            mListWorkPresenter.getInfoListWorkHistory(idMaid, currentPage);
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }, 1500);
                }
            });
        }

    }

    @Override
    public void getInfoListWorkHistory(List<WorkHistory> listWorkHistory, final int pages) {
        mDocList.clear();
        mDocList = listWorkHistory;
        historyJobAdapter = new HistoryJobAdapter(ListWorkActivity.this, mDocList);
        recyclerView.setAdapter(historyJobAdapter);
        progressBar.setVisibility(View.GONE);
        if (listWorkHistory.size() > 0) {
            view.setVisibility(View.VISIBLE);
            lnNoData.setVisibility(View.GONE);
        } else {
            lnNoData.setVisibility(View.VISIBLE);
        }
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // presenter.getAllResort(response.getCurrentPage() + 1);
                //get variables for load more
                if (currentPage < pages) {
                    mListWorkPresenter.getMoreInfoListWorkHistory(idMaid, currentPage + 1);
                }
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void getMoreInfoListWorkHistory(List<WorkHistory> listWorkHistory) {
        mDocList.addAll(listWorkHistory);
        currentPage++;
        historyJobAdapter.notifyDataSetChanged();
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                historyJobAdapter.notifyItemRangeInserted(historyJobAdapter.getItemCount(), mDocList.size() - 1);
            }
        });
    }

    @Override
    public void getError() {

    }

    public void setToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
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

    @Override
    public void connectServerFail() {
        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), this);
    }
}
