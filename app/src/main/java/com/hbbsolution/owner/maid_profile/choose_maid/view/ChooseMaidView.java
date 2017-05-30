package com.hbbsolution.owner.maid_profile.choose_maid.view;

import com.hbbsolution.owner.maid_profile.choose_maid.model.SendRequestResponse;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

/**
 * Created by buivu on 30/05/2017.
 */

public interface ChooseMaidView {
    void getAllTypeJob(TypeJobResponse typeJobResponse);

    void displayError(String error);

    void getLocationAddress(GeoCodeMapResponse geoCodeMapResponse);

    void displayNotFoundLocation(String error);

    void sendRequestJob(SendRequestResponse sendRequestResponse);
}
