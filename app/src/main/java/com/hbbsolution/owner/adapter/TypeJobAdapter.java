package com.hbbsolution.owner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.home.view.HomeActivity;
import com.hbbsolution.owner.model.TypeJob;
import com.hbbsolution.owner.work_management.view.quickpost.QuickPostActivity;

import java.util.List;

/**
 * Created by Administrator on 20/07/2017.
 */

public class TypeJobAdapter extends RecyclerView.Adapter<TypeJobAdapter.RecyclerViewHolder> {
    private Context context;
    private List<TypeJob> listData;
    private TypeJob typeJob;

    @Override
    public TypeJobAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_type_job, parent, false);
        return new RecyclerViewHolder(view);
    }

    public TypeJobAdapter(Context context, List<TypeJob> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public void onBindViewHolder(TypeJobAdapter.RecyclerViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        typeJob = listData.get(position);
        holder.tvTypeJobName.setText(typeJob.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuickPostActivity.class);
                intent.putExtra("quickPost", listData.get(position));
                context.startActivity(intent);
                ((HomeActivity)context).finish();
            }
        });

        holder.tvTypeJobName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuickPostActivity.class);
                intent.putExtra("quickPost", listData.get(position));
                context.startActivity(intent);
                ((HomeActivity)context).finish();
            }
        });
      }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        private TextView tvTypeJobName;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvTypeJobName = (TextView) itemView.findViewById(R.id.tv_TypeJobName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, QuickPostActivity.class);
            intent.putExtra("quickPost", listData.get(getAdapterPosition()));
//            ActivityOptionsCompat historyOption =
//                    ActivityOptionsCompat
//                            .makeSceneTransitionAnimation((Activity) context, (View) v.findViewById(R.id.img_job_type), "icJobType");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                context.startActivity(intent, historyOption.toBundle());
//            } else {
            context.startActivity(intent);
            ((HomeActivity)context).finish();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

}
