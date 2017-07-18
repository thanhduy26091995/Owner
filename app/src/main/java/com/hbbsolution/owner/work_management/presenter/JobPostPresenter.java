package com.hbbsolution.owner.work_management.presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;
import com.hbbsolution.owner.work_management.model.jobpost.JobPostResponse;
import com.hbbsolution.owner.work_management.view.jobpost.JobPostView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 5/23/2017.
 */

public class JobPostPresenter {

    private JobPostView mJobPostView;
    private ApiInterface apiService;

    public JobPostPresenter(JobPostView mJobPostView) {
        this.mJobPostView = mJobPostView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getAllTypeJob() {
        Call<TypeJobResponse> responseCall = apiService.getAllTypeJob();
        responseCall.enqueue(new Callback<TypeJobResponse>() {
            @Override
            public void onResponse(Call<TypeJobResponse> call, Response<TypeJobResponse> response) {
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    if (status) {
                        mJobPostView.getAllTypeJob(response.body());
                    }
                } else {
                    mJobPostView.displayError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TypeJobResponse> call, Throwable t) {
                mJobPostView.connectServerFail();
            }
        });
    }

    public void getLocaltionAddress(String addressofOwner) {
        Call<GeoCodeMapResponse> responseCall = apiService.getLocaltionAddress("https://maps.googleapis.com/maps/api/geocode/json", addressofOwner);
        responseCall.enqueue(new Callback<GeoCodeMapResponse>() {
            @Override
            public void onResponse(Call<GeoCodeMapResponse> call, Response<GeoCodeMapResponse> response) {
                if (response.isSuccessful()) {
                    GeoCodeMapResponse mGeoCodeMapResponse = response.body();
                    if(mGeoCodeMapResponse.getResults().size() > 0){
                        double lat = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
                        double lng = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
                        if (lat != 0 || lng != 0) {
                            mJobPostView.getLocaltionAddress(mGeoCodeMapResponse);
                        } else {
                            mJobPostView.displayNotFoundLocaltion();
                        }

                    }else {
                        mJobPostView.displayNotFoundLocaltion();
                    }

                }
            }

            @Override
            public void onFailure(Call<GeoCodeMapResponse> call, Throwable t) {
                mJobPostView.connectServerFail();
            }
        });
    }

    public void postJob(String title, String typeJob, String description, String address, double lat, double lng,
                        boolean isTool, String packageId, String price, String timeStartWork, String timeEndWork) {

        Call<JobPostResponse> responseCall = apiService.postJob(title, typeJob, description, address,
                lat, lng, isTool, packageId, price, timeStartWork, timeEndWork);
        responseCall.enqueue(new Callback<JobPostResponse>() {
            @Override
            public void onResponse(Call<JobPostResponse> call, Response<JobPostResponse> response) {

                if (response.isSuccessful()) {

//                    Boolean isJbPost = response.body().getStatus();
                    mJobPostView.displayNotifyJobPost(response.body());
                }
            }

            @Override
            public void onFailure(Call<JobPostResponse> call, Throwable t) {
                //Log.d("onFailure", t.toString());
                mJobPostView.connectServerFail();
            }
        });
    }

    public void updatePostJob( String idTask, String title, String typeJob, String description, String address, double lat, double lng,
                        boolean isTool, String packageId, String price, String timeStartWork, String timeEndWork) {

        Call<JobPostResponse> responseCall = apiService.updatePostJob(idTask, title, typeJob, description, address,
                lat, lng, isTool, packageId, price, timeStartWork, timeEndWork);
        responseCall.enqueue(new Callback<JobPostResponse>() {
            @Override
            public void onResponse(Call<JobPostResponse> call, Response<JobPostResponse> response) {

                try{
                    if (response.isSuccessful()) {

//                        Boolean isJbPost = response.body().getStatus();
                        mJobPostView.displayNotifyJobPost(response.body());
                    }
                }catch (Exception e){
                    Log.d("Exception", e.toString());
                }

            }

            @Override
            public void onFailure(Call<JobPostResponse> call, Throwable t) {
               // Log.d("onFailure", t.toString());
                mJobPostView.connectServerFail();
            }
        });
    }

}
