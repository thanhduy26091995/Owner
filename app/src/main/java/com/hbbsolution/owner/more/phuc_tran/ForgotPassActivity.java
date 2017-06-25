package com.hbbsolution.owner.more.phuc_tran;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.more.phuc_tran.model.ForgotPassResponse;
import com.hbbsolution.owner.more.phuc_tran.presenter.ForgotPasswordPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPassActivity extends AppCompatActivity implements ForgotPassView{

    @BindView(R.id.forgot_toolbar)
    Toolbar toolbar;
    @BindView(R.id.forgotpass_title_toolbar)
    TextView txt_forgot_pass_toolbar;
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

    }

    @Override
    public void getErrorForgotPass(String error) {

    }
}
