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
                mJobPostView.displayError(t.getMessage());
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
                    double lat = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
                    double lng = mGeoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
                    if (lat != 0 || lng != 0) {
                        mJobPostView.getLocaltionAddress(mGeoCodeMapResponse);
                    } else {
                        mJobPostView.displayNotFoundLocaltion();
                    }

                }
            }

            @Override
            public void onFailure(Call<GeoCodeMapResponse> call, Throwable t) {

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

                    Boolean isJbPost = response.body().getStatus();
                    mJobPostView.displayNotifyJobPost(isJbPost);
                }
            }

            @Override
            public void onFailure(Call<JobPostResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }
}
