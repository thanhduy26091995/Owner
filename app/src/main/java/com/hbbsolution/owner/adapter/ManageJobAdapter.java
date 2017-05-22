package com.hbbsolution.owner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.work_management.model.maid.Maid;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;
import com.hbbsolution.owner.work_management.view.detail.DetailJobPostActivity;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by tantr on 5/10/2017.
 */

public class ManageJobAdapter extends RecyclerView.Adapter<ManageJobAdapter.JobPostViewHolder> {

    private Context context;
    private List<Datum> datumList;
    private Callback callback;
    private boolean isPost;

    public ManageJobAdapter(Context context, List<Datum> datumList, boolean isPost) {
        this.context = context;
        this.datumList = datumList;
        this.isPost = isPost;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public JobPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = View.inflate(context, R.layout.item_job_post, null);
        return new ManageJobAdapter.JobPostViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(JobPostViewHolder holder, int position) {
        final Datum mDatum = datumList.get(position);
        holder.txtTitleJobPost.setText(mDatum.getInfo().getTitle());
        holder.txtDatePostHistory.setText(getDatePostHistory(mDatum.getHistory().getUpdateAt()));
        getTimePostHistory(holder.txtTimePostHistory, mDatum.getHistory().getUpdateAt());
        getTimeDoingPost(holder.txtTimeDoingPost, mDatum.getInfo().getTime().getStartAt(), mDatum.getInfo().getTime().getEndAt());
        if (isPost){
           holder.txtNumber_request_detail_post.setVisibility(View.VISIBLE);
            if (mDatum.getStakeholders().getRequest().size() == 0){
                holder.txtNumber_request_detail_post.setVisibility(View.GONE);
            }
            holder.txtNumber_request_detail_post.setText(String.valueOf(mDatum.getStakeholders().getRequest().size()));
        }else {
            holder.txtNumber_request_detail_post.setVisibility(View.GONE);
        }

        holder.txtNumber_request_detail_post.setText(String.valueOf(mDatum.getStakeholders().getRequest().size()));
        Picasso.with(context).load(mDatum.getInfo().getWork().getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.imgTypeJobPost);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onItemClick( mDatum);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class JobPostViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTimePostHistory, txtDatePostHistory, txtTimeDoingPost, txtTitleJobPost, txtNumber_request_detail_post;
        private ImageView imgTypeJobPost;

        public JobPostViewHolder(View itemView) {
            super(itemView);
            txtTitleJobPost = (TextView) itemView.findViewById(R.id.txtTitleJobPost);
            txtTimePostHistory = (TextView) itemView.findViewById(R.id.txtTimePostHistory);
            txtDatePostHistory = (TextView) itemView.findViewById(R.id.txtDatePostHistory);
            txtTimeDoingPost = (TextView) itemView.findViewById(R.id.txtTimeDoingPost);
            txtNumber_request_detail_post = (TextView) itemView.findViewById(R.id.txtNumber_request_detail_post);
            imgTypeJobPost = (ImageView) itemView.findViewById(R.id.imgTypeJobPost);

        }
    }

    private void getTimeDoingPost(TextView txtTimeDoingPost, String mTimeStartWork, String mTimeEndWork) {
        Date date = new DateTime(mTimeStartWork).toDate();
        Date date1 = new DateTime(mTimeEndWork).toDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        String _TimeStartWork = simpleDateFormat.format(date);
        String _TimeEndWork = simpleDateFormat.format(date1);
        txtTimeDoingPost.setText(_TimeStartWork + " - " + _TimeEndWork);
    }

    private String getDatePostHistory(String createDatePostHistory) {
        Date date = new DateTime(createDatePostHistory).toDate();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String mDateTimePostHistory = df.format(date);
        return mDateTimePostHistory;
    }

    private void getTimePostHistory(TextView txtTimePostHistory, String createUpTimePost) {
        long time = System.currentTimeMillis();
        DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
        Date date = parser.parseDateTime(createUpTimePost).toDate();
        long millisecond = date.getTime();
        long timer = (time - millisecond);
        long timeHistory = (timer / 60000);
        String phuts, gios, ngays;
        if (timeHistory < 60) {
            phuts = String.format("%d",
                    TimeUnit.MILLISECONDS.toMinutes(timer),
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timer)));
            txtTimePostHistory.setText(phuts + " phút trước");
        } else if (60 < timeHistory && timeHistory < 1440) {
            gios = String.format("%d",
                    TimeUnit.MILLISECONDS.toHours(timer),
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timer)));
            txtTimePostHistory.setText(gios + " giờ trước");
        } else if (timeHistory > 1440) {
            ngays = String.format("%d",
                    TimeUnit.MILLISECONDS.toDays(timer),
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timer)));
            txtTimePostHistory.setText(ngays + " ngày trước");
        }
    }

    public interface Callback {
        void onItemClick(Datum mDatum);
    }
}
