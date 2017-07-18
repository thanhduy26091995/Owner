package com.hbbsolution.owner.history;

import com.hbbsolution.owner.base.ConnectionInterface;
import com.hbbsolution.owner.history.model.liabilities.LiabilitiesHistory;

import java.util.List;

/**
 * Created by Administrator on 05/06/2017.
 */

public interface LiabilitiesView extends ConnectionInterface{
    void getLiabilitiesSuccess(List<LiabilitiesHistory> dataLiabilities);
    void getLiabilitiesError();
}
