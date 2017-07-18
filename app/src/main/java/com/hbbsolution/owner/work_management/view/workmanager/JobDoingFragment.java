package com.hbbsolution.owner.work_management.view.workmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.JobPendingAdapter;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.workmanager.WorkManagerResponse;
import com.hbbsolution.owner.work_management.model.workmanagerpending.DatumPending;
import com.hbbsolution.owner.work_management.model.workmanagerpending.JobPendingResponse;
import com.hbbsolution.owner.work_management.presenter.WorkManagerPresenter;
import com.hbbsolution.owner.work_management.view.detail.DetailJobDoingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tantr on 5/9/2017.
 */

public class JobDoingFragment extends Fragment implements WorkManagerView{

    private String idProcess = "000000000000000000000004";

    private View rootView;
    private LinearLayout lnNoData;
    private WorkManagerPresenter mWorkManagerPresenter;
    private List<DatumPending> mJobList = new ArrayList<>();
    private JobPendingAdapter mJobPendingAdapter;
    private RecyclerView mRecycler;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayoutSale;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_job_doing, container, false);

            mRecycler = (RecyclerView) rootView.findViewById(R.id.recycler_doing);
            progressBar = (ProgressBar) rootView.findViewById(R.id.progressDoing);
            lnNoData = (LinearLayout) rootView.findViewById(R.id.lnNoData);
            mSwipeRefreshLayoutSale = (SwipeRefreshLayout) rootView.findViewById(R.id.swip_refresh_job_doing);

            mWorkManagerPresenter = new WorkManagerPresenter(this);
            progressBar.setVisibility(View.VISIBLE);
            mWorkManagerPresenter.getInfoJobPending(idProcess);

            mSwipeRefreshLayoutSale.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            mWorkManagerPresenter.getInfoJobPending(idProcess);
                            mSwipeRefreshLayoutSale.setRefreshing(false);
                        }
                    }, 1500);
                }
            });

        }else {
            ViewGroup parent = (ViewGroup) container.getParent();
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void getInfoJob(WorkManagerResponse mExample) {

    }

    @Override
    public void getInfoJobPending(JobPendingResponse mJobPendingResponse) {
        progressBar.setVisibility(View.GONE);
        mJobList = mJobPendingResponse.getData();
        if(mJobList.size() > 0) {
            lnNoData.setVisibility(View.GONE);
            mRecycler.setVisibility(View.VISIBLE);
            mRecycler.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
            mJobPendingAdapter = new JobPendingAdapter(getActivity(), mJobList, 3);
            mRecycler.setLayoutManager(linearLayoutManager);
            mRecycler.setAdapter(mJobPendingAdapter);

            mJobPendingAdapter.setCallback(new JobPendingAdapter.Callback() {
                @Override
                public void onItemClick(DatumPending mDatum) {
                    Intent itDetailJobPending = new Intent(getActivity(), DetailJobDoingActivity.class);
                    itDetailJobPending.putExtra("mDatum", mDatum);
                    startActivity(itDetailJobPending);
                }

                @Override
                public void onItemLongClick(DatumPending mDatum) {

                }
            });
        }else {
            lnNoData.setVisibility(View.VISIBLE);
            mRecycler.setVisibility(View.GONE);
        }
    }

    @Override
    public void displayNotifyJobPost(boolean isJobPost) {

    }

    @Override
    public void getError() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void responseCheckToken() {

    }

    @Override
    public void errorConnectService() {

    }

    @Override
    public void connectServerFail() {
        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), getActivity());
    }
}
