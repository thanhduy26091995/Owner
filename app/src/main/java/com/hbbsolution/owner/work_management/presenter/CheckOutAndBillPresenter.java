package com.hbbsolution.owner.work_management.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.work_management.model.billGv24.BillGv24Response;
import com.hbbsolution.owner.work_management.model.chekout.CheckOutResponse;
import com.hbbsolution.owner.work_management.view.detail.CheckOutView;
import com.hbbsolution.owner.work_management.view.detail.DetailJobPostView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 6/21/2017.
 */

public class CheckOutAndBillPresenter {
    private CheckOutView mCheckOutView;
    private ApiInterface apiService;

    public CheckOutAndBillPresenter(CheckOutView mCheckOutView) {
        this.mCheckOutView = mCheckOutView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getInfoCheckOut(String idTask) {
        Call<CheckOutResponse> responseCall = apiService.getInfoCheckOut(idTask);
        responseCall.enqueue(new Callback<CheckOutResponse>() {
            @Override
            public void onResponse(Call<CheckOutResponse> call, Response<CheckOutResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        mCheckOutView.getInfoCheckOut(response.body());
                    }
                } catch (Exception e) {
                        mCheckOutView.getErrorCheckOut(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CheckOutResponse> call, Throwable t) {
                mCheckOutView.getErrorCheckOut(t.getMessage());
            }
        });
    }

}
