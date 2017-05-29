package com.hbbsolution.owner.history;

import com.hbbsolution.owner.history.model.helper.Datum;

import java.util.List;

/**
 * Created by Administrator on 29/05/2017.
 */

public interface HelperHistoryView {
    void getInfoHelperHistory (List<Datum> datumList);
    void getInfoHelperHistoryFail ();
}
