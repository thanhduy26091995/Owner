package com.hbbsolution.owner.history;

import com.hbbsolution.owner.history.model.Doc;

import java.util.List;

/**
 * Created by Administrator on 18/05/2017.
 */

public interface WorkHistoryView {
    void getInfoWorkHistory (List<Doc> listWorkHistory,int pages);
    void getMoreInfoWorkHistory (List<Doc> listWorkHistory);
    void getInfoWorkHistoryTime (List<Doc> listWorkHistory,String startAt,String endAt,int pages);
    void getMoreInfoWorkHistoryTime (List<Doc> listWorkHistory,String startAt,String endAt);
    void getError();
}
