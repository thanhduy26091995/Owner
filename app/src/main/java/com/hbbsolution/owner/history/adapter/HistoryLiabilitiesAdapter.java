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
import com.hbbsolution.owner.history.model.liabilities.LiabilitiesHistory;
import com.hbbsolution.owner.history.view.HistoryActivity;
import com.hbbsolution.owner.utils.WorkTimeValidate;
import com.hbbsolution.owner.work_management.view.payment.view.PaymentActivity;

import java.util.List;

/**
 * Created by Administrator on 26/05/2017.
 */

public class HistoryLiabilitiesAdapter extends RecyclerView.Adapter<HistoryLiabilitiesAdapter.RecyclerViewHolder> {
    private Context context;
    private List<LiabilitiesHistory> listData;
    private LiabilitiesHistory liabilitiesHistory;
    private boolean isHis;
    private long elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds;
    private String date;
    private String startTime, endTime;
    private int p;

    @Override
    public HistoryLiabilitiesAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_liabilities, parent, false);
        return new RecyclerViewHolder(view);
    }

    public HistoryLiabilitiesAdapter(Context context, List<LiabilitiesHistory> listData) {
        this.context = context;
        this.listData = listData;

    }

    @Override
    public void onBindViewHolder(HistoryLiabilitiesAdapter.RecyclerViewHolder holder, int position) {
        liabilitiesHistory = listData.get(position);
        holder.tvJob.setText(liabilitiesHistory.getTask().getInfo().getTitle());
        if (!liabilitiesHistory.getTask().getInfo().getWork().getImage().equals("")) {
            Glide.with(context).load(liabilitiesHistory.getTask().getInfo().getWork().getImage())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .centerCrop()
                    .dontAnimate()
                    .into(holder.imgType);
        }
        WorkTimeValidate.setWorkTimeRegister(context, holder.tvTime, liabilitiesHistory.getTask().getInfo().getTime().getEndAt());
        holder.tvDate.setText(WorkTimeValidate.getDatePostHistory(liabilitiesHistory.getTask().getInfo().getTime().getEndAt()));
        holder.tvDeitalTime.setText(WorkTimeValidate.getTimeWorkLanguage(context, liabilitiesHistory.getTask().getInfo().getTime().getStartAt()) + " - " + WorkTimeValidate.getTimeWorkLanguage(context, liabilitiesHistory.getTask().getInfo().getTime().getEndAt()));
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
            Intent intent = new Intent(context, PaymentActivity.class);
            intent.putExtra("liability", listData.get(getAdapterPosition()));
            HistoryActivity.changeUnpaid = true;
//            ActivityOptionsCompat historyOption =
//                    ActivityOptionsCompat
//                            .makeSceneTransitionAnimation((Activity)context, (View)v.findViewById(R.id.img_job_type), "icJobType");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                context.startActivity(intent, historyOption.toBundle());
//            }
//            else {
            context.startActivity(intent);
//            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

}

