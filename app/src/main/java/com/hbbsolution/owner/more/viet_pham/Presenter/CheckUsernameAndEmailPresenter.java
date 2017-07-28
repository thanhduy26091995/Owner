package com.hbbsolution.owner.more.viet_pham.Presenter;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.CheckUsernameEmailResponse;
import com.hbbsolution.owner.more.viet_pham.View.CheckUsernameAndEmailView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 7/28/2017.
 */

public class CheckUsernameAndEmailPresenter {
    private ApiInterface mApiInterface;
    private CheckUsernameAndEmailView mCheckUsernameAndEmailView;

    public CheckUsernameAndEmailPresenter(CheckUsernameAndEmailView mCheckUsernameAndEmailView){
        this.mCheckUsernameAndEmailView = mCheckUsernameAndEmailView;
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void checkUsername(String username){
        mApiInterface.checkUsername(username).enqueue(new Callback<CheckUsernameEmailResponse>() {
            @Override
            public void onResponse(Call<CheckUsernameEmailResponse> call, Response<CheckUsernameEmailResponse> response) {
                if (response.isSuccessful()){
                    CheckUsernameEmailResponse checkUsernameEmailResponse = response.body();
                    mCheckUsernameAndEmailView.checkUsername(checkUsernameEmailResponse);
                }
            }

            @Override
            public void onFailure(Call<CheckUsernameEmailResponse> call, Throwable t) {

            }
        });
    }

    public void checkEmail(String email)
    {
        mApiInterface.checkEmail(email).enqueue(new Callback<CheckUsernameEmailResponse>() {
            @Override
            public void onResponse(Call<CheckUsernameEmailResponse> call, Response<CheckUsernameEmailResponse> response) {
                if (response.isSuccessful()){
                    CheckUsernameEmailResponse checkUsernameEmailResponse = response.body();
                    mCheckUsernameAndEmailView.checkEmail(checkUsernameEmailResponse);
                }
            }

            @Override
            public void onFailure(Call<CheckUsernameEmailResponse> call, Throwable t) {

            }
        });
    }
}
