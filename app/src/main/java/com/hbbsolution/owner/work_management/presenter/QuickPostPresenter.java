package com.hbbsolution.owner.work_management.presenter;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.work_management.view.jobpost.JobPostView;
import com.hbbsolution.owner.work_management.view.quickpost.QuickPostView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 19/07/2017.
 */

public class QuickPostPresenter {
    private QuickPostView mQuickPostView;
    private ApiInterface apiService;

    public QuickPostPresenter(QuickPostView mQuickPostView) {
        this.mQuickPostView = mQuickPostView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getAllTypeJob() {
        Call<TypeJobResponse> responseCall = apiService.getAllTypeJob();
        responseCall.enqueue(new Callback<TypeJobResponse>() {
            @Override
            public void onResponse(Call<TypeJobResponse> call, Response<TypeJobResponse> response) {
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    if (status) {
                        mQuickPostView.getAllTypeJob(response.body());
                    }
                } else {
                    mQuickPostView.displayError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TypeJobResponse> call, Throwable t) {
                mQuickPostView.connectServerFail();
            }
        });
    }
}
