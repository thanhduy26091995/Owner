package com.hbbsolution.owner.home.prsenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.home.model.ResponseRequest;
import com.hbbsolution.owner.home.view.HomeView;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.work_management.model.jobpost.JobPostResponse;
import com.hbbsolution.owner.work_management.view.jobpost.JobPostView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 6/1/2017.
 */

public class HomePresenter {
    private HomeView mHomeView;
    private ApiInterface apiService;

    public HomePresenter(HomeView mHomeView) {
        this.mHomeView = mHomeView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void requestCheckToken() {
        Call<ResponseRequest> responseCall = apiService.checkToken();
        responseCall.enqueue(new Callback<ResponseRequest>() {
            @Override
            public void onResponse(Call<ResponseRequest> call, Response<ResponseRequest> response) {
                Log.d("onResponse", response.code() + "");
                    if(response.code() == 401){
                        mHomeView.responseCheckToken();
                    }
            }

            @Override
            public void onFailure(Call<ResponseRequest> call, Throwable t) {
                Log.d("onFailure", t.toString());
                mHomeView.errorConnectService();
            }
        });
    }
}
