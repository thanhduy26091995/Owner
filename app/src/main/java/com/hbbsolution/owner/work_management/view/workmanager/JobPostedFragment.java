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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.ManageJobAdapter;
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

    private String token = "0eb910010d0252eb04296d7dc32e657b402290755a85367e8b7a806c7e8bd14b0902e541763a67ef41f2dfb3b9b4919869b609e34dbf6bace4525fa6731d1046";
    private String idProcess = "000000000000000000000001";

    private View rootView;
    private WorkManagerPresenter mWorkManagerPresenter;
    private List<Datum> mJobList = new ArrayList<>();
    private ManageJobAdapter mJobPostAdapter;
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
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        mJobPostAdapter = new ManageJobAdapter(getActivity(), mJobList, 1);
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
                alertDialog.setTitle("Thông báo");
                alertDialog.setMessage("Bạn có muốn xóa công việc nay ? ");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressBar.setVisibility(View.GONE);
                        mWorkManagerPresenter.deleteJob(mDatum.getId(), mDatum.getStakeholders().getOwner());
                    }
                });
                alertDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
            }
        });
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
            alertDialog.setTitle("Thông báo");
            alertDialog.setMessage("Bạn đã xóa công việc này!");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
    public void getError() {
        progressBar.setVisibility(View.GONE);
    }
}
