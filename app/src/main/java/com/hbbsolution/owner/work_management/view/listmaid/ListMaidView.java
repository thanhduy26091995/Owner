package com.hbbsolution.owner.work_management.view.listmaid;

import com.hbbsolution.owner.base.ConnectionInterface;
import com.hbbsolution.owner.work_management.model.jobpost.JobPostResponse;
import com.hbbsolution.owner.work_management.model.maid.ListMaidResponse;

/**
 * Created by tantr on 5/19/2017.
 */

public interface ListMaidView extends ConnectionInterface{
    void getInfoListMaid(ListMaidResponse mListMaidRespose);
    void responseChosenMaid(JobPostResponse mJobPostResponse);
    void getError();
}
