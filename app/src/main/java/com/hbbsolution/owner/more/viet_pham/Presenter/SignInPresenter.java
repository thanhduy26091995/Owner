package com.hbbsolution.owner.more.viet_pham.Presenter;

import android.util.Log;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.viet_pham.Model.RegisterResponse;
import com.hbbsolution.owner.more.viet_pham.View.signin.SignInView;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 5/25/2017.
 */

public class SignInPresenter {
    private SignInView mSignInView;
    private ApiInterface mApiService;

    public SignInPresenter(SignInView mSignInView) {
        this.mSignInView = mSignInView;
        mApiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void signIn(String username, String password) {
        RequestBody requestBodyUserName = RequestBody.create(MediaType.parse("text"), username);
        RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("text"), password);
        mApiService.signInAccount(requestBodyUserName,requestBodyPassword).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful())
                {
                    try {
                        RegisterResponse registerResponse = response.body();
                        mSignInView.displaySignUp(registerResponse);
                        Log.e("Tag1",response.body().getStatus());
                    }catch (Exception e)
                    {
                        Log.e("exception", e.toString());
                        mSignInView.displayError();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("Tag",t.toString());
            }
        });
    }
}
