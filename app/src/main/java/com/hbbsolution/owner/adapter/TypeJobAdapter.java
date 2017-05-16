package com.hbbsolution.owner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;

import java.util.List;

/**
 * Created by tantr on 5/15/2017.
 */

public class TypeJobAdapter extends RecyclerView.Adapter<TypeJobAdapter.TypeJobViewHolder>{

    private Context context;
    private List<String> listTypeJob;
    private Callback callback;

    public TypeJobAdapter(Context context, List<String> listTypeJob){
        this.context = context;
        this.listTypeJob = listTypeJob;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
    @Override
    public TypeJobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = View.inflate(context, R.layout.item_recy_job_type, null);
        return new TypeJobAdapter.TypeJobViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TypeJobViewHolder holder, final int position) {
        holder.txt_item_type_job.setText(listTypeJob.get(position));
        holder.lo_item_type_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onItemClick( position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTypeJob.size();
    }

    public class TypeJobViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout lo_item_type_job;
        private TextView txt_item_type_job;

        public TypeJobViewHolder(View itemView) {
            super(itemView);
            lo_item_type_job = (LinearLayout) itemView.findViewById(R.id.lo_item_type_job);
            txt_item_type_job = (TextView) itemView.findViewById(R.id.txt_item_type_job);
        }
    }

    public interface Callback {
        void onItemClick(int position);
    }

}
