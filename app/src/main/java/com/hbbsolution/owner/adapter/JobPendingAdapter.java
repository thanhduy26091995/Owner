package com.hbbsolution.owner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;
import com.hbbsolution.owner.work_management.model.workmanagerpending.DatumPending;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by tantr on 5/25/2017.
 */

public class JobPendingAdapter extends RecyclerView.Adapter<JobPendingAdapter.JobPendingViewHolder> {

    private Context context;
    private List<DatumPending> datumList;
    private Callback callback;
    private int tabJob;

    public JobPendingAdapter(Context context, List<DatumPending> datumList, int tabJob) {
        this.context = context;
        this.datumList = datumList;
        this.tabJob = tabJob;
     
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public JobPendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = View.inflate(context, R.layout.item_job_post, null);
        return new JobPendingAdapter.JobPendingViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(JobPendingViewHolder holder, int position) {
        final DatumPending mDatum = datumList.get(position);
        holder.txtTitleJobPost.setText(mDatum.getInfo().getTitle());
        holder.txtDatePostHistory.setText(getDatePostHistory(mDatum.getInfo().getTime().getStartAt()));
        getTimePostHistory(holder.txtTimePostHistory, mDatum.getHistory().getUpdateAt());
        getTimeDoingPost(holder.txtTimeDoingPost, mDatum.getInfo().getTime().getStartAt(), mDatum.getInfo().getTime().getEndAt());

        if(tabJob == 2){
            if (CompareDays(getDatePostHistory(mDatum.getInfo().getTime().getEndAt()))) {
                holder.txtType.setText(context.getResources().getString(R.string.qua_han_xac_nhan));
                holder.txtExpired.setVisibility(View.VISIBLE);
                holder.lo_background.setVisibility(View.VISIBLE);
                holder.txtNumber_request_detail_post.setVisibility(View.GONE);
            } else {
                holder.txtType.setText(context.getResources().getString(R.string.dang_cho_xac_nhan));
                holder.txtExpired.setVisibility(View.GONE);
                holder.lo_background.setVisibility(View.GONE);
            }
        }

        if(tabJob == 3){
            holder.txtType.setText(context.getResources().getString(R.string.running_work));
        }

        Picasso.with(context).load(mDatum.getInfo().getWork().getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.imgTypeJobPost);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onItemClick(mDatum);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (callback != null) {
                    callback.onItemLongClick(mDatum);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class JobPendingViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTimePostHistory, txtDatePostHistory, txtTimeDoingPost,
                txtTitleJobPost, txtNumber_request_detail_post, txtExpired, txtType;
        private ImageView imgTypeJobPost;
        private LinearLayout lo_background;

        public JobPendingViewHolder(View itemView) {
            super(itemView);

            txtTitleJobPost = (TextView) itemView.findViewById(R.id.txtTitleJobPost);
            txtTimePostHistory = (TextView) itemView.findViewById(R.id.txtTimePostHistory);
            txtDatePostHistory = (TextView) itemView.findViewById(R.id.txtDatePostHistory);
            txtTimeDoingPost = (TextView) itemView.findViewById(R.id.txtTimeDoingPost);
            txtNumber_request_detail_post = (TextView) itemView.findViewById(R.id.txtNumber_request_detail_post);
            imgTypeJobPost = (ImageView) itemView.findViewById(R.id.imgTypeJobPost);
            txtExpired = (TextView) itemView.findViewById(R.id.txtExpired_request_detail_post);
            lo_background = (LinearLayout) itemView.findViewById(R.id.lo_background);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
        }
    }

    public interface Callback {
        void onItemClick(DatumPending mDatum);
        void onItemLongClick(DatumPending mDatum);
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

    private boolean CompareDays(String dateStartWork) {
        Date date1 = null;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date1 = sdf.parse(dateStartWork);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date1.after(date)) {
            return false;
        }
        return true;
    }
}
