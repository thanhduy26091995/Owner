package com.hbbsolution.owner.history.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.model.workhistory.WorkHistory;
import com.hbbsolution.owner.history.view.DetailWorkHistoryActivity;
import com.hbbsolution.owner.utils.WorkTimeValidate;

import java.util.List;


/**
 * Created by Administrator on 16/05/2017.
 */

public class HistoryJobAdapter extends RecyclerView.Adapter<HistoryJobAdapter.RecyclerViewHolder> {
    private Context context;
    private List<WorkHistory> listData;
    private WorkHistory workHistory;
    private long elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds;
    private String date;
    private String startTime, endTime;
    private int p;

    @Override
    public HistoryJobAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job, parent, false);
        return new RecyclerViewHolder(view);
    }

    public HistoryJobAdapter(Context context, List<WorkHistory> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public void onBindViewHolder(HistoryJobAdapter.RecyclerViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        workHistory = listData.get(position);
        holder.tvJob.setText(workHistory.getInfo().getTitle());
        if (!workHistory.getInfo().getWork().getImage().equals("")) {
            Glide.with(context).load(workHistory.getInfo().getWork().getImage())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .dontAnimate()
                    .into(holder.imgType);
        }
        WorkTimeValidate.setWorkTimeRegister(context, holder.tvTime, workHistory.getInfo().getTime().getEndAt());
        holder.tvDate.setText(WorkTimeValidate.getDatePostHistory(workHistory.getInfo().getTime().getEndAt()));
        holder.tvDeitalTime.setText(WorkTimeValidate.getTimeWorkLanguage(context,workHistory.getInfo().getTime().getStartAt()) + " - " + WorkTimeValidate.getTimeWorkLanguage(context,workHistory.getInfo().getTime().getEndAt()));
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
            imgType = (ImageView) itemView.findViewById(R.id.img_job_type);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, DetailWorkHistoryActivity.class);
            intent.putExtra("work", listData.get(getAdapterPosition()));
//            ActivityOptionsCompat historyOption =
//                    ActivityOptionsCompat
//                            .makeSceneTransitionAnimation((Activity) context, (View) v.findViewById(R.id.img_job_type), "icJobType");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                context.startActivity(intent, historyOption.toBundle());
//            } else {
            context.startActivity(intent);
//            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

}
