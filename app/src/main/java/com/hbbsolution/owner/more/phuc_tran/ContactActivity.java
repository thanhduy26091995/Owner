package com.hbbsolution.owner.more.phuc_tran;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.contact_title_toothbar)
    TextView txtTerms_title_toothbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ButterKnife.bind(this);

        //config toolbar

        //a

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTerms_title_toothbar.setText(getResources().getString(R.string.contact));

        addEvents();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void addEvents() {

        findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = "0900000000";  //set tạm
                clickCall(phoneNo);
            }
        });

        findViewById(R.id.btn_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = "mailmail@gmail.com"; //set tạm
                clickEmail(mail);
            }
        });

    }

    private void clickEmail(String mail) {
        Intent i = new Intent(Intent.ACTION_SEND);

        //đưa người dùng vào email app
        i.setType("application/octet-stream");

        i.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});       //{mail}  --> mail nguoi nhan
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");       //chu de mail
        i.putExtra(Intent.EXTRA_TEXT, "");          //body of mail
        try {
            startActivity(i);

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactActivity.this, R.string.no_email_client, Toast.LENGTH_SHORT).show();
        }
    }

    private void clickCall(String phoneNo) {
        if (!TextUtils.isEmpty(phoneNo)) {
            String dial = "tel:" + phoneNo;
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
        } else {
            Toast.makeText(ContactActivity.this, R.string.enter_phonenumber, Toast.LENGTH_SHORT).show();
        }
    }
}