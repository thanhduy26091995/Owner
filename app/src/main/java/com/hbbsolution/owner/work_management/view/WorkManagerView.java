package com.hbbsolution.owner.work_management.view;

import com.hbbsolution.owner.work_management.model.WorkManagerResponse;

/**
 * Created by tantr on 5/10/2017.
 */

public interface WorkManagerView {
    void getInfoJob (WorkManagerResponse mExample);
    void getError();
}
