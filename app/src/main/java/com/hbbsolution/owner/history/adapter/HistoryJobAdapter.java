package com.hbbsolution.owner.history.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.model.workhistory.WorkHistory;
import com.hbbsolution.owner.history.view.DetailWorkHistoryActivity;
import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by Administrator on 16/05/2017.
 */

public class HistoryJobAdapter extends RecyclerView.Adapter<HistoryJobAdapter.RecyclerViewHolder> {
    private Context context;
    private List<WorkHistory> listData;
    private long elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds;
    private String date;
    private String startTime, endTime;
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
        holder.tvJob.setText(listData.get(position).getInfo().getTitle());
        if(!listData.get(position).getInfo().getWork().getImage().equals("")) {
            Picasso.with(context).load(listData.get(position).getInfo().getWork().getImage())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(holder.imgType);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat time = new SimpleDateFormat("H:mm a", Locale.US);
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.US);
        // OVERRIDE SOME symbols WHILE RETAINING OTHERS
        symbols.setAmPmStrings(new String[] { "am", "pm" });
        time.setDateFormatSymbols(symbols);
        try {
            Date endDate = simpleDateFormat.parse(listData.get(position).getInfo().getTime().getEndAt());
            Date nowDate = new Date();
            Date startDate = simpleDateFormat.parse(listData.get(position).getInfo().getTime().getStartAt());
            date = dates.format(endDate);
            startTime = time.format(startDate);
            endTime = time.format(endDate);
            printDifference(endDate, nowDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (elapsedDays != 0) {
            holder.tvTime.setText(String.valueOf(elapsedDays) + " "+ context.getResources().getString(R.string.before,context.getResources().getQuantityString(R.plurals.day,(int)elapsedDays)));
        } else if (elapsedHours != 0) {
            holder.tvTime.setText(String.valueOf(elapsedHours) + " "+ context.getResources().getString(R.string.before,context.getResources().getQuantityString(R.plurals.hour,(int)elapsedHours)));
        } else if (elapsedMinutes != 0) {
            holder.tvTime.setText(String.valueOf(elapsedMinutes) + " "+ context.getResources().getString(R.string.before,context.getResources().getQuantityString(R.plurals.minute,(int)elapsedMinutes)));
        } else if (elapsedSeconds != 0) {
            holder.tvTime.setText(String.valueOf(elapsedSeconds) + " "+ context.getResources().getString(R.string.before,context.getResources().getQuantityString(R.plurals.second,(int)elapsedSeconds)));
        }
        holder.tvDate.setText(date);
        holder.tvDeitalTime.setText(startTime.replace(":","h") + " - "+endTime.replace(":","h"));
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
            intent.putExtra("work",listData.get(getAdapterPosition()));
            ActivityOptionsCompat historyOption =
                    ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity)context, (View)v.findViewById(R.id.img_job_type), "icJobType");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent, historyOption.toBundle());
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void printDifference(Date startDate, Date endDate) {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();


        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        elapsedSeconds = different / secondsInMilli;

    }
}
