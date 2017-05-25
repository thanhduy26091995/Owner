package com.hbbsolution.owner.more.viet_pham.View.signin;

import com.hbbsolution.owner.more.viet_pham.Model.RegisterResponse;

/**
 * Created by Administrator on 5/25/2017.
 */

public interface SignInView {
    void displaySignUp(RegisterResponse registerResponse);
    void displayError();
}
