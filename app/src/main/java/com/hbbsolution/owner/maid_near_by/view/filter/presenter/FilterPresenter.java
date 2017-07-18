package com.hbbsolution.owner.maid_near_by.view.filter.presenter;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.maid_near_by.view.filter.view.FilterView;
import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.model.TypeJobResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 23/05/2017.
 */

public class FilterPresenter {
    private FilterView view;
    private ApiInterface apiService;

    public FilterPresenter(FilterView view) {
        this.view = view;
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
                        view.getAllTypeJob(response.body());
                    }
                } else {
                    view.displayError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TypeJobResponse> call, Throwable t) {
                view.connectServerFail();
            }
        });
    }

    public void filterMaid(Double lat, Double lng, Integer ageMin, Integer ageMax, Integer gender, Integer maxDistance, Integer priceMin, Integer priceMax, String workId) {
        Call<MaidNearByResponse> nearByResponseCall = apiService.getMaidNearBy(lat, lng, ageMin, ageMax, gender, maxDistance, priceMin, priceMax, workId);
        nearByResponseCall.enqueue(new Callback<MaidNearByResponse>() {
            @Override
            public void onResponse(Call<MaidNearByResponse> call, Response<MaidNearByResponse> response) {
                if (response.isSuccessful()) {
                    view.filterMaid(response.body());

                } else {
                    view.displayError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MaidNearByResponse> call, Throwable t) {
                view.connectServerFail();
            }
        });
    }
}
