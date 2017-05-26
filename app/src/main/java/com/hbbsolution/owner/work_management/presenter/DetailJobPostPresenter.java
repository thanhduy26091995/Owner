package com.hbbsolution.owner.work_management.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.work_management.model.jobpost.JobPostResponse;
import com.hbbsolution.owner.work_management.view.detail.DetailJobPostView;
import com.hbbsolution.owner.work_management.view.jobpost.JobPostView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 5/26/2017.
 */

public class DetailJobPostPresenter {

    private DetailJobPostView mDetailJobPostView;
    private ApiInterface apiService;

    public DetailJobPostPresenter(DetailJobPostView mDetailJobPostView) {
        this.mDetailJobPostView = mDetailJobPostView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void deleteJob(String idTask, String ownerId) {

        Call<JobPostResponse> responseCall = apiService.delleteJob(idTask, ownerId);
        responseCall.enqueue(new Callback<JobPostResponse>() {
            @Override
            public void onResponse(Call<JobPostResponse> call, Response<JobPostResponse> response) {
                Log.d("onResponse", response.code() + "");
                try{
                    if (response.isSuccessful()) {

                        Boolean isJbPost = response.body().getStatus();
                        mDetailJobPostView.displayNotifyJobPost(isJbPost);
                    }
                }catch (Exception e){
                    Log.d("Exception", e.toString());
                }

            }

            @Override
            public void onFailure(Call<JobPostResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }
}
