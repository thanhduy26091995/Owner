package com.hbbsolution.owner.work_management.view.detail;

import com.hbbsolution.owner.model.CheckInResponse;

/**
 * Created by tantr on 5/26/2017.
 */

public interface DetailJobPostView {
    void displayNotifyJobPost(boolean isJobPost);
    void displayError(String error);

    void checkIn(CheckInResponse checkInResponse);

    void checkInFail(String error);
}
