package com.hbbsolution.owner.maid_near_by.model;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;

/**
 * Created by buivu on 18/05/2017.
 */

public class InfoWindowRefresh implements Callback {

    private Marker mMarkerToRefresh;

    public InfoWindowRefresh(Marker mMarkerToRefresh) {
        this.mMarkerToRefresh = mMarkerToRefresh;
    }

    @Override
    public void onSuccess() {
        mMarkerToRefresh.showInfoWindow();
    }

    @Override
    public void onError() {

    }
}
