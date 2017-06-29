package com.hbbsolution.owner.more.duy_nguyen.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.duy_nguyen.model.RechargeOnlineFiResponse;
import com.hbbsolution.owner.more.duy_nguyen.model.RechargeOnlineFiView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 29/06/2017.
 */

public class RechargeOnlineFiPresenter {
    private RechargeOnlineFiView rechargeOnlineFiView;
    private ApiInterface apiService;

    public RechargeOnlineFiPresenter(RechargeOnlineFiView rechargeOnlineFiView) {
        this.rechargeOnlineFiView = rechargeOnlineFiView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }
    public void getRechargeOnlineFi(double price)
    {
        Call<RechargeOnlineFiResponse> call = apiService.getRechargeOnlineFi(price);
        call.enqueue(new Callback<RechargeOnlineFiResponse>() {
            @Override
            public void onResponse(Call<RechargeOnlineFiResponse> call, Response<RechargeOnlineFiResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        RechargeOnlineFiResponse rechargeOnlineFiResponse = response.body();
                        rechargeOnlineFiView.fiSuccess(rechargeOnlineFiResponse.getData().getBill(),rechargeOnlineFiResponse.getData().getKey());
                    } catch (Exception e) {
                        rechargeOnlineFiView.fiFail();
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<RechargeOnlineFiResponse> call, Throwable t) {
                rechargeOnlineFiView.fiFail();
            }
        });
    }
}
