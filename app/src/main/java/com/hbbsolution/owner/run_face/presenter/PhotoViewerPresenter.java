package com.hbbsolution.owner.run_face.presenter;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.model.DefaultResponse;
import com.hbbsolution.owner.run_face.view.PhotoViewerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by buivu on 13/09/2017.
 */

public class PhotoViewerPresenter {
    private PhotoViewerView mView;
    private ApiInterface mApiInterface;

    public PhotoViewerPresenter(PhotoViewerView mView) {
        this.mView = mView;
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void sendPushForMaid(String maidId) {
        Call<DefaultResponse> responseCall = mApiInterface.sendPushForMaid(maidId);
        responseCall.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        mView.sendPushNotificationSuccessfully();
                    } else {
                        mView.sendPushNotificationFailed(response.body().getMessage());
                    }
                } else {
                    mView.sendPushNotificationFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                mView.sendPushNotificationFailed(t.getMessage());
            }
        });
    }
}
