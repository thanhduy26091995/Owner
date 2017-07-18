package com.hbbsolution.owner.report;

import com.hbbsolution.owner.base.ConnectionInterface;

/**
 * Created by Administrator on 30/05/2017.
 */

public interface ReportView extends ConnectionInterface{
    void reportSuccess(String message);
    void reportFail();
}
