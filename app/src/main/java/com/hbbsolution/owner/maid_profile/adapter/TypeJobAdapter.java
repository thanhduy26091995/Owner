package com.hbbsolution.owner.maid_profile.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.ManageJobAdapter;
import com.hbbsolution.owner.model.Ability;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tantr on 6/2/2017.
 */

public class TypeJobAdapter extends RecyclerView.Adapter<TypeJobAdapter.TypeJobViewHolder> {

    private Context context;
    private List<Ability> abilityList;

    public TypeJobAdapter(Context context, List<Ability> abilityList) {
        this.context = context;
        this.abilityList = abilityList;
    }

    @Override
    public TypeJobAdapter.TypeJobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = View.inflate(context, R.layout.item_type_job, null);
        return new TypeJobAdapter.TypeJobViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TypeJobAdapter.TypeJobViewHolder holder, int position) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if(abilityList.size() > 3){

            int devicewidth = displaymetrics.widthPixels / 3;
            int percent = devicewidth / 10;
            int deviceheight = displaymetrics.heightPixels / 10;
            holder.imageView.getLayoutParams().width = devicewidth - percent ;
            holder.imageView.getLayoutParams().height = deviceheight;

        } else if(abilityList.size() <= 3) {

            int devicewidth = displaymetrics.widthPixels / 3;
            int deviceheight = displaymetrics.heightPixels / 10;
            holder.imageView.getLayoutParams().width = devicewidth  ;
            holder.imageView.getLayoutParams().height = deviceheight;
        }

        Ability ability = abilityList.get(position);
//        holder.urlImg = ability.getImage();
        holder.txtNameTypeJob.setText(ability.getName());
        Picasso.with(context)
                .load(ability.getImage())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return abilityList.size();
    }

    public class TypeJobViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtNameTypeJob;

        public TypeJobViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.img_TypeJob);
            txtNameTypeJob = (TextView) itemView.findViewById(R.id.txtNameTypeJob);
        }
    }
}
