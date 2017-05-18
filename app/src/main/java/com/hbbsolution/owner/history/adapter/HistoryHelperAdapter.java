package com.hbbsolution.owner.history.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hbbsolution.owner.R;

/**
 * Created by Administrator on 16/05/2017.
 */

public class HistoryHelperAdapter extends RecyclerView.Adapter<HistoryHelperAdapter.RecyclerViewHolder>{
    private Context context;
    @Override
    public HistoryHelperAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_helpler, parent, false);
        return new RecyclerViewHolder(view);
    }

    public HistoryHelperAdapter(Context context){
        this.context=context;
    }
    @Override
    public void onBindViewHolder(HistoryHelperAdapter.RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }
    class RecyclerViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener{
        private TextView tvName,tvDate;
        private RatingBar ratingBar;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView)itemView.findViewById(R.id.txt_history_name);
            tvDate = (TextView)itemView.findViewById(R.id.txt_history_date);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(context.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(context.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(context.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
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
