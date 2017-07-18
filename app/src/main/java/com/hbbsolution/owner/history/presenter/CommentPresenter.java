package com.hbbsolution.owner.history.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.history.CommentView;
import com.hbbsolution.owner.history.model.workhistory.CommentResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 26/05/2017.
 */

public class CommentPresenter {
    private CommentView commentView;
    private ApiInterface apiService;

    public CommentPresenter(CommentView commentView) {
        this.commentView = commentView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void postComment(String idTask, String toId, String content, int rate) {
        Call<CommentResponse> call = apiService.postComment(idTask, toId, content, rate);
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        CommentResponse commentResponse = response.body();
                        commentView.commentSuccess(commentResponse.getMessage());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                commentView.connectServerFail();
            }
        });
    }
}
