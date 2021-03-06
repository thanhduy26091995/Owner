package com.hbbsolution.owner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.utils.WorkTimeValidate;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;

import java.util.List;

/**
 * Created by tantr on 6/15/2017.
 */

public class JobPostAdapter extends RecyclerView.Adapter<JobPostAdapter.JobPostViewHolder> {

    private Context context;
    private List<Datum> datumList;
    private Callback callback;

    public JobPostAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public JobPostAdapter.JobPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = View.inflate(context, R.layout.item_job_post, null);
        return new JobPostAdapter.JobPostViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(JobPostAdapter.JobPostViewHolder holder, int position) {
        final Datum mDatum = datumList.get(position);
        try {
            holder.txtTitleJobPost.setText(mDatum.getInfo().getTitle());
            holder.txtDatePostHistory.setText(WorkTimeValidate.getDatePostHistory(mDatum.getInfo().getTime().getStartAt()));
            WorkTimeValidate.setWorkTimeRegister(context, holder.txtTimePostHistory, mDatum.getHistory().getUpdateAt());

            String mStartTime = WorkTimeValidate.getTimeWorkLanguage(context, mDatum.getInfo().getTime().getStartAt());
            String mEndTime = WorkTimeValidate.getTimeWorkLanguage(context, mDatum.getInfo().getTime().getEndAt());
            holder.txtTimeDoingPost.setText(mStartTime + " - " + mEndTime);

            if (!WorkTimeValidate.compareDays(mDatum.getInfo().getTime().getEndAt())) {
                holder.txtExpired.setVisibility(View.VISIBLE);
                holder.lo_background.setVisibility(View.VISIBLE);
                holder.txtNumber_request_detail_post.setVisibility(View.GONE);
                holder.txtRequestDirect.setVisibility(View.GONE);
                holder.txtType.setText(context.getResources().getString(R.string.qua_han_ung_tuyen));
            } else {
                holder.txtExpired.setVisibility(View.GONE);
                holder.lo_background.setVisibility(View.GONE);
                holder.txtType.setText(context.getResources().getString(R.string.jobs_for_applications));
//            holder.txtNumber_request_detail_post.setVisibility(View.VISIBLE);
                if (mDatum.getProcess().getId().equals("000000000000000000000006")) {
                    holder.txtRequestDirect.setVisibility(View.VISIBLE);
                    holder.txtNumber_request_detail_post.setVisibility(View.GONE);
                } else {
                    holder.txtRequestDirect.setVisibility(View.GONE);
                    if (mDatum.getStakeholders().getRequest().size() == 0) {
                        holder.txtNumber_request_detail_post.setVisibility(View.GONE);
                    } else {
                        holder.txtNumber_request_detail_post.setVisibility(View.VISIBLE);
                        holder.txtNumber_request_detail_post.setText(String.valueOf(mDatum.getStakeholders().getRequest().size()));
                    }
                }
            }

            if (mDatum != null && mDatum.getInfo() != null && mDatum.getInfo().getWork() != null && mDatum.getInfo().getWork().getImage() != null) {
                Glide.with(context).load(mDatum.getInfo().getWork().getImage())
                        .placeholder(R.drawable.no_image)
                        .thumbnail(0.5f)
                        .dontAnimate()
                        .error(R.drawable.no_image)
                        .into(holder.imgTypeJobPost);
            }

        } catch (Exception e) {
            Log.d("ERROR_LOAD", mDatum.getId());
            Log.d("ERROR_LOAD", e.getMessage());
        }
        holder.linearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onItemClickDetail(mDatum);
                }
            }
        });

//        holder.linearDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (callback != null){
//                    callback.onItemClickDelete(mDatum);
//                }
//            }
//        });
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                if (callback != null) {
//                    callback.onItemLongClick(mDatum);
//                }
//                return true;
//            }
//        });
//
//        holder.linearDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("CLICK", "TRUE");
//            }
//        });
//
//        holder.linearData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("CLICK_DATA", "TRUE");
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class JobPostViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTimePostHistory, txtDatePostHistory, txtTimeDoingPost,
                txtTitleJobPost, txtNumber_request_detail_post, txtExpired, txtType, txtRequestDirect;
        private ImageView imgTypeJobPost;
        private LinearLayout lo_background;
        private SwipeLayout swipeLayout;
        private LinearLayout linearDelete, linearData;

        public JobPostViewHolder(View itemView) {
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
            txtRequestDirect = (TextView) itemView.findViewById(R.id.txtExpired_request_direct_detail);
//            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe_post);
//            linearDelete = (LinearLayout) itemView.findViewById(R.id.bottom_delete);
            linearData = (LinearLayout) itemView.findViewById(R.id.linear_data);

//            //config swipe
//            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
//            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, itemView.findViewById(R.id.bottom_delete));
//            swipeLayout.setRightSwipeEnabled(true);
//            swipeLayout.setLeftSwipeEnabled(false);
        }
    }

    public interface Callback {
        void onItemClickDetail(Datum mDatum);

        void onItemClickDelete(Datum mDatum);

        void onItemLongClick(Datum mDatum);
    }
}
