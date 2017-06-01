package com.hbbsolution.owner.work_management.view.listmaid;

import com.hbbsolution.owner.work_management.model.maid.ListMaidResponse;

/**
 * Created by tantr on 5/19/2017.
 */

public interface ListMaidView {
    void getInfoListMaid(ListMaidResponse mListMaidRespose);
    void responseChosenMaid(boolean isResponseChosenMaid);
    void getError();
}
