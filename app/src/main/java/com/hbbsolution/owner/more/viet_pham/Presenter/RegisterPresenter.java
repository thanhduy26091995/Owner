package com.hbbsolution.owner.more.viet_pham.Presenter;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.viet_pham.Model.RegisterResponse;
import com.hbbsolution.owner.more.viet_pham.View.SignUpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 5/22/2017.
 */

public class RegisterPresenter {
    private SignUpView mSignUpView;
    private ApiInterface mApiService;

    public RegisterPresenter(SignUpView mSignUpView) {
        this.mSignUpView = mSignUpView;
        mApiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void createAccount(String username, String password, String email, String phone, String image, String adressName, double lat, double lng, int gender) {
        mApiService.createAccount(username,password,email,phone,image,adressName,lat,lng,gender).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }
}
