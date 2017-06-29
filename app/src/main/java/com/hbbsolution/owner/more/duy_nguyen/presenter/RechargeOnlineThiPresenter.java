package com.hbbsolution.owner.more.duy_nguyen.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.duy_nguyen.RechargeOnlineThiView;
import com.hbbsolution.owner.more.duy_nguyen.model.RechargeOnlineThiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 29/06/2017.
 */

public class RechargeOnlineThiPresenter {
    private RechargeOnlineThiView rechargeOnlineThiView;
    private ApiInterface apiService;

    public RechargeOnlineThiPresenter(RechargeOnlineThiView rechargeOnlineThiView) {
        this.rechargeOnlineThiView = rechargeOnlineThiView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }
    public void getRechargeOnlineThi(String billId)
    {
        Call<RechargeOnlineThiResponse> call = apiService.getRechargeOnlineThi(billId);
        call.enqueue(new Callback<RechargeOnlineThiResponse>() {
            @Override
            public void onResponse(Call<RechargeOnlineThiResponse> call, Response<RechargeOnlineThiResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        RechargeOnlineThiResponse rechargeOnlineThiResponse = response.body();
                        rechargeOnlineThiView.thiSuccess();
                    } catch (Exception e) {
                        rechargeOnlineThiView.thiFail();
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<RechargeOnlineThiResponse> call, Throwable t) {
                rechargeOnlineThiView.thiFail();
            }
        });
    }
}
