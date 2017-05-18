package com.hbbsolution.owner.history.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.adapter.HistoryJobAdapter;


/**
 * Created by Administrator on 15/05/2017.
 */

public class HistoryJobFragment extends Fragment {
    private View v;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryJobAdapter historyJobAdapter;
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
        historyJobAdapter = new HistoryJobAdapter(getActivity());
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_history_job);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        historyJobAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(historyJobAdapter);

        return v;
    }
}
