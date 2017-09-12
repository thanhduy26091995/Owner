package com.hbbsolution.owner.work_management.view.detail.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.work_management.model.maid.Request;

import java.util.List;

/**
 * Created by buivu on 11/09/2017.
 */

public class ApplicantListAdapter extends RecyclerView.Adapter<ApplicantListAdapter.ApplicantViewHolder> {

    private List<Request> requestList;
    private Activity activity;
    private int mSelected = -1;
    private OnItemClickApplicant listener;

    public ApplicantListAdapter(List<Request> requestList, Activity activity) {
        this.requestList = requestList;
        this.activity = activity;
    }

    public void OnItemClick(OnItemClickApplicant listener) {
        this.listener = listener;
    }

    @Override
    public ApplicantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_applicant_list, parent, false);
        return new ApplicantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ApplicantViewHolder holder, final int position) {
        if (mSelected == position) {
            holder.itemView.setBackground(activity.getResources().getDrawable(R.drawable.background_button_blue));
            holder.mTextViewName.setTextColor(activity.getResources().getColor(R.color.colorWhite));
        } else {
            holder.itemView.setBackground(activity.getResources().getDrawable(R.drawable.background_button_white));
            holder.mTextViewName.setTextColor(activity.getResources().getColor(R.color.colorBlack));
        }
        final Maid mMaid = requestList.get(position).getMaid();
        try {
            holder.mTextViewName.setText(mMaid.getInfo().getName());
            Glide.with(activity).load(mMaid.getInfo().getImage())
                    .placeholder(R.drawable.avatar)
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .error(R.drawable.avatar)
                    .into(holder.mImageViewAvatar);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(mMaid, true);
                    mSelected = position;
                    notifyDataSetChanged();


                }
            });

            holder.mImageViewAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickAvatar(mMaid);
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ApplicantViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageViewAvatar;
        private TextView mTextViewName;

        public ApplicantViewHolder(View itemView) {
            super(itemView);

            mImageViewAvatar = (ImageView) itemView.findViewById(R.id.imageView_applicant_avatar);
            mTextViewName = (TextView) itemView.findViewById(R.id.textView_applicant_name);
        }
    }

    public interface OnItemClickApplicant {
        void onItemClickAvatar(Maid maid);

        void onItemClick(Maid maid, Boolean result);
    }
}
