package com.hbbsolution.owner.history;

import com.hbbsolution.owner.history.model.workhistory.WorkHistory;

import java.util.List;

/**
 * Created by Administrator on 05/06/2017.
 */

public interface LiabilitiesView {
    void getLiabilitiesSuccess(List<WorkHistory> dataLiabilities);
    void getLiabilitiesError();
}
