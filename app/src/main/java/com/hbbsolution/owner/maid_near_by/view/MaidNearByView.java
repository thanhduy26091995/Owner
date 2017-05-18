package com.hbbsolution.owner.maid_near_by.view;

import com.hbbsolution.owner.model.MaidNearByResponse;

/**
 * Created by buivu on 18/05/2017.
 */

public interface MaidNearByView {
    void displayMaidNearBy(MaidNearByResponse maidNearByResponse);

    void displayError(String error);
}
