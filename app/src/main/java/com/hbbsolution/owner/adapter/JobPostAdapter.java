package com.hbbsolution.owner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.work_management.model.Datum;

import java.util.List;

/**
 * Created by tantr on 5/10/2017.
 */

public class JobPostAdapter extends RecyclerView.Adapter<JobPostAdapter.JobPostViewHolder> {

    private Context context;
    private List<Datum> datumList;
    private int [] logo_job = {R.drawable.nauan, R.drawable.dondepnha};

    public  JobPostAdapter(Context context, List<Datum> datumList)
    {
        this.context = context;
        this.datumList = datumList;
    }

    @Override
    public JobPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = View.inflate(context, R.layout.item_recy_job_pending, null);
        return new JobPostAdapter.JobPostViewHolder(rootView);    }

    @Override
    public void onBindViewHolder(JobPostViewHolder holder, int position) {
        final Datum mDatum = datumList.get(position);
        holder.job_post_txtTitle_job.setText(mDatum.getInfo().getTitle());
        holder.job_post_txtType_job.setText(mDatum.getInfo().getWork().getName());
        holder.job_post_txt_content_job.setText(mDatum.getInfo().getDescription());
        holder.job_post_txtPrice.setText(String.valueOf(mDatum.getInfo().getPrice()));
        switch (mDatum.getInfo().getWork().getId()){
            case "590a8c37f892e6000446c99a":
                holder.job_post_img_type.setImageResource(logo_job[0]);
                break;
            case "0001":
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class JobPostViewHolder extends RecyclerView.ViewHolder {
        private TextView job_post_txtTime_Post, job_post_txtTitle_job, job_post_txtType_job, job_post_txt_content_job,
                job_post_txtPrice, job_post_txtTime_Day, job_post_txtTime_Doing, job_post_txtAdress;
        private ImageView job_post_img_type;

        public JobPostViewHolder(View itemView) {
            super(itemView);
            job_post_txtTime_Post = (TextView) itemView.findViewById(R.id.job_post_txtTime_Post);
            job_post_txtTitle_job = (TextView) itemView.findViewById(R.id.job_post_txtTitle_job);
            job_post_txtType_job =  (TextView) itemView.findViewById(R.id.job_post_txtType_job);
            job_post_txt_content_job = (TextView) itemView.findViewById(R.id.job_post_txt_content_job);
            job_post_txtPrice = (TextView) itemView.findViewById(R.id.job_post_txtPrice);
            job_post_txtTime_Day = (TextView) itemView.findViewById(R.id.job_post_txtTime_Day);
            job_post_txtTime_Doing = (TextView) itemView.findViewById(R.id.job_post_txtTime_Doing);
            job_post_txtAdress = (TextView) itemView.findViewById(R.id.job_post_txtAdress);
            job_post_img_type = (ImageView) itemView.findViewById(R.id.job_post_img_type);

        }
    }
}
