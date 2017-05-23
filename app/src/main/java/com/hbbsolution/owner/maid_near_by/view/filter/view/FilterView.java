package com.hbbsolution.owner.maid_near_by.view.filter.view;

import com.hbbsolution.owner.model.TypeJobResponse;

/**
 * Created by buivu on 23/05/2017.
 */

public interface FilterView {
    void getAllTypeJob(TypeJobResponse typeJobResponse);

    void displayError(String error);
}
