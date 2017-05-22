package com.hbbsolution.owner.history.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.WorkHistoryView;
import com.hbbsolution.owner.history.adapter.HistoryJobAdapter;
import com.hbbsolution.owner.history.presenter.WorkHistoryPresenter;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;

import java.util.List;


/**
 * Created by Administrator on 15/05/2017.
 */

public class HistoryJobFragment extends Fragment implements WorkHistoryView {
    private View v;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryJobAdapter historyJobAdapter;
    private WorkHistoryPresenter workHistoryPresenter;
    public HistoryJobFragment() {
    }
    public static HistoryJobFragment newInstance() {
        HistoryJobFragment fragment = new HistoryJobFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_history_job, container, false);
        //Gán adapter các thứ
        workHistoryPresenter = new WorkHistoryPresenter(this);
        workHistoryPresenter.getInfoWorkHistory("0eb910010d0252eb04296d7dc32e657b402290755a85367e8b7a806c7e8bd14b0902e541763a67ef41f2dfb3b9b4919869b609e34dbf6bace4525fa6731d1046","000000000000000000000005");

        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_history_job);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        return v;
    }

    @Override
    public void getInfoWorkHistory(List<Datum> listWorkHistory) {

        historyJobAdapter = new HistoryJobAdapter(getActivity(),listWorkHistory);
        historyJobAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(historyJobAdapter);
    }

    @Override
    public void getError() {

    }
}
