package com.hbbsolution.owner.maid_near_by_new_version.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.ImageLoader;
import com.hbbsolution.owner.model.Maid;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by buivu on 08/09/2017.
 */

public class MaidNearByNewAdapter extends RecyclerView.Adapter<MaidNearByNewAdapter.MaidNearByNewViewHolder> {

    private List<Maid> mMaidInfoList;
    private Activity mActivity;
    private OnItemClick listener;

    public MaidNearByNewAdapter(List<Maid> mMaidInfoList, Activity mActivity) {
        this.mMaidInfoList = mMaidInfoList;
        this.mActivity = mActivity;
    }

    public void setItemClick(OnItemClick listener) {
        this.listener = listener;
    }

    @Override
    public MaidNearByNewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maid_near_by_list, parent, false);
        return new MaidNearByNewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MaidNearByNewViewHolder holder, int position) {
        try {
            final Maid maid = mMaidInfoList.get(position);
            //load data
            ImageLoader.getInstance().loadImageAvatar(mActivity, maid.getInfo().getImage(), holder.mImageViewAvatar);
            holder.mTextViewName.setText(maid.getInfo().getName());
            holder.mTextViewPrice.setText(NumberFormat.getNumberInstance(Locale.GERMANY).format(maid.getWorkInfo().getPrice()) + " VND" + mActivity.getResources().getString(R.string.hour));
            if (maid.getDist().getCalculated() < 1000) {
                holder.mTextViewDistance.setText(String.format("%d m", Math.round(maid.getDist().getCalculated() * 100) / 100));
            } else {
                holder.mTextViewDistance.setText(String.format("%d km", (Math.round(maid.getDist().getCalculated() / 1000) * 100) / 100));
            }
            holder.mTextViewAge.setText(String.format("%s %d", mActivity.getResources().getString(R.string.maid_near_by_age), maid.getInfo().getAge()));
            holder.mRatingBar.setRating(maid.getWorkInfo().getEvaluationPoint());
            //event click
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClickDetail(maid);
                    }
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return mMaidInfoList.size();
    }

    public class MaidNearByNewViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageViewAvatar;
        private TextView mTextViewName, mTextViewPrice, mTextViewDistance, mTextViewAge;
        private RatingBar mRatingBar;

        public MaidNearByNewViewHolder(View itemView) {
            super(itemView);

            mImageViewAvatar = (ImageView) itemView.findViewById(R.id.imageView_avatar);
            mTextViewName = (TextView) itemView.findViewById(R.id.textView_maid_name);
            mTextViewPrice = (TextView) itemView.findViewById(R.id.textView_maid_price);
            mTextViewDistance = (TextView) itemView.findViewById(R.id.textView_maid_distance);
            mTextViewAge = (TextView) itemView.findViewById(R.id.textView_maid_birth);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }

    public interface OnItemClick {
        void onItemClickDetail(Maid maid);
    }
}
