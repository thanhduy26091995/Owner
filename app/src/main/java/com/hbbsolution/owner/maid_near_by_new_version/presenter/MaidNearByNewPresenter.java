package com.hbbsolution.owner.maid_near_by_new_version.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.maid_near_by_new_version.view.MaidNearByNewView;
import com.hbbsolution.owner.model.MaidNearByResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 08/09/2017.
 */

public class MaidNearByNewPresenter {
    private MaidNearByNewView mView;
    private ApiInterface mApiInterface;

    public MaidNearByNewPresenter(MaidNearByNewView mView) {
        this.mView = mView;
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getMaidNearBy(Double lat, Double lng) {
        Call<MaidNearByResponse> mMaidNearByResponseCall = mApiInterface.getMaidNearBy(lat, lng, null, null, null, null, null, null, null);
        mMaidNearByResponseCall.enqueue(new Callback<MaidNearByResponse>() {
            @Override
            public void onResponse(Call<MaidNearByResponse> call, Response<MaidNearByResponse> response) {
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    if (status) {
                        mView.displayMaidNearBy(response.body());
                    } else {
                        mView.displayError(response.body().getMessage());
                    }
                } else {
                    mView.displayError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MaidNearByResponse> call, Throwable t) {
                mView.connectServerFail();
                Log.d("ERROR", t.getMessage());
            }
        });
    }
}
