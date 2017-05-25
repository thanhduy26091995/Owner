package com.hbbsolution.owner.more.viet_pham.View.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.utils.ShowAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 5/10/2017.
 */

public class SignUp1Activity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.button_next)
    Button buttonNext;
    @BindView(R.id.edit_username)
    EditText edtUserName;
    @BindView(R.id.edit_password)
    EditText edtPassword;
    @BindView(R.id.edit_confirm_password)
    EditText edtConfirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_1);
        ButterKnife.bind(this);
        setTitle("");
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

    public void addEvents() {
        // Event click next page sign up 2
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUserName.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();
                if (username.trim().length() == 0 || password.length() == 0 || confirmPassword.length() == 0) {
                    ShowAlertDialog.showAlert("Vui lòng nhập đầy đủ thông tin",SignUp1Activity.this);
                } else {
                    if (password.equals(confirmPassword)) {
                        Intent iSignUp1 = new Intent(SignUp1Activity.this, SignUp2Activity.class);
                        Bundle bNextPage = new Bundle();
                        bNextPage.putString("username", username);
                        bNextPage.putString("password", password);
                        iSignUp1.putExtra("bNextPage", bNextPage);
                        startActivity(iSignUp1);
                    } else {
//                        Toast.makeText(SignUp1Activity.this,"Xác nhận mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        ShowAlertDialog.showAlert("Xác nhận mật khẩu không đúng",SignUp1Activity.this);
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
