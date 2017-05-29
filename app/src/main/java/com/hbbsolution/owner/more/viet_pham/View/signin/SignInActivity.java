package com.hbbsolution.owner.more.viet_pham.View.signin;

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
import com.hbbsolution.owner.home.HomeActivity;
import com.hbbsolution.owner.more.viet_pham.Model.RegisterResponse;
import com.hbbsolution.owner.more.viet_pham.Presenter.SignInPresenter;
import com.hbbsolution.owner.more.viet_pham.View.signup.SignUp1Activity;
import com.hbbsolution.owner.utils.SessionManagerUser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 5/9/2017.
 */

public class SignInActivity extends AppCompatActivity implements SignInView {
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
    private SignInPresenter mSignInPresenter;
    private SessionManagerUser sessionManagerUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        sessionManagerUser = new SessionManagerUser(this);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addEvents();

        mSignInPresenter = new SignInPresenter(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addEvents() {
        // Sign up now
        btnSignUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUp1Activity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUserName.getText().toString();
                String password = editPassword.getText().toString();

                mSignInPresenter.signIn(username, password);
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
        if (registerResponse.getStatus().equals("true")) {
            //save session
            sessionManagerUser.createLoginSession(registerResponse.getData().getUser());
            //move to HomeActivity
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void displayError() {

    }
}
