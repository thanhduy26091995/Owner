package com.hbbsolution.owner.more.viet_pham;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hbbsolution.owner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 5/9/2017.
 */

public class SignInActivity extends AppCompatActivity {
    @BindView(R.id.toobar)
    Toolbar toolbar;
    @BindView(R.id.bt_work_around_here)
    Button btnWorkAroundHere;
    @BindView(R.id.bt_sign_in)
    Button btnSignIn;
    @BindView(R.id.bt_forget_password)
    Button btnForgetPassword;
    @BindView(R.id.bt_sign_up_now)
    Button btnSignUpNow;
    @BindView(R.id.edit_username)
    EditText editUserName;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.imb_facebook)
    ImageButton imbFacebook;
    @BindView(R.id.imb_google)
    ImageButton imbGoogle;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addEvents();

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
        // Sign up now
        btnSignUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,SignUp1Activity.class);
                startActivity(intent);
            }
        });
    }
}
