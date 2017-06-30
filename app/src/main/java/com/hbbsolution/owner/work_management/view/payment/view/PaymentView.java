package com.hbbsolution.owner.work_management.view.payment.view;

import com.hbbsolution.owner.work_management.model.billGv24.BillGv24Response;

/**
 * Created by Administrator on 13/06/2017.
 */

public interface PaymentView {
    void getWalletSuccess(long wallet);
    void getWalletFail();
    void getInfoBill24h(BillGv24Response billGv24Response);
    void getErrorBill24h(String error);
    void getInfoPaymentBymoney(BillGv24Response billGv24Response);
    void getErrorPaymentBymoney(String error);

    void getInfoPaymentByOnline(BillGv24Response billGv24Response);


}
