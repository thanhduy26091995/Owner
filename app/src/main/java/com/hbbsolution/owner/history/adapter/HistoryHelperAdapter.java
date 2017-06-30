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

import com.bumptech.glide.Glide;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.model.helper.MaidHistory;
import com.hbbsolution.owner.history.view.ListWorkActivity;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.utils.WorkTimeValidate;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 16/05/2017.
 */

public class HistoryHelperAdapter extends RecyclerView.Adapter<HistoryHelperAdapter.RecyclerViewHolder> {
    private Context context;
    private List<MaidHistory> datumList;
    private MaidHistory maidHistory;
    private boolean isHis;
    private String time;
    private Date date;

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
        maidHistory = datumList.get(position);
        holder.ratingBar.setRating(maidHistory.getId().getWorkInfo().getEvaluationPoint());
        holder.tvName.setText(maidHistory.getId().getInfo().getName());
        holder.tvDate.setText(WorkTimeValidate.getDatePostHistory(maidHistory.getTimes().get(0)));
        if (!maidHistory.getId().getInfo().getImage().equals("")) {
            Glide.with(context).load(maidHistory.getId().getInfo().getImage())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .centerCrop()
                    .dontAnimate()
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
            lo_info_user = (RelativeLayout) itemView.findViewById(R.id.rela_info);
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
            switch (v.getId()) {
                case R.id.rela_info:
                    intent = new Intent(context, MaidProfileActivity.class);
                    intent.putExtra("helper", datumList.get(getAdapterPosition()));
//                    ActivityOptionsCompat historyOption =
//                            ActivityOptionsCompat
//                                    .makeSceneTransitionAnimation((Activity)context, (View)v.findViewById(R.id.img_history_avatar), "icAvatar");
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                        context.startActivity(intent, historyOption.toBundle());
//                    }
//                    else {
                    context.startActivity(intent);
//                    }
                    break;
                case R.id.txt_history_list_work:
                    intent = new Intent(context, ListWorkActivity.class);
                    intent.putExtra("idMaid", datumList.get(getAdapterPosition()).getId().getId());
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
