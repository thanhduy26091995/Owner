package com.hbbsolution.owner.history;

import com.hbbsolution.owner.history.model.workhistory.WorkHistory;

import java.util.List;

/**
 * Created by Administrator on 08/06/2017.
 */

public interface ListWorkView {
    void getInfoListWorkHistory (List<WorkHistory> listWorkHistory, int pages);
    void getMoreInfoListWorkHistory (List<WorkHistory> listWorkHistory);
    void getError();
}