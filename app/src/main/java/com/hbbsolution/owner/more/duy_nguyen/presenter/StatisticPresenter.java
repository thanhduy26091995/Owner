package com.hbbsolution.owner.more.duy_nguyen.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.duy_nguyen.StatisticView;
import com.hbbsolution.owner.more.duy_nguyen.model.StatisticResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 01/06/2017.
 */

public class StatisticPresenter {
    private StatisticView statisticView;
    private ApiInterface apiService;

    public StatisticPresenter(StatisticView statisticView) {
        this.statisticView = statisticView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }
    public void getStatistic(String startAt,String endAt)
    {
        Call<StatisticResponse> call = apiService.getStatistic(startAt,endAt);
        call.enqueue(new Callback<StatisticResponse>() {
            @Override
            public void onResponse(Call<StatisticResponse> call, Response<StatisticResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        StatisticResponse statisticResponse = response.body();
                        statisticView.getStatisticSuccess(statisticResponse.getData().getTask(),statisticResponse.getData().getTotalPrice(),statisticResponse.getData().getWallet());
                    } catch (Exception e) {
                        statisticView.getStatisticFail();
                        Log.e("exception", e.toString());
                    }
                }
            }
            @Override
            public void onFailure(Call<StatisticResponse> call, Throwable t) {
                statisticView.getStatisticFail();
            }
        });
    }
}
