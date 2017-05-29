package com.hbbsolution.owner.history.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.history.CommentHistoryView;
import com.hbbsolution.owner.history.model.commenthistory.CommentHistoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 29/05/2017.
 */

public class CommentHistoryPresenter {
    private CommentHistoryView commentHistoryView;
    private ApiInterface apiService;

    public CommentHistoryPresenter(CommentHistoryView commentHistoryView) {
        this.commentHistoryView = commentHistoryView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void checkComment(String idTask) {
        Call<CommentHistoryResponse> call = apiService.checkComment(idTask);
        call.enqueue(new Callback<CommentHistoryResponse>() {
            @Override
            public void onResponse(Call<CommentHistoryResponse> call, Response<CommentHistoryResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        CommentHistoryResponse commentHistoryResponse = response.body();
                        if(commentHistoryResponse.getStatus()) {
                            commentHistoryView.success(commentHistoryResponse.getData().getContent());
                        }
                        else
                        {
                            commentHistoryView.fail();
                        }
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentHistoryResponse> call, Throwable t) {
                commentHistoryView.fail();
            }
        });
    }
}
