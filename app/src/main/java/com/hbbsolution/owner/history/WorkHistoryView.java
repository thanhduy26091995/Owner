package com.hbbsolution.owner.history;

import com.hbbsolution.owner.history.model.workhistory.WorkHistory;

import java.util.List;

/**
 * Created by Administrator on 18/05/2017.
 */

public interface WorkHistoryView {
    void getInfoWorkHistory (List<WorkHistory> listWorkHistory, int pages);
    void getMoreInfoWorkHistory (List<WorkHistory> listWorkHistory);
    void getInfoWorkHistoryTime (List<WorkHistory> listWorkHistory, String startAt, String endAt, int pages);
    void getMoreInfoWorkHistoryTime (List<WorkHistory> listWorkHistory, String startAt, String endAt);
    void getError();
}
