package com.hbbsolution.owner.maid_near_by.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.maid_near_by.view.MaidNearByView;
import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

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

    public void getMaidNearBy(Double lat, Double lng) {
        Call<MaidNearByResponse> mMaidNearByResponseCall = mApiService.getMaidNearBy(lat, lng, null, null, null, null, null, null);
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

    public void searchMaid(Double lat, Double lng) {
        Call<MaidNearByResponse> nearByResponseCall = mApiService.searchMaidByAddress(lat, lng);
        nearByResponseCall.enqueue(new Callback<MaidNearByResponse>() {
            @Override
            public void onResponse(Call<MaidNearByResponse> call, Response<MaidNearByResponse> response) {
                if (response.isSuccessful()) {
                    mView.displaySearchResult(response.body());
                } else {
                    mView.displayError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MaidNearByResponse> call, Throwable t) {
                mView.displayError(t.getMessage());
            }
        });
    }

    public void getLocationAddress(String addressOfMaid) {
        Call<GeoCodeMapResponse> responseCall = mApiService.getLocaltionAddress("https://maps.googleapis.com/maps/api/geocode/json", addressOfMaid);
        responseCall.enqueue(new Callback<GeoCodeMapResponse>() {
            @Override
            public void onResponse(Call<GeoCodeMapResponse> call, Response<GeoCodeMapResponse> response) {
                if (response.isSuccessful()) {
                    GeoCodeMapResponse mGeoCodeMapResponse = response.body();
                    if (mGeoCodeMapResponse.getResults().size() > 0) {
                        double lat = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
                        double lng = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
                        if (lat != 0 || lng != 0) {
                            mView.getLocationAddress(mGeoCodeMapResponse);
                        } else {
                            mView.displayNotFoundLocation("Không tìm thấy vị trí");
                        }
                    } else {
                        mView.displayNotFoundLocation("Không tìm thấy vị trí");
                    }
                }
            }

            @Override
            public void onFailure(Call<GeoCodeMapResponse> call, Throwable t) {
                mView.displayError(t.getMessage());
            }
        });
    }
}
