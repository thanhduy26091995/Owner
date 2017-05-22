package com.hbbsolution.owner.history;

import com.hbbsolution.owner.history.model.Datum;

import java.util.List;

/**
 * Created by Administrator on 18/05/2017.
 */

public interface WorkHistoryView {
    void getInfoWorkHistory (List<Datum> listWorkHistory);
    void getError();
}
