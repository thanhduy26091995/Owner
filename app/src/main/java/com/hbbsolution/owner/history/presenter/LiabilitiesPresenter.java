package com.hbbsolution.owner.history.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.history.LiabilitiesView;
import com.hbbsolution.owner.history.model.liabilities.LiabilitiesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 05/06/2017.
 */

public class LiabilitiesPresenter {
    private LiabilitiesView liabilitiesView;
    private ApiInterface apiService;

    public LiabilitiesPresenter(LiabilitiesView liabilitiesView) {
        this.liabilitiesView = liabilitiesView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getInfoLiabilities(String endTime) {
        Call<LiabilitiesResponse> call = apiService.getLiabilities("", endTime);
        call.enqueue(new Callback<LiabilitiesResponse>() {
            @Override
            public void onResponse(Call<LiabilitiesResponse> call, Response<LiabilitiesResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        LiabilitiesResponse liabilitiesResponse = response.body();
                        liabilitiesView.getLiabilitiesSuccess(liabilitiesResponse.getData());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                        liabilitiesView.getLiabilitiesError();
                    }
                }
            }

            @Override
            public void onFailure(Call<LiabilitiesResponse> call, Throwable t) {
                liabilitiesView.connectServerFail();
                Log.e("error", t.toString());
            }
        });
    }

    public void getInfoLiabilitiesTime(String startTime, String endTime) {
        Call<LiabilitiesResponse> call = apiService.getLiabilities(startTime, endTime);
        call.enqueue(new Callback<LiabilitiesResponse>() {
            @Override
            public void onResponse(Call<LiabilitiesResponse> call, Response<LiabilitiesResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        LiabilitiesResponse liabilitiesResponse = response.body();
                        liabilitiesView.getLiabilitiesSuccess(liabilitiesResponse.getData());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                        liabilitiesView.getLiabilitiesError();
                    }
                }
            }

            @Override
            public void onFailure(Call<LiabilitiesResponse> call, Throwable t) {
                liabilitiesView.connectServerFail();
                Log.e("error", t.toString());
            }
        });
    }
}
