package com.hbbsolution.owner.maid_profile.presenter;

import android.util.Log;


import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.maid_profile.view.MaidProfileView;
import com.hbbsolution.owner.work_management.model.listcommentmaid.CommentMaidResponse;
import com.hbbsolution.owner.work_management.model.maid.ListMaidResponse;
import com.hbbsolution.owner.work_management.view.listmaid.ListMaidView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 5/22/2017.
 */

public class MaidProfilePresenter {
    private MaidProfileView mMaidProfileView;
    private ApiInterface apiService;

    public MaidProfilePresenter(MaidProfileView mMaidProfileView){
        this.mMaidProfileView = mMaidProfileView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getInfoListMaid(String token, String process, int page){
        Call<CommentMaidResponse> call = apiService.getListCommentMaid(token, process, page);
        call.enqueue(new Callback<CommentMaidResponse>() {
            @Override
            public void onResponse(Call<CommentMaidResponse> call, Response<CommentMaidResponse> response) {
                if(response.isSuccessful()){

                    Log.e("successfuly", "successful");
                    try{
                        CommentMaidResponse mCommentMaidResponse = response.body();
                        mMaidProfileView.getListCommentMaid(mCommentMaidResponse);
                    }catch (Exception e){
                        Log.e("exception", e.toString());
                        mMaidProfileView.getMessager();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentMaidResponse> call, Throwable t) {
                Log.e("errors", t.toString());
                mMaidProfileView.getMessager();
            }
        });
    }
}

