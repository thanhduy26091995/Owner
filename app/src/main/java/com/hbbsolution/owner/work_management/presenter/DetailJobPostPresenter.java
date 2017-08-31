package com.hbbsolution.owner.work_management.presenter;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.model.CheckInResponse;
import com.hbbsolution.owner.work_management.model.jobpost.JobPostResponse;
import com.hbbsolution.owner.work_management.view.detail.DetailJobPostView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 5/26/2017.
 */

public class DetailJobPostPresenter {

    private DetailJobPostView mDetailJobPostView;
    private ApiInterface apiService;

    public DetailJobPostPresenter(DetailJobPostView mDetailJobPostView) {
        this.mDetailJobPostView = mDetailJobPostView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void deleteJob(String idTask, String ownerId) {

        Call<JobPostResponse> responseCall = apiService.deleteJob(idTask, ownerId);
        responseCall.enqueue(new Callback<JobPostResponse>() {
            @Override
            public void onResponse(Call<JobPostResponse> call, Response<JobPostResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        Boolean isJbPost = response.body().getStatus();
                        mDetailJobPostView.displayNotifyJobPost(isJbPost);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<JobPostResponse> call, Throwable t) {
                mDetailJobPostView.connectServerFail();
            }
        });
    }

    public void checkIn(String filePath, String ownerId, String taskId) {
        MultipartBody.Part fileToUpload;
        if (filePath.equals("")) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            fileToUpload = MultipartBody.Part.create(requestBody);
        } else {
            File file = new File(filePath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        }

        RequestBody requestOwnerId = RequestBody.create(MediaType.parse("multipart/form-data"), ownerId);
        RequestBody requestTaskId = RequestBody.create(MediaType.parse("multipart/form-data"), taskId);

        Call<CheckInResponse> checkInResponseCall = apiService.checkIn(fileToUpload, requestOwnerId, requestTaskId);
        checkInResponseCall.enqueue(new Callback<CheckInResponse>() {
            @Override
            public void onResponse(Call<CheckInResponse> call, Response<CheckInResponse> response) {
                if (response.isSuccessful()) {
                    mDetailJobPostView.checkIn(response.body());
                } else {
                    mDetailJobPostView.checkInFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<CheckInResponse> call, Throwable t) {
                mDetailJobPostView.checkInFail(t.getMessage());
            }
        });
    }
}
