package com.hbbsolution.owner.history.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.history.HelperHistoryView;
import com.hbbsolution.owner.history.model.helper.HistoryHelperResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 29/05/2017.
 */

public class HelperHistoryPresenter {
    private HelperHistoryView helperHistoryView;
    private ApiInterface apiService;

    public HelperHistoryPresenter(HelperHistoryView helperHistoryView) {
        this.helperHistoryView = helperHistoryView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getInfoHelperHistory(String endTime) {
        Call<HistoryHelperResponse> call = apiService.getAllWorkedMaid("", endTime);
        call.enqueue(new Callback<HistoryHelperResponse>() {
            @Override
            public void onResponse(Call<HistoryHelperResponse> call, Response<HistoryHelperResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        HistoryHelperResponse historyHelperResponse = response.body();
                        helperHistoryView.getInfoHelperHistory(historyHelperResponse.getData());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                        helperHistoryView.getInfoHelperHistoryFail();
                    }
                }
            }

            @Override
            public void onFailure(Call<HistoryHelperResponse> call, Throwable t) {
                helperHistoryView.getInfoHelperHistoryFail();
                Log.e("error", t.toString());
            }
        });
    }
    public void getInfoHelperHistoryTime(String startTime,String endTime) {
        Call<HistoryHelperResponse> call = apiService.getAllWorkedMaid(startTime, endTime);
        call.enqueue(new Callback<HistoryHelperResponse>() {
            @Override
            public void onResponse(Call<HistoryHelperResponse> call, Response<HistoryHelperResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        HistoryHelperResponse historyHelperResponse = response.body();
                        helperHistoryView.getInfoHelperHistory(historyHelperResponse.getData());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                        helperHistoryView.getInfoHelperHistoryFail();
                    }
                }
            }

            @Override
            public void onFailure(Call<HistoryHelperResponse> call, Throwable t) {
                Log.e("error", t.toString());
                helperHistoryView.getInfoHelperHistoryFail();
            }
        });
    }
}
