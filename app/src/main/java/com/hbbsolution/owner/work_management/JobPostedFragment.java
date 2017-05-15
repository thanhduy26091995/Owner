package com.hbbsolution.owner.work_management;

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
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.JobPostAdapter;
import com.hbbsolution.owner.work_management.model.Datum;
import com.hbbsolution.owner.work_management.model.WorkManagerResponse;
import com.hbbsolution.owner.work_management.presenter.WorkManagerPresenter;
import com.hbbsolution.owner.work_management.view.DetailJobPostActivity;
import com.hbbsolution.owner.work_management.view.WorkManagerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by tantr on 5/9/2017.
 */

public class JobPostedFragment extends Fragment implements WorkManagerView {

    private String token = "0eb910010d0252eb04296d7dc32e657b402290755a85367e8b7a806c7e8bd14b0902e541763a67ef41f2dfb3b9b4919869b609e34dbf6bace4525fa6731d1046";
    private String idProcess = "000000000000000000000001";

    private View rootView;
    private WorkManagerPresenter mWorkManagerPresenter;
    private List<Datum> mJobList = new ArrayList<>();
    private JobPostAdapter mJobPostAdapter;
    private RecyclerView mRecycler;
    private LinearLayout lo_item_post;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_job_posted, container, false);

            lo_item_post = (LinearLayout) rootView.findViewById(R.id.lo_item_post);

            lo_item_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), DetailJobPostActivity.class);
                    startActivity(intent);
                }
            });

//            mRecycler = (RecyclerView) rootView.findViewById(R.id.recycler_post);
//            mWorkManagerPresenter = new WorkManagerPresenter(this);
//            mWorkManagerPresenter.getInfoWorkList(token, idProcess);
        }else {
            ViewGroup parent = (ViewGroup) container.getParent();
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void getInfoJob(WorkManagerResponse mExample) {
        Toast.makeText(this.getContext(), "Hoàn thành", Toast.LENGTH_SHORT).show();
        mJobList = mExample.getData();
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        mJobPostAdapter = new JobPostAdapter(getActivity(), mJobList);
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(mJobPostAdapter);
    }

    @Override
    public void getError() {

    }
}
