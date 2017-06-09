package com.hbbsolution.owner.history.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.model.helper.MaidHistory;
import com.hbbsolution.owner.history.view.ListWorkActivity;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 16/05/2017.
 */

public class HistoryHelperAdapter extends RecyclerView.Adapter<HistoryHelperAdapter.RecyclerViewHolder> {
    private Context context;
    private List<MaidHistory> datumList;
    private boolean isHis;
    private String time;
    private Date date;
    private int p;
    @Override
    public HistoryHelperAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_helpler, parent, false);
        return new RecyclerViewHolder(view);
    }

    public HistoryHelperAdapter(Context context, List<MaidHistory> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @Override
    public void onBindViewHolder(HistoryHelperAdapter.RecyclerViewHolder holder, int position) {
        p=position;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
        holder.ratingBar.setRating(datumList.get(p).getId().getWorkInfo().getEvaluationPoint());
        try {
            date = simpleDateFormat.parse(datumList.get(p).getTimes().get(0));
            time = dates.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvName.setText(datumList.get(p).getId().getInfo().getName());
        holder.tvDate.setText(time);
        if(!datumList.get(p).getId().getInfo().getImage().equals("")) {
            Picasso.with(context).load(datumList.get(p).getId().getInfo().getImage())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(holder.imgMaid);
        }
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        private TextView tvName, tvDate, tvListWork;
        private RatingBar ratingBar;
        private CircleImageView imgMaid;
        private RelativeLayout lo_info_user, lo_ChosenMaid;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            lo_info_user = (RelativeLayout)itemView.findViewById(R.id.rela_info) ;
            tvName = (TextView) itemView.findViewById(R.id.txt_history_name);
            tvDate = (TextView) itemView.findViewById(R.id.txt_history_date);
            tvListWork = (TextView) itemView.findViewById(R.id.txt_history_list_work);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            imgMaid = (CircleImageView) itemView.findViewById(R.id.img_history_avatar);
            lo_info_user.setOnClickListener(this);
            tvListWork.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.rela_info:
                    intent = new Intent(context, MaidProfileActivity.class);
                    intent.putExtra("helper",datumList.get(getAdapterPosition()));
                    context.startActivity(intent);
                    break;
                case R.id.txt_history_list_work:
                    intent = new Intent(context, ListWorkActivity.class);
                    intent.putExtra("idMaid",datumList.get(getAdapterPosition()).getId().getId());
                    context.startActivity(intent);
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
