package com.hbbsolution.owner.history.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.adapter.HistoryHelperAdapter;

/**
 * Created by Administrator on 15/05/2017.
 */

public class HistoryHelperFragment extends Fragment {
    private View v;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryHelperAdapter historyHelperAdapter;

    public static HistoryHelperFragment newInstance() {
        HistoryHelperFragment fragment = new HistoryHelperFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_history_helper, container, false);
        //Gán adapter các thứ
//        historyHelperAdapter = new HistoryHelperAdapter(getActivity());
//        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_history_helper);
//        layoutManager = new LinearLayoutManager(getActivity());
//        historyHelperAdapter.notifyDataSetChanged();
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(historyHelperAdapter);
        return v;
    }
}
