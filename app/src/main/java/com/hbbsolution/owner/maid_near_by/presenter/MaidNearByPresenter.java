package com.hbbsolution.owner.maid_near_by.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.maid_near_by.view.MaidNearByView;
import com.hbbsolution.owner.model.MaidNearByResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 18/05/2017.
 */

public class MaidNearByPresenter {
    private MaidNearByView mView;
    private ApiInterface mApiService;

    public MaidNearByPresenter(MaidNearByView mView) {
        this.mView = mView;
        mApiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getMaidNearBy(String token, Double lat, Double lng) {
        Call<MaidNearByResponse> mMaidNearByResponseCall = mApiService.getMaidNearBy(token, lat, lng);
        mMaidNearByResponseCall.enqueue(new Callback<MaidNearByResponse>() {
            @Override
            public void onResponse(Call<MaidNearByResponse> call, Response<MaidNearByResponse> response) {
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    if (status) {
                        mView.displayMaidNearBy(response.body());
                    }
                } else {
                    mView.displayError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MaidNearByResponse> call, Throwable t) {
                mView.displayError(t.getMessage());
                Log.d("ERROR", t.getMessage());
            }
        });
    }
}
