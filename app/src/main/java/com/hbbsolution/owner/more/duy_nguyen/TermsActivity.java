package com.hbbsolution.owner.more.duy_nguyen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.more.viet_pham.Model.RegisterResponse;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by buivu on 04/05/2017.
 */

public class TermsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.terms_title_toothbar)
    TextView txtTerms_title_toothbar;
    @BindView(R.id.terms_btn_OK)
    Button btnOK;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        ButterKnife.bind(this);

        //config toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTerms_title_toothbar.setText(getResources().getString(R.string.terms));

        mProgressDialog = new ProgressDialog(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addEvents()
    {
        // Event register account
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iTerms = getIntent();
                Bundle bTerms = iTerms.getBundleExtra("bSignUp2");
                String username = bTerms.getString("username");
                String password = bTerms.getString("password");
                String email = bTerms.getString("email");
                String fullname = bTerms.getString("fullname");
                String gender = bTerms.getString("gender");
                String phoneNumber = bTerms.getString("phone");
                String location = bTerms.getString("location");
                String filepath = bTerms.getString("filepath");

            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
