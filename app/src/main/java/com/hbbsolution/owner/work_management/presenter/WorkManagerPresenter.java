package com.hbbsolution.owner.work_management.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.work_management.model.WorkManagerResponse;
import com.hbbsolution.owner.work_management.view.WorkManagerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 5/10/2017.
 */

public class WorkManagerPresenter {
    private WorkManagerView mWorkManagerView;
    private ApiInterface apiService;

    public WorkManagerPresenter(WorkManagerView mWorkManagerView){
        this.mWorkManagerView = mWorkManagerView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getInfoWorkList(String token, String process){
        Call<WorkManagerResponse> call = apiService.getInfo(token, process);
        call.enqueue(new Callback<WorkManagerResponse>() {
            @Override
            public void onResponse(Call<WorkManagerResponse> call, Response<WorkManagerResponse> response) {
                if(response.isSuccessful()){
                    try{
                        WorkManagerResponse workManagerResponse = response.body();
                        mWorkManagerView.getInfoJob(workManagerResponse);
                    }catch (Exception e){
                        Log.e("exception", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<WorkManagerResponse> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }
}
