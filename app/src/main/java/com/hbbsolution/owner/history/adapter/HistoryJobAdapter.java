package com.hbbsolution.owner.history.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.view.DetailWorkHistoryActivity;
import com.hbbsolution.owner.work_management.model.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 16/05/2017.
 */

public class HistoryJobAdapter extends RecyclerView.Adapter<HistoryJobAdapter.RecyclerViewHolder> {
    private Context context;
    private List<Datum> listData;
    @Override
    public HistoryJobAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job, parent, false);
        return new RecyclerViewHolder(view);
    }

    public HistoryJobAdapter(Context context, List<Datum> listData) {
        this.context = context;
        this.listData=listData;
    }

    @Override
    public void onBindViewHolder(HistoryJobAdapter.RecyclerViewHolder holder, int position) {
        holder.tvJob.setText(listData.get(position).getInfo().getTitle());
        Picasso.with(context).load(listData.get(position).getInfo().getWork().getImage())
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(holder.imgType);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        private TextView tvJob, tvTime, tvDate, tvDeitalTime;
        private ImageView imgType;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvJob = (TextView) itemView.findViewById(R.id.tvJob);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvDeitalTime = (TextView) itemView.findViewById(R.id.tvDetailTime);
            imgType = (ImageView)itemView.findViewById(R.id.img_job_type);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, DetailWorkHistoryActivity.class);
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
