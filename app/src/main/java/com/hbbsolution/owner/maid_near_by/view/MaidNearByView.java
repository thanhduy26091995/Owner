package com.hbbsolution.owner.maid_near_by.view;

import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

/**
 * Created by buivu on 18/05/2017.
 */

public interface MaidNearByView {
    void displayMaidNearBy(MaidNearByResponse maidNearByResponse);

    void displayError(String error);

    void displaySearchResult(MaidNearByResponse maidNearByResponse);

    void getLocationAddress(GeoCodeMapResponse geoCodeMapResponse);

    void displayNotFoundLocation(String error);
}
