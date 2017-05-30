package com.hbbsolution.owner.maid_profile.choose_maid.view;

import com.hbbsolution.owner.model.TypeJobResponse;

/**
 * Created by buivu on 30/05/2017.
 */

public interface ChooseMaidView {
    void getAllTypeJob(TypeJobResponse typeJobResponse);

    void displayError(String error);
}
