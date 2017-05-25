package com.hbbsolution.owner.work_management.view.workmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.JobPendingAdapter;
import com.hbbsolution.owner.adapter.ManageJobAdapter;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;
import com.hbbsolution.owner.work_management.model.workmanager.WorkManagerResponse;
import com.hbbsolution.owner.work_management.model.workmanagerpending.DatumPending;
import com.hbbsolution.owner.work_management.model.workmanagerpending.JobPendingResponse;
import com.hbbsolution.owner.work_management.presenter.WorkManagerPresenter;
import com.hbbsolution.owner.work_management.view.detail.DetailJobDoingActivity;
import com.hbbsolution.owner.work_management.view.detail.DetailJobPendingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tantr on 5/9/2017.
 */

public class JobDoingFragment extends Fragment implements WorkManagerView{

    private String token = "0eb910010d0252eb04296d7dc32e657b402290755a85367e8b7a806c7e8bd14b0902e541763a67ef41f2dfb3b9b4919869b609e34dbf6bace4525fa6731d1046";
    private String idProcess = "000000000000000000000004";

    private View rootView;
    private WorkManagerPresenter mWorkManagerPresenter;
    private List<DatumPending> mJobList = new ArrayList<>();
    private JobPendingAdapter mJobPendingAdapter;
    private RecyclerView mRecycler;

    private LinearLayout lo_item_doing;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_job_doing, container, false);

            mRecycler = (RecyclerView) rootView.findViewById(R.id.recycler_doing);
            mWorkManagerPresenter = new WorkManagerPresenter(this);
            mWorkManagerPresenter.getInfoJobPending(token, idProcess);

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
        mJobList = mJobPendingResponse.getData();
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        mJobPendingAdapter = new JobPendingAdapter(getActivity(), mJobList, 3);
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(mJobPendingAdapter);

        mJobPendingAdapter.setCallback(new JobPendingAdapter.Callback() {
            @Override
            public void onItemClick(DatumPending mDatum) {
                Intent itDetailJobPending = new Intent(getActivity(), DetailJobPendingActivity.class);
                itDetailJobPending.putExtra("mDatum", mDatum);
                startActivity(itDetailJobPending);
            }
        });

    }

    @Override
    public void getError() {

    }
}
