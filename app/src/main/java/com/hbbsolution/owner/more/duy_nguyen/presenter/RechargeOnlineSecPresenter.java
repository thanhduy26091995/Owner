package com.hbbsolution.owner.more.duy_nguyen.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.duy_nguyen.RechargeOnlineSecView;
import com.hbbsolution.owner.more.duy_nguyen.model.RechargeOnlineSecResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 29/06/2017.
 */

public class RechargeOnlineSecPresenter {
    private RechargeOnlineSecView rechargeOnlineSecView;
    private ApiInterface apiService;

    public RechargeOnlineSecPresenter(RechargeOnlineSecView rechargeOnlineSecView) {
        this.rechargeOnlineSecView = rechargeOnlineSecView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }
    public void getRechargeOnlineSec(String key,String billId)
    {
        Call<RechargeOnlineSecResponse> call = apiService.getRechargeOnlineSec(key, billId);
        call.enqueue(new Callback<RechargeOnlineSecResponse>() {
            @Override
            public void onResponse(Call<RechargeOnlineSecResponse> call, Response<RechargeOnlineSecResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        RechargeOnlineSecResponse rechargeOnlineSecResponse = response.body();
                        rechargeOnlineSecView.secSuccess(rechargeOnlineSecResponse.getData().getKey());
                    } catch (Exception e) {
                        rechargeOnlineSecView.secFail();
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<RechargeOnlineSecResponse> call, Throwable t) {
                rechargeOnlineSecView.secFail();
            }
        });
    }
}
