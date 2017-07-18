package com.hbbsolution.owner.history.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.history.ListWorkView;
import com.hbbsolution.owner.history.model.workhistory.WorkHistoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 08/06/2017.
 */

public class ListWorkPresenter {
    private ListWorkView listWorkView;
    private ApiInterface apiService;

    public ListWorkPresenter(ListWorkView listWorkView) {
        this.listWorkView = listWorkView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getInfoListWorkHistory(String idMaid, int page) {
        Call<WorkHistoryResponse> call = apiService.getListWorkByMaid(idMaid, page);
        call.enqueue(new Callback<WorkHistoryResponse>() {
            @Override
            public void onResponse(Call<WorkHistoryResponse> call, Response<WorkHistoryResponse> response) {
                Log.e("onResponse", "isResponse");
                if (response.isSuccessful()) {
                    try {
                        int codeResponse = response.code();
                        Log.e("codeResponse", codeResponse + "");
                        WorkHistoryResponse workManagerResponse = response.body();
                        listWorkView.getInfoListWorkHistory(workManagerResponse.getData().getDocs(), workManagerResponse.getData().getPages());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                        listWorkView.getError();
                    }
                }
            }

            @Override
            public void onFailure(Call<WorkHistoryResponse> call, Throwable t) {
                Log.e("dsaerror", t.toString());
                listWorkView.connectServerFail();
            }
        });
    }

    public void getMoreInfoListWorkHistory(String idMaid, int page) {
        Call<WorkHistoryResponse> call = apiService.getListWorkByMaid(idMaid, page);
        call.enqueue(new Callback<WorkHistoryResponse>() {
            @Override
            public void onResponse(Call<WorkHistoryResponse> call, Response<WorkHistoryResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        WorkHistoryResponse workManagerResponse = response.body();
                        listWorkView.getMoreInfoListWorkHistory(workManagerResponse.getData().getDocs());
                    } catch (Exception e) {
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<WorkHistoryResponse> call, Throwable t) {
                Log.e("error", t.toString());
                listWorkView.connectServerFail();
            }
        });
    }
}
