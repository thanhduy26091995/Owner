package com.hbbsolution.owner.maid_profile.choose_maid.presenter;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.maid_profile.choose_maid.view.ChooseMaidView;
import com.hbbsolution.owner.model.TypeJobResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 30/05/2017.
 */

public class ChooseMaidPresenter {
    private ApiInterface apiService;
    private ChooseMaidView view;

    public ChooseMaidPresenter(ChooseMaidView view) {
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
                view.displayError(t.getMessage());
            }
        });
    }
}
