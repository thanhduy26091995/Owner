package com.hbbsolution.owner.work_management.view.workmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.JobPostAdapter;
import com.hbbsolution.owner.adapter.ManageJobAdapter;
import com.hbbsolution.owner.base.IconTextView;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;
import com.hbbsolution.owner.work_management.model.workmanager.WorkManagerResponse;
import com.hbbsolution.owner.work_management.model.workmanagerpending.JobPendingResponse;
import com.hbbsolution.owner.work_management.presenter.WorkManagerPresenter;
import com.hbbsolution.owner.work_management.view.detail.DetailJobPostActivity;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 5/9/2017.
 */

public class JobPostedFragment extends Fragment implements WorkManagerView {
    private String idProcess = "000000000000000000000001";

    private View rootView;
    private LinearLayout lnNoData;
    private WorkManagerPresenter mWorkManagerPresenter;
    private List<Datum> mJobList = new ArrayList<>();
    private JobPostAdapter mJobPostAdapter;
    private RecyclerView mRecycler;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayoutSale;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_job_posted, container, false);

            mRecycler = (RecyclerView) rootView.findViewById(R.id.recycler_post);
            progressBar = (ProgressBar) rootView.findViewById(R.id.progressPost);
            lnNoData = (LinearLayout) rootView.findViewById(R.id.lnNoData);
            mSwipeRefreshLayoutSale = (SwipeRefreshLayout) rootView.findViewById(R.id.swip_refresh_job_post);

            mWorkManagerPresenter = new WorkManagerPresenter(this);
            progressBar.setVisibility(View.VISIBLE);

            mWorkManagerPresenter.getInfoWorkList(idProcess);

            mSwipeRefreshLayoutSale.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            mWorkManagerPresenter.getInfoWorkList(idProcess);
                            mSwipeRefreshLayoutSale.setRefreshing(false);
                        }
                    }, 1500);
                }
            });
        } else {
            ViewGroup parent = (ViewGroup) container.getParent();
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void getInfoJob(WorkManagerResponse mExample) {
        progressBar.setVisibility(View.GONE);
        EventBus.getDefault().postSticky(mExample.getData().size());
        mJobList = mExample.getData();
        if (mJobList.size() > 0){
            lnNoData.setVisibility(View.GONE);
            mRecycler.setVisibility(View.VISIBLE);
            mRecycler.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
            mJobPostAdapter = new JobPostAdapter(getActivity(), mJobList);
            mRecycler.setLayoutManager(linearLayoutManager);
            mRecycler.setAdapter(mJobPostAdapter);

            mJobPostAdapter.setCallback(new ManageJobAdapter.Callback() {
                @Override
                public void onItemClick(Datum mDatum) {
                    Intent itDetailJobPost = new Intent(getActivity(), DetailJobPostActivity.class);
                    itDetailJobPost.putExtra("mDatum", mDatum);
                    startActivity(itDetailJobPost);
                }

                @Override
                public void onItemLongClick(final Datum mDatum) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getResources().getString(R.string.notification));
                    alertDialog.setMessage(getResources().getString(R.string.notification_del_job_post));
                    alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            progressBar.setVisibility(View.GONE);
                            mWorkManagerPresenter.deleteJob(mDatum.getId(), mDatum.getStakeholders().getOwner());
                        }
                    });
                    alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                }
            });
        }else {
            lnNoData.setVisibility(View.VISIBLE);
            mRecycler.setVisibility(View.GONE);
        }

    }

    @Override
    public void getInfoJobPending(JobPendingResponse mJobPendingResponse) {

    }

    @Override
    public void displayNotifyJobPost(boolean isJobPost) {
        progressBar.setVisibility(View.GONE);
        if(isJobPost){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getResources().getString(R.string.notification));
            alertDialog.setMessage(getResources().getString(R.string.notification__pass_del_job_post));
            alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getActivity().finish();
                    getActivity().overridePendingTransition(0, 0);
                    getActivity().startActivity(getActivity().getIntent());
                    getActivity().overridePendingTransition(0, 0);
                }
            });

            alertDialog.show();
        }else {
            Toast.makeText(getActivity(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void authenticationFailed() {

    }

    @Override
    public void getError() {
        progressBar.setVisibility(View.GONE);
    }
}
