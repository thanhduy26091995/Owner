package com.hbbsolution.owner.more.phuc_tran.presenter;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.phuc_tran.view.ForgotPassView;
import com.hbbsolution.owner.more.phuc_tran.model.ForgotPassResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 6/25/2017.
 */

public class ForgotPasswordPresenter {
    private ForgotPassView mForgotPassView;
    private ApiInterface apiService;

    public ForgotPasswordPresenter(ForgotPassView mForgotPassView) {
        this.mForgotPassView = mForgotPassView;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void forgotPassword(String email,String username) {
        Call<ForgotPassResponse> responseCall = apiService.forgotPassword(email,username);
        responseCall.enqueue(new Callback<ForgotPassResponse>() {
            @Override
            public void onResponse(Call<ForgotPassResponse> call, Response<ForgotPassResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        mForgotPassView.getForgotPass(response.body());
                    }
                } catch (Exception e) {
                    mForgotPassView.getErrorForgotPass(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ForgotPassResponse> call, Throwable t) {

                mForgotPassView.getErrorForgotPass(t.toString());
            }
        });
    }
}
