package com.hbbsolution.owner.more.phuc_tran.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.more.phuc_tran.model.ForgotPassResponse;
import com.hbbsolution.owner.more.phuc_tran.presenter.ForgotPasswordPresenter;
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

        forgotPasswordPresenter = new ForgotPasswordPresenter(this);

        btn_send_require.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidMail(edt_email.getText().toString())) {

                    forgotPasswordPresenter.forgotPassword(edt_email.getText().toString(),edt_username.getText().toString());

                } else if (edt_username.getText().toString().equals("")) {
                    edt_username.setError(getResources().getString(R.string.usename_empty));
                } else if (edt_email.getText().toString().equals("")) {
                    edt_email.setError(getResources().getString(R.string.emai_empty));
                } else {
                    edt_email.setError(getResources().getString(R.string.email_wrong));
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


    //hàm check mail
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void getForgotPass(ForgotPassResponse forgotPassResponse) {

        if (forgotPassResponse.getStatus()) {
            ShowAlertDialog.showAlert(getResources().getString(R.string.forgot_password_success), ForgotPassActivity.this);
        }
        else {
            ShowAlertDialog.showAlert(getResources().getString(R.string.forgot_password_failed), ForgotPassActivity.this);
        }
    }

    @Override
    public void getErrorForgotPass(String error) {
        ShowAlertDialog.showAlert(error, ForgotPassActivity.this);
    }


}
