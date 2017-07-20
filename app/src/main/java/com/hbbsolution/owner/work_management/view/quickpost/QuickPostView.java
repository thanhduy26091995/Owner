package com.hbbsolution.owner.work_management.view.quickpost;

import com.hbbsolution.owner.base.ConnectionInterface;
import com.hbbsolution.owner.model.TypeJobResponse;

/**
 * Created by Administrator on 19/07/2017.
 */

public interface QuickPostView extends ConnectionInterface {
    void getAllTypeJob(TypeJobResponse typeJobResponse);
    void displayError(String error);
}
