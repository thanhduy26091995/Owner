package com.hbbsolution.owner.history.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.model.Datum;
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
    private List<Datum> listData;
    private long elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds;
    private String date;
    private String startTime, endTime;
    private int lastPosition = -1;
    private int previousPosition = 0;
    private String type,title,work,description,address,avatar,name,address_;
    private int price;
    private Datum datum;
    private Pair<View, String> pairJobType;
    @Override
    public HistoryJobAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job, parent, false);
        return new RecyclerViewHolder(view);
    }

    public HistoryJobAdapter(Context context, List<Datum> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public void onBindViewHolder(HistoryJobAdapter.RecyclerViewHolder holder, int position) {
        holder.tvJob.setText(listData.get(position).getInfo().getTitle());
        Picasso.with(context).load(listData.get(position).getInfo().getWork().getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.imgType);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat time = new SimpleDateFormat("H:mm a", Locale.US);
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
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
            holder.tvTime.setText(String.valueOf(elapsedDays) + " ngày" + " trước");
        } else if (elapsedHours != 0) {
            holder.tvTime.setText(String.valueOf(elapsedHours) + " giờ" + " trước");
        } else if (elapsedMinutes != 0) {
            holder.tvTime.setText(String.valueOf(elapsedMinutes) + " phút" + " trước");
        } else if (elapsedSeconds != 0) {
            holder.tvTime.setText(String.valueOf(elapsedSeconds) + " giây" + " trước");
        }
        holder.tvDate.setText(date);
        holder.tvDeitalTime.setText(startTime.replace(":","h") + " - "+endTime.replace(":","h"));
        setAnimation(holder.itemView, position);
        previousPosition=position;
        getData(position);

    }

    public void getData(int position)
    {
       datum=listData.get(position);
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

            intent.putExtra("app",listData.get(getAdapterPosition()));
            intent.putExtra("work",datum);
            ActivityOptionsCompat learningOption =
                    ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity)context, (View)v.findViewById(R.id.img_job_type), "icJobType");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent, learningOption.toBundle());
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

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
