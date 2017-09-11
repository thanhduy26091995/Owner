package com.hbbsolution.owner.maid_near_by.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.model.Item;

import java.util.List;

/**
 * Created by buivu on 23/08/2017.
 */

public class ShowItemAdapter extends RecyclerView.Adapter<ShowItemAdapter.ShowItemViewHolder> {

    private Context mContext;
    private List<Item> mItems;
    private OnItemClickListener listener = null;


    public ShowItemAdapter(Context mContext, List<Item> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ShowItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent, false);
        return new ShowItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShowItemViewHolder holder, int position) {
        Item item = mItems.get(position);

        holder.textViewTitle.setText(item.getTitle());
        holder.setOnClick(listener, item, position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ShowItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private View vLine;

        public ShowItemViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.textview_title);
            vLine = (View) itemView.findViewById(R.id.line);
        }

        //set on click
        public void setOnClick(final OnItemClickListener onItemClickListener, final Item item, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClickListener(item, position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClickListener(Item item, int position);
    }
}
