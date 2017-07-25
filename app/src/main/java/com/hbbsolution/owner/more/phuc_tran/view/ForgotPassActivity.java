package com.hbbsolution.owner.more.phuc_tran.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.more.phuc_tran.model.ForgotPassResponse;
import com.hbbsolution.owner.more.phuc_tran.presenter.ForgotPasswordPresenter;
import com.hbbsolution.owner.utils.EmailValidate;
import com.hbbsolution.owner.utils.ShowAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPassActivity extends AppCompatActivity implements ForgotPassView {

    @BindView(R.id.forgot_toolbar)
    Toolbar toolbar;
    @BindView(R.id.forgotpass_title_toolbar)
    TextView txt_forgot_pass_toolbar;
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.btn_send_require)
    Button btn_send_require;

    private ProgressDialog mProgressDialog;
    ForgotPasswordPresenter forgotPasswordPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        ButterKnife.bind(this);

        //config toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_forgot_pass_toolbar.setText(getResources().getString(R.string.forgot_password));

        mProgressDialog = new ProgressDialog(this);
        forgotPasswordPresenter = new ForgotPasswordPresenter(this);

        btn_send_require.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmailValidate.IsOk(edt_email.getText().toString())) {

                    forgotPasswordPresenter.forgotPassword(edt_email.getText().toString(), edt_username.getText().toString());
                    showProgress();

                } else if (edt_username.getText().toString().equals("")) {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.usename_empty), ForgotPassActivity.this);
                } else if (edt_email.getText().toString().equals("")) {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.emai_empty), ForgotPassActivity.this);
                } else {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.email_wrong), ForgotPassActivity.this);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void getForgotPass(ForgotPassResponse forgotPassResponse) {
        hideProgress();
        if (forgotPassResponse.getStatus()) {
            ShowAlertDialog.showAlert(getResources().getString(R.string.forgot_password_success), ForgotPassActivity.this);
        } else {
            ShowAlertDialog.showAlert(getResources().getString(R.string.forgot_password_failed), ForgotPassActivity.this);
        }
    }

    @Override
    public void getErrorForgotPass(String error) {
        hideProgress();
        ShowAlertDialog.showAlert(error, ForgotPassActivity.this);
    }

    private void showProgress() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.loading));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}
