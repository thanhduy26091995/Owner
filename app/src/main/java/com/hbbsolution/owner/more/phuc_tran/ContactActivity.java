package com.hbbsolution.owner.more.phuc_tran;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hbbsolution.owner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactActivity extends AppCompatActivity {
    @BindView(R.id.toobar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

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
            Toast.makeText(ContactActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clickCall(String phoneNo) {
        if (!TextUtils.isEmpty(phoneNo)) {
            String dial = "tel:" + phoneNo;
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
        } else {
            Toast.makeText(ContactActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
        }
    }
}