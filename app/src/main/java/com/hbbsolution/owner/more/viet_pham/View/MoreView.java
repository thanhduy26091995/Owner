package com.hbbsolution.owner.more.viet_pham.View;

import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.BodyResponse;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.DataUpdateResponse;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

/**
 * Created by Administrator on 5/29/2017.
 */

public interface MoreView {
    void displaySignUpAndSignIn(BodyResponse bodyResponse);
    void displayUpdate(DataUpdateResponse dataUpdateResponse);
    void displayError();
    void displayNotFoundLocaltion();
    void getLocaltionAddress(GeoCodeMapResponse geoCodeMapResponse);
    void displaySignInGooAndFace(BodyResponse bodyResponse);
}
