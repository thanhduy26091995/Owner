package com.hbbsolution.owner.work_management.view.payment.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.work_management.model.billGv24.BillGv24Response;
import com.hbbsolution.owner.work_management.view.payment.view.PaymentView;
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

    public void getInfoPaymentBill24h(String idBill) {
        Call<BillGv24Response> call = apiService.getInfoBill24h(idBill);
        call.enqueue(new Callback<BillGv24Response>() {
            @Override
            public void onResponse(Call<BillGv24Response> call, Response<BillGv24Response> response) {
                if (response.isSuccessful()) {
                    try {
                        paymentView.getInfoBill24h(response.body());
                    } catch (Exception e) {
                        paymentView.getErrorBill24h(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<BillGv24Response> call, Throwable t) {

                paymentView.getErrorBill24h(t.toString());
            }
        });
    }

    public void getInfoPaymnetByMoney(String idBill) {
        Call<BillGv24Response> call = apiService.getInfoPaymnetByMoney(idBill);
        call.enqueue(new Callback<BillGv24Response>() {
            @Override
            public void onResponse(Call<BillGv24Response> call, Response<BillGv24Response> response) {
                if (response.isSuccessful()) {
                    try {
                        paymentView.getInfoPaymentBymoney(response.body());
                    } catch (Exception e) {
                        paymentView.getErrorPaymentBymoney(e.toString());
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<BillGv24Response> call, Throwable t) {
                paymentView.getErrorPaymentBymoney(t.toString());
            }
        });
    }

    public void getInfoPaymnetByOnline(String idBill) {
        Call<BillGv24Response> call = apiService.getInfoPaymnetByOnline(idBill);
        call.enqueue(new Callback<BillGv24Response>() {
            @Override
            public void onResponse(Call<BillGv24Response> call, Response<BillGv24Response> response) {
                if (response.isSuccessful()) {
                    try {
                        paymentView.getInfoPaymentByOnline(response.body());
                    } catch (Exception e) {
                        paymentView.getErrorPaymentBymoney(e.toString());
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<BillGv24Response> call, Throwable t) {
                paymentView.getErrorPaymentBymoney(t.toString());
            }
        });
    }
}
