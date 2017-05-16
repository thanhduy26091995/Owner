package com.hbbsolution.owner.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.model.Comment;

import java.util.List;

/**
 * Created by buivu on 15/05/2017.
 */

public class ListCommentAdapter extends RecyclerView.Adapter<ListCommentAdapter.ListCommentViewHolder> {

    public Activity activity;
    public List<Comment> comments;

    public ListCommentAdapter(Activity activity, List<Comment> comments) {
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
        Comment comment = comments.get(position);
        holder.txtCommentName.setText("Nguyễn Văn A");
        holder.txtCommentContent.setText(comment.getType());
        holder.txtCommentType.setText(comment.getContent());
        holder.txtCommentTime.setText("15/05/2017");
        //  holder.ratingBar.setRating((int) comment.getRating());
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
        }
    }
}
