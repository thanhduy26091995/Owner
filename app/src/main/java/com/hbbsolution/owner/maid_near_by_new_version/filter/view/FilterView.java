package com.hbbsolution.owner.maid_near_by_new_version.filter.view;

import com.hbbsolution.owner.base.ConnectionInterface;
import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.model.TypeJobResponse;

/**
 * Created by buivu on 23/05/2017.
 */

public interface FilterView extends ConnectionInterface {
    void getAllTypeJob(TypeJobResponse typeJobResponse);
    void displayError(String error);

    void filterMaid(MaidNearByResponse maidNearByResponse);
}
