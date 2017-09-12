package com.hbbsolution.owner.work_management.view.detail;

import com.hbbsolution.owner.base.ConnectionInterface;
import com.hbbsolution.owner.model.CheckInResponse;
import com.hbbsolution.owner.work_management.view.listmaid.ListMaidView;

/**
 * Created by tantr on 5/26/2017.
 */

public interface DetailJobPostView extends ConnectionInterface, ListMaidView {
    void displayNotifyJobPost(boolean isJobPost);

    void displayError(String error);

    void checkIn(CheckInResponse checkInResponse);

    void checkInFail(String error);


}
