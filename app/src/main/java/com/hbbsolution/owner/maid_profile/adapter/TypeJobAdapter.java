package com.hbbsolution.owner.maid_profile.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.ManageJobAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tantr on 6/2/2017.
 */

public class TypeJobAdapter extends RecyclerView.Adapter<TypeJobAdapter.TypeJobViewHolder> {

    private Context context;
    private List<String> stringList;

    public TypeJobAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
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
        if(stringList.size() > 3){

            int devicewidth = displaymetrics.widthPixels / 3;
            int percent = devicewidth / 10;
            int deviceheight = displaymetrics.heightPixels / 10;
            holder.imageView.getLayoutParams().width = devicewidth - percent ;
            holder.imageView.getLayoutParams().height = deviceheight;

        } else if(stringList.size() <= 3) {

            int devicewidth = displaymetrics.widthPixels / 3;
            int deviceheight = displaymetrics.heightPixels / 10;
            holder.imageView.getLayoutParams().width = devicewidth  ;
            holder.imageView.getLayoutParams().height = deviceheight;
        }

        String urlImage = stringList.get(position);
        holder.urlImg = urlImage;

        Picasso.with(context)
                .load(urlImage)
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class TypeJobViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        String urlImg;

        public TypeJobViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.img_TypeJob);

        }
    }
}
