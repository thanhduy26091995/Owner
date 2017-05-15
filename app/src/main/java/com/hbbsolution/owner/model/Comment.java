package com.hbbsolution.owner.model;

/**
 * Created by buivu on 15/05/2017.
 */

public class Comment {
    private String commentBy;
    private long timestamp;
    private float rating;
    private String type;
    private String content;

    public Comment() {
    }

    public Comment(String commentBy, long timestamp, float rating, String type, String content) {
        this.commentBy = commentBy;
        this.timestamp = timestamp;
        this.rating = rating;
        this.type = type;
        this.content = content;
    }

    public String getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
