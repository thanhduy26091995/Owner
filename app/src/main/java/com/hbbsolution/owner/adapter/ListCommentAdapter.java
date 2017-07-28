package com.hbbsolution.owner.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.work_management.model.listcommentmaid.Doc;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by buivu on 15/05/2017.
 */

public class ListCommentAdapter extends RecyclerView.Adapter<ListCommentAdapter.ListCommentViewHolder> {

    public Activity activity;
    public List<Doc> comments;

    public ListCommentAdapter(Activity activity, List<Doc> comments) {
        this.activity = activity;
        this.comments = comments;
    }

    @Override
    public ListCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(activity, R.layout.item_comment, null);
        return new ListCommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListCommentViewHolder holder, int position) {

        Doc comment = comments.get(position);
        try {
            holder.txtCommentName.setText(comment.getFromId().getInfo().getName());
            holder.txtCommentContent.setText(comment.getContent());
            if (comment.getTask() == null) throw new IllegalArgumentException();
            else {
                holder.txtCommentType.setText(comment.getTask().getInfoTask().getTitle());
            }
        } catch (Exception e) {

        }

        holder.txtCommentTime.setText(getDatePostHistory(comment.getCreateAt()));
        holder.ratingBar.setRating(comment.getEvaluationPoint());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ListCommentViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgAvatar;
        public TextView txtCommentName, txtCommentTime, txtCommentType, txtCommentContent;
        public RatingBar ratingBar;

        public ListCommentViewHolder(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.img_comment_avatar);
            txtCommentName = (TextView) itemView.findViewById(R.id.txt_comment_name);
            txtCommentTime = (TextView) itemView.findViewById(R.id.txt_comment_time);
            txtCommentType = (TextView) itemView.findViewById(R.id.txt_comment_type);
            txtCommentContent = (TextView) itemView.findViewById(R.id.txt_comment_content);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating_comment);
        }
    }

    private String getDatePostHistory(String createDatePostHistory) {
        Date date = new DateTime(createDatePostHistory).toDate();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String mDateTimePostHistory = df.format(date);
        return mDateTimePostHistory;
    }
}
