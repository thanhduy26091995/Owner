package com.hbbsolution.owner.history.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.view.DetailLiabilitiesHistory;
import com.hbbsolution.owner.work_management.model.maid.Request;

import java.util.List;

/**
 * Created by Administrator on 26/05/2017.
 */

public class HistoryLiabilitiesAdapter extends RecyclerView.Adapter<HistoryLiabilitiesAdapter.RecyclerViewHolder> {
    private Context context;
    private List<Request> maidList;
    private boolean isHis;


    @Override
    public HistoryLiabilitiesAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_liabilities, parent, false);
        return new RecyclerViewHolder(view);
    }

    public HistoryLiabilitiesAdapter(Context context) {
        this.context = context;

    }

    @Override
    public void onBindViewHolder(HistoryLiabilitiesAdapter.RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        private TextView tvName, tvDate, tvType, imgIcCheck;
        private RatingBar ratingBar;
        private RelativeLayout lo_info_user, lo_ChosenMaid;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.txt_history_name);
            tvDate = (TextView) itemView.findViewById(R.id.txt_history_date);
            tvType = (TextView) itemView.findViewById(R.id.txt_history_list_work);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, DetailLiabilitiesHistory.class);
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

}

