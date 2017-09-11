package com.hbbsolution.owner.maid_near_by_new_version.view;

import com.hbbsolution.owner.model.MaidNearByResponse;

/**
 * Created by buivu on 08/09/2017.
 */

public interface MaidNearByNewView {
    void displayMaidNearBy(MaidNearByResponse maidNearByResponse);

    void displayError(String error);

    void connectServerFail();
}
