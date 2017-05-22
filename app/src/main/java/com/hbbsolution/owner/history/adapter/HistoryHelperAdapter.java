package com.hbbsolution.owner.history.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.work_management.model.maid.Maid;
import com.hbbsolution.owner.work_management.model.maid.Request;

import java.util.List;

/**
 * Created by Administrator on 16/05/2017.
 */

public class HistoryHelperAdapter extends RecyclerView.Adapter<HistoryHelperAdapter.RecyclerViewHolder> {
    private Context context;
    private List<Request> maidList;
    private boolean isHis;


    @Override
    public HistoryHelperAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recy_list_recruitment, parent, false);
        return new RecyclerViewHolder(view);
    }

    public HistoryHelperAdapter(Context context, List<Request> maidList, boolean isHis) {
        this.context = context;
        this.maidList = maidList;
        this.isHis = isHis;
    }

    @Override
    public void onBindViewHolder(HistoryHelperAdapter.RecyclerViewHolder holder, int position) {
        final Maid mMaid = maidList.get(position).getMaid();
        holder.tvName.setText(mMaid.getInfoMaid().getUsername());
        holder.ratingBar.setRating(4);
        if(isHis){
            holder.imgIcCheck.setVisibility(View.GONE);
            holder.tvDate.setText("12/03/2016");
            holder.tvType.setText("Danh sách công việc");

        }else {
            holder.imgIcCheck.setVisibility(View.VISIBLE);
            holder.tvDate.setText(String.valueOf(mMaid.getWorkInfo().getPrice()));
            holder.tvType.setText("Chọn người giúp việc");
        }

        holder.lo_info_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itInfoUser = new Intent(context, MaidProfileActivity.class);
                itInfoUser.putExtra("maid",mMaid);
                context.startActivity(itInfoUser);
            }
        });

        holder.lo_ChosenMaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Đã Chọn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return maidList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        private TextView tvName, tvDate, tvType, imgIcCheck;
        private RatingBar ratingBar;
        private RelativeLayout lo_info_user, lo_ChosenMaid;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.txtName_recruitment);
            tvDate = (TextView) itemView.findViewById(R.id.txtPrice_and_Date);
            tvType = (TextView) itemView.findViewById(R.id.job_doing_txtDone);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating_comment);
            imgIcCheck = (TextView) itemView.findViewById(R.id.img_ic_check);
            lo_info_user = (RelativeLayout) itemView.findViewById(R.id.lo_info_user);
            lo_ChosenMaid = (RelativeLayout) itemView.findViewById(R.id.lo_ChosenMaid);

//            tvName = (TextView) itemView.findViewById(R.id.txt_history_name);
//            tvDate = (TextView) itemView.findViewById(R.id.txt_history_date);
//            tvType = (TextView) itemView.findViewById(R.id.txt_history_list_work);
//            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
//            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
//            stars.getDrawable(2).setColorFilter(context.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(0).setColorFilter(context.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(1).setColorFilter(context.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void setBackgroundRatingBar() {

    }
}
