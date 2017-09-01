package com.hbbsolution.owner.paymentonline.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.more.duy_nguyen.RechargeOnlineFiView;
import com.hbbsolution.owner.more.duy_nguyen.presenter.RechargeOnlineFiPresenter;
import com.hbbsolution.owner.paymentonline.api.SendOrderRequest;
import com.hbbsolution.owner.paymentonline.bean.SendOrderBean;
import com.hbbsolution.owner.utils.Commons;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.rey.material.widget.ProgressView;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;


public class PaymentOnlineActivity extends AppCompatActivity implements View.OnClickListener, SendOrderRequest.SendOrderRequestOnResult, RechargeOnlineFiView {

    private Toolbar toolbar;
    private EditText editFullName;
    private EditText editAmount;
    private EditText editEmail;
    private EditText editPhoneNumber;
    private EditText editAddress;
    private Button btnSendOrder;
    private ScrollView scrollView;
    private ProgressView progressView;
    private String idBillOrder = "";
    public static Activity mPaymentOnlineActivity;
    private RechargeOnlineFiPresenter rechargeOnlineFiPresenter;
    private String fullName, amount, email, phoneNumber, address;
    private String key = "";
    private boolean recharge = false;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashDataUser = new HashMap<>();
    private TextView titleTongSoTien;
    private Bundle infoMaid;
    private TextView recruitment_title_toothbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_online);
        mPaymentOnlineActivity = this;
        initView();
        recharge = getIntent().getBooleanExtra("recharge", false);
        infoMaid = getIntent().getBundleExtra("mbundleComment");
        rechargeOnlineFiPresenter = new RechargeOnlineFiPresenter(this);
        if (recharge) {
            recruitment_title_toothbar.setText(getResources().getString(R.string.naptientructuyen));
            editAmount.setEnabled(true);
            titleTongSoTien.setText(getResources().getString(R.string.sotiennaptaikhoan));
            editAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        editAmount.removeTextChangedListener(this);
                        String titleString = editAmount.getText().toString().replace(".","");
                        editAmount.setText(NumberFormat.getNumberInstance(Locale.GERMANY).format(Long.parseLong(titleString)));
                        editAmount.setSelection(editAmount.getText().toString().length());
                        editAmount.addTextChangedListener(this);
                    }
                    catch (Exception e){
                        editAmount.addTextChangedListener(this);
                    }
                }
            });
        }
        else if (infoMaid != null) {
            editAmount.setEnabled(false);
            idBillOrder = infoMaid.getString("idBillOrder", "");
            editAmount.setText(NumberFormat.getNumberInstance(Locale.GERMANY).format(infoMaid.getInt("total", 0)));
        }
        setData();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_payment);
        editFullName = (EditText) findViewById(R.id.activity_main_editFullName);
        editAmount = (EditText) findViewById(R.id.activity_main_editAmount);
        editEmail = (EditText) findViewById(R.id.activity_main_editEmail);
        editPhoneNumber = (EditText) findViewById(R.id.activity_main_editPhoneNumber);
        editAddress = (EditText) findViewById(R.id.activity_main_editAddress);
        btnSendOrder = (Button) findViewById(R.id.activity_main_btnSendOrder);

        scrollView = (ScrollView) findViewById(R.id.activity_main_scrollView);
        progressView = (ProgressView) findViewById(R.id.activity_main_progressView);
        titleTongSoTien = (TextView) findViewById(R.id.titleTongSoTien);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recruitment_title_toothbar = (TextView)findViewById(R.id.recruitment_title_toothbar);

        if (!Commons.checkInternetConnection(getApplicationContext())) {
            showErrorDialog(getString(R.string.error_disconnect), true);
        }

        btnSendOrder.setOnClickListener(this);
    }

    public void setData() {
        sessionManagerUser = new SessionManagerUser(this);
        hashDataUser = sessionManagerUser.getUserDetails();
        editFullName.setText(hashDataUser.get(SessionManagerUser.KEY_NAME));
        editAddress.setText(hashDataUser.get(SessionManagerUser.KEY_ADDRESS));
        editEmail.setText(hashDataUser.get(SessionManagerUser.KEY_EMAIL));
        editPhoneNumber.setText(hashDataUser.get(SessionManagerUser.KEY_PHONE));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.activity_main_btnSendOrder:
                fullName = editFullName.getText().toString();
                amount = editAmount.getText().toString().replace(".","");
                email = editEmail.getText().toString();
                phoneNumber = editPhoneNumber.getText().toString();
                address = editAddress.getText().toString();

                if (!fullName.equalsIgnoreCase("")) {
                    if (!amount.equalsIgnoreCase("") && Integer.valueOf(amount) >= 2000) {
                        if (!email.equalsIgnoreCase("")) {
                            if (!phoneNumber.equalsIgnoreCase("")) {
                                if (!address.equalsIgnoreCase("")) {
                                    if (recharge) {
                                        rechargeOnlineFiPresenter.getRechargeOnlineFi(Double.parseDouble(amount));
                                    } else {
                                        sendOrderObject(fullName, amount, email, phoneNumber, address);
                                    }
                                } else {
                                    showErrorDialog(getString(R.string.error_address), false);
                                }
                            } else {
                                showErrorDialog(getString(R.string.error_mobile), false);
                            }
                        } else {
                            showErrorDialog(getString(R.string.error_email), false);
                        }
                    } else {
                        ShowAlertDialog.showAlert(getString(R.string.error_amount), PaymentOnlineActivity.this);
//                        showErrorDialog(getString(R.string.error_amount), false);
                    }
                } else {
                    showErrorDialog(getString(R.string.error_name_order), false);
                }

                break;
        }
    }

    private void sendOrderObject(String fullName, String amount, String email, String phoneNumber, String address) {
        SendOrderBean sendOrderBean = new SendOrderBean();
        sendOrderBean.setFunc("sendOrder");
        sendOrderBean.setVersion("1.0");
        sendOrderBean.setMerchantID(Constants.MERCHANT_ID);
        sendOrderBean.setMerchantAccount("info@hbbsolution.com");
        sendOrderBean.setOrderCode(idBillOrder);
        sendOrderBean.setTotalAmount(Integer.valueOf(amount));
        sendOrderBean.setCurrency("vnd");
        sendOrderBean.setLanguage("vi");
        sendOrderBean.setReturnUrl(Constants.RETURN_URL);
        sendOrderBean.setCancelUrl(Constants.CANCEL_URL);
        sendOrderBean.setNotifyUrl(Constants.NOTIFY_URL);
        sendOrderBean.setBuyerFullName(fullName);
        sendOrderBean.setBuyerEmail(email);
        sendOrderBean.setBuyerMobile(phoneNumber);
        sendOrderBean.setBuyerAddress(address);

        String checksum = getChecksum(sendOrderBean);
        sendOrderBean.setChecksum(checksum);

        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.execute(getApplicationContext(), sendOrderBean);
        sendOrderRequest.getSendOrderRequestOnResult(this);
        scrollView.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);
    }

    private String getChecksum(SendOrderBean sendOrderBean) {
        String stringSendOrder = sendOrderBean.getFunc() + "|" +
                sendOrderBean.getVersion() + "|" +
                sendOrderBean.getMerchantID() + "|" +
                sendOrderBean.getMerchantAccount() + "|" +
                sendOrderBean.getOrderCode() + "|" +
                sendOrderBean.getTotalAmount() + "|" +
                sendOrderBean.getCurrency() + "|" +
                sendOrderBean.getLanguage() + "|" +
                sendOrderBean.getReturnUrl() + "|" +
                sendOrderBean.getCancelUrl() + "|" +
                sendOrderBean.getNotifyUrl() + "|" +
                sendOrderBean.getBuyerFullName() + "|" +
                sendOrderBean.getBuyerEmail() + "|" +
                sendOrderBean.getBuyerMobile() + "|" +
                sendOrderBean.getBuyerAddress() + "|" +
                Constants.MERCHANT_PASSWORD;
        String checksum = Commons.md5(stringSendOrder);

        return checksum;
    }

    @Override
    public void onSendOrderRequestOnResult(boolean result, String data) {
        if (result == true) {
            try {
                JSONObject objResult = new JSONObject(data);
                String responseCode = objResult.getString("response_code");
                if (responseCode.equalsIgnoreCase("00")) {
                    String tokenCode = objResult.getString("token_code");
                    String checkoutUrl = objResult.getString("checkout_url");

                    Intent intentCheckout = new Intent(getApplicationContext(), CheckOutActivity.class);
                    intentCheckout.putExtra(CheckOutActivity.TOKEN_CODE, tokenCode);
                    intentCheckout.putExtra(CheckOutActivity.CHECKOUT_URL, checkoutUrl);
                    intentCheckout.putExtra("idOderBill", idBillOrder);
                    intentCheckout.putExtra("infoMaid", infoMaid );
                    intentCheckout.putExtra("key", key);
                    intentCheckout.putExtra("amount",amount);
                    startActivity(intentCheckout);
                    finish();
                } else {
                    scrollView.setVisibility(View.VISIBLE);
                    progressView.setVisibility(View.GONE);
                    showErrorDialog(Commons.getCodeError(getApplicationContext(), responseCode), false);
                }
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }
    }

    private void showErrorDialog(String message, final boolean isExit) {
        final Dialog mSuccessDialog = new Dialog(PaymentOnlineActivity.this);
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
    public void fiSuccess(String billId, String keyNumber) {
        idBillOrder = billId;
        key = keyNumber;
        sendOrderObject(fullName, amount, email, phoneNumber, address);
    }

    @Override
    public void fiFail() {
        ShowAlertDialog.showAlert(getResources().getString(R.string.error), PaymentOnlineActivity.this);
    }
}
