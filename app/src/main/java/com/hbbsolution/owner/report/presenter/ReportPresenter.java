package com.hbbsolution.owner.report.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.report.ReportView;
import com.hbbsolution.owner.report.model.ReportResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 30/05/2017.
 */

public class ReportPresenter {
    private ReportView reportView;
    private ApiInterface apiService;

    public ReportPresenter(ReportView reportView) {
        this.reportView = reportView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void reportMaid(String toId,String content) {
        Call<ReportResponse> call = apiService.reportMaid(toId,content);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        ReportResponse reportResponse = response.body();
                        reportView.reportSuccess(reportResponse.getMessage());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
            }
        });
    }
}
