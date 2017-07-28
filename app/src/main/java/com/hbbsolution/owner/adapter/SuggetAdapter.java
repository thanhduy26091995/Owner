package com.hbbsolution.owner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.model.Suggest;

import java.util.List;

/**
 * Created by Administrator on 21/07/2017.
 */

public class SuggetAdapter extends RecyclerView.Adapter<SuggetAdapter.RecyclerViewHolder> {
    private Context context;
    private List<Suggest> listData;
    private Suggest suggest;
    private SuggetAdapter.Callback callback;

    @Override
    public SuggetAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_suggest, parent, false);
        return new RecyclerViewHolder(view);
    }

    public SuggetAdapter(Context context, List<Suggest> listData) {
        this.context = context;
        this.listData = listData;
    }

    public void setCallback(SuggetAdapter.Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onBindViewHolder(SuggetAdapter.RecyclerViewHolder holder, final int position) {
        suggest = listData.get(position);
        holder.tvSuggestName.setText(suggest.getName());
        holder.chbSuggest.setChecked(suggest.isChecked());
        holder.chbSuggest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (callback != null) {
                    if (isChecked) {
                        callback.onItemChecked(listData.get(position));
                    } else {
                        callback.onItemNotChecked(listData.get(position));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSuggestName;
        private CheckBox chbSuggest;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvSuggestName = (TextView) itemView.findViewById(R.id.tvSuggest);
            chbSuggest = (CheckBox) itemView.findViewById(R.id.chbSuggest);
        }
    }

    public interface Callback {
        void onItemChecked(Suggest suggest);

        void onItemNotChecked(Suggest suggest);
    }
}
