package com.hbbsolution.owner.work_management.view.payment.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.work_management.view.payment.PaymentView;
import com.hbbsolution.owner.work_management.view.payment.model.PaymentResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 13/06/2017.
 */

public class PaymentPresenter {
    private PaymentView paymentView;
    private ApiInterface apiService;

    public PaymentPresenter(PaymentView paymentView) {
        this.paymentView = paymentView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getWallet() {
        Call<PaymentResponse> call = apiService.getWallet();
        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        PaymentResponse paymentResponse = response.body();
                        paymentView.getWalletSuccess(paymentResponse.getData().getWallet());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
            }
        });
    }
}
