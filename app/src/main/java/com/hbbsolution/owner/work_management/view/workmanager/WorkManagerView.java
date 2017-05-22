package com.hbbsolution.owner.work_management.view.workmanager;

import com.hbbsolution.owner.work_management.model.workmanager.WorkManagerResponse;

/**
 * Created by tantr on 5/10/2017.
 */

public interface WorkManagerView {
    void getInfoJob (WorkManagerResponse mExample);
    void getError();
}
