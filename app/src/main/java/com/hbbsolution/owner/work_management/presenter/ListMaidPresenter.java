package com.hbbsolution.owner.work_management.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.work_management.model.jobpost.JobPostResponse;
import com.hbbsolution.owner.work_management.model.maid.ListMaidResponse;
import com.hbbsolution.owner.work_management.view.listmaid.ListMaidView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 5/18/2017.
 */

public class ListMaidPresenter {
    private ListMaidView mListMaidView;
    private ApiInterface apiService;

    public ListMaidPresenter(ListMaidView mListMaidView){
        this.mListMaidView = mListMaidView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getInfoListMaid(String process){
        Call<ListMaidResponse> call = apiService.getInfoListMaid(process);
        call.enqueue(new Callback<ListMaidResponse>() {
            @Override
            public void onResponse(Call<ListMaidResponse> call, Response<ListMaidResponse> response) {
                if(response.isSuccessful()){
                    try{
                        ListMaidResponse mListMaidResponse = response.body();
                        mListMaidView.getInfoListMaid(mListMaidResponse);
                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call<ListMaidResponse> call, Throwable t) {
            }
        });
    }

    public void sentRequestChosenMaid(String id, String maidId){
        Call<JobPostResponse> call = apiService.sentRequestChosenMaid(id, maidId);
        call.enqueue(new Callback<JobPostResponse>() {
            @Override
            public void onResponse(Call<JobPostResponse> call, Response<JobPostResponse> response) {

                if(response.isSuccessful()){

                    try{
//                        Boolean isJbPost = response.body().getStatus();
                        mListMaidView.responseChosenMaid(response.body());
                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call<JobPostResponse> call, Throwable t) {
                Log.e("errors", t.toString());
            }
        });
    }
}
