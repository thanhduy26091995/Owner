package com.hbbsolution.owner.more.duy_nguyen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.more.viet_pham.Model.RegisterResponse;
import com.hbbsolution.owner.more.viet_pham.Presenter.RegisterPresenter;
import com.hbbsolution.owner.more.viet_pham.View.signup.SignUpView;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 04/05/2017.
 */

public class TermsActivity extends AppCompatActivity implements SignUpView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.terms_title_toothbar)
    TextView txtTerms_title_toothbar;
    @BindView(R.id.terms_btn_OK)
    Button btnOK;
    private RegisterPresenter mRegisterPresenter;
    private double mLat;
    private double mLng;
    private String mUserName,mPassword,mEmail,mFullName,mPhoneName,mFilePath,mFileContentResolver,mLocation;
    private int mGender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        ButterKnife.bind(this);
        addEvents();
        //config toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTerms_title_toothbar.setText(getResources().getString(R.string.terms));


        Intent iTerms = getIntent();
        Bundle bTerms = iTerms.getBundleExtra("bSignUp2");
        mUserName = bTerms.getString("username");
        mPassword = bTerms.getString("password");
        mEmail = bTerms.getString("email");
        mFullName = bTerms.getString("fullname");
        mGender = bTerms.getInt("gender");
        mPhoneName = bTerms.getString("phone");
        mLocation = bTerms.getString("location");
        mLat = bTerms.getDouble("lat");
        mLng = bTerms.getDouble("lng");
        mFilePath = bTerms.getString("filepath");
        mFileContentResolver = bTerms.getString("filecontent");

        mRegisterPresenter = new RegisterPresenter(this);
        mRegisterPresenter.getLocaltionAddress(mLocation);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addEvents() {
        // Event register account
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegisterPresenter.createAccount(mUserName, mPassword, mEmail, mPhoneName, mFullName, mFilePath, mLocation, mLat, mLng, mGender, mFileContentResolver);

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void displaySignUp(RegisterResponse registerResponse) {
        if (registerResponse.getStatus().equals("true"))
        {
            ShowAlertDialog.showAlert("Đăng ký thành công",TermsActivity.this);
        }

    }

    @Override
    public void displayError() {

    }

    @Override
    public void displayNotFoundLocaltion() {

    }

    @Override
    public void getLocaltionAddress(GeoCodeMapResponse geoCodeMapResponse) {

    }
}
