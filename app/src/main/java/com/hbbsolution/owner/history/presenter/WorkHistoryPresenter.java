package com.hbbsolution.owner.history.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.history.WorkHistoryView;
import com.hbbsolution.owner.history.model.workhistory.WorkHistoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 18/05/2017.
 */

public class WorkHistoryPresenter {
    private WorkHistoryView workHistoryView;
    private ApiInterface apiService;

    public WorkHistoryPresenter(WorkHistoryView workHistoryView) {
        this.workHistoryView = workHistoryView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getInfoWorkHistory(int page,String endTime) {
        Call<WorkHistoryResponse> call = apiService.getInfoWorkHistory("", endTime, page);
        call.enqueue(new Callback<WorkHistoryResponse>() {
            @Override
            public void onResponse(Call<WorkHistoryResponse> call, Response<WorkHistoryResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        WorkHistoryResponse workManagerResponse = response.body();
                        workHistoryView.getInfoWorkHistory(workManagerResponse.getData().getDocs(), workManagerResponse.getData().getPages());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<WorkHistoryResponse> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    public void getMoreInfoWorkHistory(int page,String endDate) {
        Call<WorkHistoryResponse> call = apiService.getInfoWorkHistory("", endDate, page);
        call.enqueue(new Callback<WorkHistoryResponse>() {
            @Override
            public void onResponse(Call<WorkHistoryResponse> call, Response<WorkHistoryResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        WorkHistoryResponse workManagerResponse = response.body();
                        workHistoryView.getMoreInfoWorkHistory(workManagerResponse.getData().getDocs());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<WorkHistoryResponse> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    public void getInfoWorkHistoryTime(final String startAt, final String endAt, int page) {
        Call<WorkHistoryResponse> call = apiService.getInfoWorkHistory(startAt, endAt, page);
        call.enqueue(new Callback<WorkHistoryResponse>() {
            @Override
            public void onResponse(Call<WorkHistoryResponse> call, Response<WorkHistoryResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        WorkHistoryResponse workManagerResponse = response.body();
                        workHistoryView.getInfoWorkHistoryTime(workManagerResponse.getData().getDocs(), startAt, endAt, workManagerResponse.getData().getPages());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<WorkHistoryResponse> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    public void getMoreInfoWorkHistoryTime(final String startAt, final String endAt, int page) {
        Call<WorkHistoryResponse> call = apiService.getInfoWorkHistory(startAt, endAt, page);
        call.enqueue(new Callback<WorkHistoryResponse>() {
            @Override
            public void onResponse(Call<WorkHistoryResponse> call, Response<WorkHistoryResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        WorkHistoryResponse workManagerResponse = response.body();
                        workHistoryView.getMoreInfoWorkHistoryTime(workManagerResponse.getData().getDocs(), startAt, endAt);
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<WorkHistoryResponse> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }
}
