package com.hbbsolution.owner.more.viet_pham.Presenter;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.DataUpdateResponse;
import com.hbbsolution.owner.more.viet_pham.View.MoreView;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 6/21/2017.
 */

public class SignInGooAndFacePresenter {
    private MoreView mMoreView;
    private ApiInterface mApiService;

    public SignInGooAndFacePresenter(MoreView mMoreView) {
        this.mMoreView = mMoreView;
        mApiService = ApiClient.getClient().create(ApiInterface.class);
    }


    public void signInGooAndFace(String id, String token, String deviceToken) {
        RequestBody requestBodyId = RequestBody.create(MediaType.parse("text"), id);
        RequestBody requestBodyToken= RequestBody.create(MediaType.parse("text"), token);
        RequestBody requestBodyDeviceToken = RequestBody.create(MediaType.parse("text"), deviceToken);
        mApiService.signInGoogleAndFace(requestBodyId, requestBodyToken, requestBodyDeviceToken).enqueue(new Callback<DataUpdateResponse>() {
            @Override
            public void onResponse(Call<DataUpdateResponse> call, Response<DataUpdateResponse> response) {
                try {
                    DataUpdateResponse dataUpdateResponse = response.body();
                    mMoreView.displaySignInGooAndFace(dataUpdateResponse);

                } catch (Exception e) {
                    mMoreView.displayError();
                }
            }

            @Override
            public void onFailure(Call<DataUpdateResponse> call, Throwable t) {

            }
        });
    }
}
