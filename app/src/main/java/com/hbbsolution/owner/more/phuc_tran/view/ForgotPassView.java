package com.hbbsolution.owner.more.phuc_tran.view;

import com.hbbsolution.owner.more.phuc_tran.model.ForgotPassResponse;

/**
 * Created by tantr on 6/25/2017.
 */

public interface ForgotPassView {
    void getForgotPass(ForgotPassResponse forgotPassResponse);
    void getErrorForgotPass(String error);
}
