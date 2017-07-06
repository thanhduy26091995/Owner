package com.hbbsolution.owner.paymentonline.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.BaseActivity;
import com.hbbsolution.owner.history.view.CommentActivity;
import com.hbbsolution.owner.more.duy_nguyen.RechargeActivity;
import com.hbbsolution.owner.more.duy_nguyen.RechargeOnlineSecView;
import com.hbbsolution.owner.more.duy_nguyen.RechargeOnlineThiView;
import com.hbbsolution.owner.more.duy_nguyen.presenter.RechargeOnlineSecPresenter;
import com.hbbsolution.owner.more.duy_nguyen.presenter.RechargeOnlineThiPresenter;
import com.hbbsolution.owner.paymentonline.api.CheckOrderPresenter;
import com.hbbsolution.owner.paymentonline.api.CheckOrderRequest;
import com.hbbsolution.owner.paymentonline.bean.CheckOrderBean;
import com.hbbsolution.owner.utils.Commons;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.view.detail.DetailJobDoingActivity;
import com.hbbsolution.owner.work_management.view.payment.view.PaymentActivity;
import com.rey.material.app.Dialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;


/**
 * Created by DucChinh on 6/14/2016.
 */
public class CheckOrderActivity extends BaseActivity implements CheckOrderRequest.CheckOrderRequestOnResult, CheckOrderView, RechargeOnlineSecView, RechargeOnlineThiView {

    public static final String TOKEN_CODE = "token_code";

    //    private TextView txtData;
    private TextView txtFullName, txtAmount, txtEmail, txtPhoneNumber, txttAddress;
    private ProgressView mProgressView;

    private String mTokenCode = "";
    private String idBillOrder = "";
    private CheckOrderPresenter checkOrderPresenter;
    private Button btnCheckOrderOk;
    private Activity mCheckOrderActivity;
    private String key = "";
    private RechargeOnlineSecPresenter rechargeOnlineSecPresenter;
    private RechargeOnlineThiPresenter rechargeOnlineThiPresenter;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashDataUser = new HashMap<>();
    private Bundle infoMaid;
    private String amount;
    private TextView checkInfoPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_order_activity);
        mCheckOrderActivity = this;
        sessionManagerUser = new SessionManagerUser(this);
        hashDataUser = sessionManagerUser.getUserDetails();
        Bundle extras = getIntent().getExtras();
        infoMaid = getIntent().getBundleExtra("infoMaid");
        if (extras != null) {
            mTokenCode = extras.getString(TOKEN_CODE, "");
            idBillOrder = extras.getString("idBillOrder", "");
            key = extras.getString("key", "");
            amount = extras.getString("amount","");
        }
        checkOrderPresenter = new CheckOrderPresenter(this);
        rechargeOnlineSecPresenter = new RechargeOnlineSecPresenter(this);
        rechargeOnlineThiPresenter = new RechargeOnlineThiPresenter(this);
        initView();
        if (key.equals("")) {
            checkOrderPresenter.getInfoPaymnetByOnline(idBillOrder);
        } else {
            rechargeOnlineSecPresenter.getRechargeOnlineSec(key, idBillOrder);
            checkInfoPayment.setVisibility(View.GONE);
        }
    }

    private void initView() {
        btnCheckOrderOk = (Button) findViewById(R.id.activity_main_btnSendOrder);
        txtFullName = (TextView) findViewById(R.id.activity_main_editFullName);
        txtAmount = (TextView) findViewById(R.id.activity_main_editAmount);
        txtEmail = (TextView) findViewById(R.id.activity_main_editEmail);
        txtPhoneNumber = (TextView) findViewById(R.id.activity_main_editPhoneNumber);
        txttAddress = (TextView) findViewById(R.id.activity_main_editAddress);
        mProgressView = (ProgressView) findViewById(R.id.activity_main_progressView);
        checkInfoPayment = (TextView) findViewById(R.id.checkInfoPayment);
        checkOrderObject();

        btnCheckOrderOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCheckOrderActivity != null) {
                    mCheckOrderActivity.finish();
                }
                try {
                    if (PaymentOnlineActivity.mPaymentOnlineActivity != null) {
                        PaymentOnlineActivity.mPaymentOnlineActivity.finish();
                    }
                    if (PaymentActivity.mPaymentActivity != null) {
                        PaymentActivity.mPaymentActivity.finish();
                    }
                    if (DetailJobDoingActivity.mDetailJobDoingActivity != null) {
                        DetailJobDoingActivity.mDetailJobDoingActivity.finish();
                    }
//                    if (WorkManagementActivity.mWorkManagementActivity != null) {
//                        WorkManagementActivity.mWorkManagementActivity.finish();
//                    }
                } catch (Exception e) {

                }
                if(key.equals("")) {
                    Intent itCommnet = new Intent(CheckOrderActivity.this, CommentActivity.class);
                    itCommnet.putExtra("infoMaid", infoMaid);
                    startActivity(itCommnet);
                }
                else
                {
                    try{
                        if(RechargeActivity.mRechargeActivity!=null)
                        {
                            RechargeActivity.mRechargeActivity.finish();
                        }
                    }
                    catch (Exception e){}
                }
            }
        });
    }

    private void getInfoBill() {
        txtFullName.setText(hashDataUser.get(SessionManagerUser.KEY_NAME));
        txtEmail.setText(hashDataUser.get(SessionManagerUser.KEY_EMAIL));
        txtPhoneNumber.setText(hashDataUser.get(SessionManagerUser.KEY_PHONE));
        txttAddress.setText(hashDataUser.get(SessionManagerUser.KEY_ADDRESS));
        if(infoMaid != null){
            txtAmount.setText(NumberFormat.getNumberInstance(Locale.GERMANY).format(infoMaid.getInt("total", 0))+ " VND");
        }else {
            txtAmount.setText(NumberFormat.getNumberInstance(Locale.GERMANY).format(Integer.parseInt(amount))+ " VND");
        }
    }
    private void checkOrderObject() {
        CheckOrderBean checkOrderBean = new CheckOrderBean();
        checkOrderBean.setFunc("checkOrder");
        checkOrderBean.setVersion("1.0");
        checkOrderBean.setMerchantID(Constants.MERCHANT_ID);
        checkOrderBean.setTokenCode(mTokenCode);

        String checksum = getChecksum(checkOrderBean);
        checkOrderBean.setChecksum(checksum);

        CheckOrderRequest checkOrderRequest = new CheckOrderRequest();
        checkOrderRequest.execute(getApplicationContext(), checkOrderBean);
        checkOrderRequest.getCheckOrderRequestOnResult(this);
        mProgressView.setVisibility(View.VISIBLE);
    }

    private String getChecksum(CheckOrderBean checkOrderBean) {
        String stringSendOrder = checkOrderBean.getFunc() + "|" +
                checkOrderBean.getVersion() + "|" +
                checkOrderBean.getMerchantID() + "|" +
                checkOrderBean.getTokenCode() + "|" +
                Constants.MERCHANT_PASSWORD;
        String checksum = Commons.md5(stringSendOrder);

        return checksum;
    }

    @Override
    public void onBackPressed() {
        if (mCheckOrderActivity != null) {
            mCheckOrderActivity.finish();
        }
        try {
            if (PaymentOnlineActivity.mPaymentOnlineActivity != null) {
                PaymentOnlineActivity.mPaymentOnlineActivity.finish();
            }
            if (PaymentActivity.mPaymentActivity != null) {
                PaymentActivity.mPaymentActivity.finish();
            }
            if (DetailJobDoingActivity.mDetailJobDoingActivity != null) {
                DetailJobDoingActivity.mDetailJobDoingActivity.finish();
            }
//                    if (WorkManagementActivity.mWorkManagementActivity != null) {
//                        WorkManagementActivity.mWorkManagementActivity.finish();
//                    }
        } catch (Exception e) {

        }
        if(key.equals("")) {
            Intent itCommnet = new Intent(CheckOrderActivity.this, CommentActivity.class);
            itCommnet.putExtra("infoMaid", infoMaid);
            startActivity(itCommnet);
        }
        else
        {
            try{
                if(RechargeActivity.mRechargeActivity!=null)
                {
                    RechargeActivity.mRechargeActivity.finish();
                }
            }
            catch (Exception e){}
        }
    }

    @Override
    public void onCheckOrderRequestOnResult(boolean result, String data) {

        if (result == true) {
            try {
                JSONObject objResult = new JSONObject(data);
                String responseCode = objResult.getString("response_code");
                if (responseCode.equalsIgnoreCase("00")) {
                    String response_code = objResult.getString("response_code");
                    String receiver_email = objResult.getString("receiver_email");
                    String order_code = objResult.getString("order_code");
                    int total_amount = objResult.getInt("total_amount");
                    String currency = objResult.getString("currency");
                    String language = objResult.getString("language");
                    String return_url = objResult.getString("return_url");
                    String cancel_url = objResult.getString("cancel_url");
                    String notify_url = objResult.getString("notify_url");
                    String buyer_full_name = objResult.getString("buyer_fullname");
                    String buyer_email = objResult.getString("buyer_email");
                    String buyer_mobile = objResult.getString("buyer_mobile");
                    String buyer_address = objResult.getString("buyer_address");
                    int transaction_id = objResult.getInt("transaction_id");
                    int transaction_status = objResult.getInt("transaction_status");
                    int transaction_amount = objResult.getInt("transaction_amount");
                    String transaction_currency = objResult.getString("transaction_currency");
                    int transaction_escrow = objResult.getInt("transaction_escrow");

                    String dataCheckOrder =
                            "response_code:  " + response_code + "\n\n" +
                                    "receiver_email:  " + receiver_email + "\n\n" +
                                    "order_code:  " + order_code + "\n\n" +
                                    "total_amount:  " + total_amount + "\n\n" +
                                    "currency:  " + currency + "\n\n" +
                                    "language:  " + language + "\n\n" +
                                    "return_url:  " + return_url + "\n\n" +
                                    "cancel_url:  " + cancel_url + "\n\n" +
                                    "notify_url:  " + notify_url + "\n\n" +
                                    "buyer_full_name:  " + buyer_full_name + "\n\n" +
                                    "buyer_email:  " + buyer_email + "\n\n" +
                                    "buyer_mobile:  " + buyer_mobile + "\n\n" +
                                    "buyer_address:  " + buyer_address + "\n\n" +
                                    "transaction_id:  " + transaction_id + "\n\n" +
                                    "transaction_status:  " + transaction_status + "\n\n" +
                                    "transaction_amount:  " + transaction_amount + "\n\n" +
                                    "transaction_currency:  " + transaction_currency + "\n\n" +
                                    "transaction_escrow:  " + transaction_escrow + "\n\n";

                    getInfoBill();
//                    mProgressView.setVisibility(View.GONE);
                } else {
                    mProgressView.setVisibility(View.GONE);
                    showErrorDialog(Commons.getCodeError(getApplicationContext(), responseCode), false);
                }
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }
    }

    private void showErrorDialog(String message, final boolean isExit) {
        final Dialog mSuccessDialog = new Dialog(CheckOrderActivity.this);
        mSuccessDialog.setContentView(R.layout.dialog_success);
        mSuccessDialog.setCancelable(false);
        mSuccessDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mSuccessDialog.getWindow().setGravity(Gravity.CENTER);

        TextView txtContent = (TextView) mSuccessDialog.findViewById(R.id.dialog_success_txtContent);
        txtContent.setText(message);
        Button btnClose = (Button) mSuccessDialog.findViewById(R.id.dialog_success_btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSuccessDialog.dismiss();
                if (isExit) {
                    finish();
                }
            }
        });

        mSuccessDialog.show();
    }

    @Override
    public void checkOrderServerSuccess() {

        mProgressView.setVisibility(View.GONE);
//        Toast.makeText(CheckOrderActivity.this, "Thành công rồi đó", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getErrorPaymentBymoney(String error) {
        mProgressView.setVisibility(View.GONE);
    }

    @Override
    public void secSuccess(String keyNumber) {

        rechargeOnlineThiPresenter.getRechargeOnlineThi(keyNumber, idBillOrder);
    }

    @Override
    public void secFail() {
        ShowAlertDialog.showAlert(getResources().getString(R.string.error), CheckOrderActivity.this);
    }

    @Override
    public void thiSuccess() {
        ShowAlertDialog.showAlert(getResources().getString(R.string.naptienthanhcong), CheckOrderActivity.this);
        mProgressView.setVisibility(View.GONE);
//        txtData.setVisibility(View.VISIBLE);
        getInfoBill();
    }

    @Override
    public void thiFail() {
        ShowAlertDialog.showAlert(getResources().getString(R.string.error), CheckOrderActivity.this);
        mProgressView.setVisibility(View.GONE);
//        txtData.setVisibility(View.VISIBLE);
        getInfoBill();
    }


}
