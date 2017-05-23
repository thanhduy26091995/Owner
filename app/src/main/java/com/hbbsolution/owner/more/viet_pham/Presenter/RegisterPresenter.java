package com.hbbsolution.owner.more.viet_pham.Presenter;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.api.ApiInterface;
import com.hbbsolution.owner.more.viet_pham.View.SignUpView;

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


}
