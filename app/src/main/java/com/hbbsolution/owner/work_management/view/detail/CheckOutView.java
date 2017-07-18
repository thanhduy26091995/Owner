package com.hbbsolution.owner.work_management.view.detail;

import com.hbbsolution.owner.base.ConnectionInterface;
import com.hbbsolution.owner.work_management.model.chekout.CheckOutResponse;

/**
 * Created by tantr on 6/21/2017.
 */

public interface CheckOutView extends ConnectionInterface{
    void getInfoCheckOut(CheckOutResponse checkOutResponse);
    void getErrorCheckOut(String error);

}
