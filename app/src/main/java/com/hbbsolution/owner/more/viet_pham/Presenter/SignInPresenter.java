package com.hbbsolution.owner.more.viet_pham.Presenter;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.viet_pham.Model.BodyResponse;
import com.hbbsolution.owner.more.viet_pham.View.MoreView;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 5/25/2017.
 */

public class SignInPresenter {
    private MoreView mMoreView;
    private ApiInterface mApiService;

    public SignInPresenter(MoreView mMoreView) {
        this.mMoreView = mMoreView;
        mApiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void signIn(String username, String password) {
        RequestBody requestBodyUserName = RequestBody.create(MediaType.parse("text"), username);
        RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("text"), password);
        mApiService.signInAccount(requestBodyUserName, requestBodyPassword).enqueue(new Callback<BodyResponse>() {
            @Override
            public void onResponse(Call<BodyResponse> call, Response<BodyResponse> response) {
                try {
                    BodyResponse bodyResponse = response.body();
                    mMoreView.displaySignUpAndSignIn(bodyResponse);

                } catch (Exception e) {
                    mMoreView.displayError();
                }
            }

            @Override
            public void onFailure(Call<BodyResponse> call, Throwable t) {

            }
        });
    }
}
