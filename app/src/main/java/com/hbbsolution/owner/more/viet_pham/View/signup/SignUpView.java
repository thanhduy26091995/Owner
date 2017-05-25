package com.hbbsolution.owner.more.viet_pham.View.signup;

import com.hbbsolution.owner.more.viet_pham.Model.RegisterResponse;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

/**
 * Created by Administrator on 5/22/2017.
 */

public interface SignUpView {
    void displaySignUp(RegisterResponse registerResponse);
    void displayError();
    void displayNotFoundLocaltion();
    void getLocaltionAddress(GeoCodeMapResponse geoCodeMapResponse);
}
