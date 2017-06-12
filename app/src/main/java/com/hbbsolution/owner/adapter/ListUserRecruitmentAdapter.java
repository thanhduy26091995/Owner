package com.hbbsolution.owner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.work_management.model.maid.Request;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;
import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by tantr on 5/22/2017.
 */

public class ListUserRecruitmentAdapter extends RecyclerView.Adapter<ListUserRecruitmentAdapter.ViewHolerListUserRecruitment>{

    private Context context;
    private List<Request> maidList;
    private boolean isHis;

    private Callback callback;
    private CallbackChosenMaid callbackChosenMaid;

    public ListUserRecruitmentAdapter(Context context, List<Request> maidList, boolean isHis) {
        this.context = context;
        this.maidList = maidList;
        this.isHis = isHis;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setCallbackChosenMaid(CallbackChosenMaid callbackChosenMaid) {
        this.callbackChosenMaid = callbackChosenMaid;
    }

    @Override
    public ListUserRecruitmentAdapter.ViewHolerListUserRecruitment onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recy_list_recruitment, parent, false);
        return new ListUserRecruitmentAdapter.ViewHolerListUserRecruitment(view);
    }

    @Override
    public void onBindViewHolder(ListUserRecruitmentAdapter.ViewHolerListUserRecruitment holder, int position) {
        final Maid mMaid = maidList.get(position).getMaid();
        holder.tvName.setText(mMaid.getInfo().getName());
        holder.ratingBar.setRating(mMaid.getWorkInfo().getEvaluationPoint());
        Picasso.with(context).load(mMaid.getInfo().getImage())
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(holder.img_avatar);

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
                if (callback != null) {
                    callback.onItemClick(mMaid);
                }
            }
        });

        holder.lo_ChosenMaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callbackChosenMaid != null) {
                    callbackChosenMaid.onItemClickChosenMaid(mMaid);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return maidList.size();
    }

    public class ViewHolerListUserRecruitment extends RecyclerView.ViewHolder {

        private TextView tvName, tvDate, tvType, imgIcCheck;
        private RatingBar ratingBar;
        private RelativeLayout lo_info_user, lo_ChosenMaid;
        private ImageView img_avatar;

        public ViewHolerListUserRecruitment(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.txtName_recruitment);
            tvDate = (TextView) itemView.findViewById(R.id.txtPrice_and_Date);
            tvType = (TextView) itemView.findViewById(R.id.job_doing_txtDone);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating_comment);
            imgIcCheck = (TextView) itemView.findViewById(R.id.img_ic_check);
            lo_info_user = (RelativeLayout) itemView.findViewById(R.id.lo_info_user);
            lo_ChosenMaid = (RelativeLayout) itemView.findViewById(R.id.lo_ChosenMaid);
            img_avatar = (ImageView) itemView.findViewById(R.id.img_avatar);
        }
    }

    public interface Callback {
        void onItemClick(Maid mMaid);
    }

    public interface CallbackChosenMaid {
        void onItemClickChosenMaid(Maid mMaid);
    }
}
