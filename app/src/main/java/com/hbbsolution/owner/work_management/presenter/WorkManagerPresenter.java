package com.hbbsolution.owner.work_management.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.work_management.model.workmanager.WorkManagerResponse;
import com.hbbsolution.owner.work_management.model.workmanagerpending.JobPendingResponse;
import com.hbbsolution.owner.work_management.view.workmanager.WorkManagerView;

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

    public void getInfoWorkList(String process){
        Call<WorkManagerResponse> call = apiService.getInfo(process);
        call.enqueue(new Callback<WorkManagerResponse>() {
            @Override
            public void onResponse(Call<WorkManagerResponse> call, Response<WorkManagerResponse> response) {
                if(response.isSuccessful()){
                    try{
                        WorkManagerResponse workManagerResponse = response.body();
                        mWorkManagerView.getInfoJob(workManagerResponse);
                    }catch (Exception e){
                        mWorkManagerView.getError();
                    }
                }
            }

            @Override
            public void onFailure(Call<WorkManagerResponse> call, Throwable t) {
            }
        });
    }

    public void getInfoJobPending(String process){
        Call<JobPendingResponse> call = apiService.getJobPendingResponse(process);
        call.enqueue(new Callback<JobPendingResponse>() {
            @Override
            public void onResponse(Call<JobPendingResponse> call, Response<JobPendingResponse> response) {
                Log.e("onResponses", "isonResponse");
                if(response.isSuccessful()){
                    try{
                        Log.e("onResponse", "isonResponse");
                        JobPendingResponse jobPendingResponse = response.body();
                        mWorkManagerView.getInfoJobPending(jobPendingResponse);
                    }catch (Exception e){
                        Log.e("exception", e.toString());
                        mWorkManagerView.getError();
                    }
                }
            }

            @Override
            public void onFailure(Call<JobPendingResponse> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }
}
