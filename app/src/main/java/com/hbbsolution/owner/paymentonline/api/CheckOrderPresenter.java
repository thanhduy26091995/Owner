package com.hbbsolution.owner.paymentonline.api;

import android.util.Log;


import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.paymentonline.ui.activity.CheckOrderView;
import com.hbbsolution.owner.work_management.model.billGv24.BillGv24Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 6/26/2017.
 */

public class CheckOrderPresenter {

    private CheckOrderView mCheckOrderView;
    private ApiInterface apiService;

    public CheckOrderPresenter(CheckOrderView mCheckOrderView) {
        this.mCheckOrderView = mCheckOrderView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getInfoPaymnetByOnline(String idBill) {
        Call<BillGv24Response> call = apiService.getInfoPaymnetByOnlineSuccess(idBill);
        call.enqueue(new Callback<BillGv24Response>() {
            @Override
            public void onResponse(Call<BillGv24Response> call, Response<BillGv24Response> response) {
                if (response.isSuccessful()) {
                    try {
                        mCheckOrderView.checkOrderServerSuccess();
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                        mCheckOrderView.getErrorPaymentBymoney(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<BillGv24Response> call, Throwable t) {
                mCheckOrderView.getErrorPaymentBymoney(t.toString());
            }
        });
    }
}
