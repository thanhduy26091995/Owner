package com.hbbsolution.owner.more.duy_nguyen;

import com.hbbsolution.owner.more.duy_nguyen.model.DataContact;

/**
 * Created by Administrator on 21/06/2017.
 */

public interface ContactView {
    void getContactSuccess(DataContact dataContact);
    void getContactFail();
}
