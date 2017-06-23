package com.hbbsolution.owner.more.phuc_tran.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.more.duy_nguyen.ContactView;
import com.hbbsolution.owner.more.duy_nguyen.model.DataContact;
import com.hbbsolution.owner.more.duy_nguyen.presenter.ContactPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactActivity extends AppCompatActivity implements ContactView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.contact_title_toolbar)
    TextView txt_contact_title_toolbar;
    @BindView(R.id.btn_call)
    Button btn_call;
    @BindView(R.id.btn_email)
    Button btn_email;
    @BindView(R.id.tvName)
    TextView txt_name;
    @BindView(R.id.tvAddr)
    TextView txt_address;

    private ContactPresenter mContactPresenter;
    private String phoneNo;
    private String mail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ButterKnife.bind(this);

        //config toolbar

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_contact_title_toolbar.setText(getResources().getString(R.string.contact));

        mContactPresenter = new ContactPresenter(this);

        mContactPresenter.getContact();

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

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickCall(phoneNo);
            }
        });

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEmail(mail);
            }
        });

    }

    private void clickEmail(String mail) {
        Intent i = new Intent(Intent.ACTION_SEND);

        //đưa người dùng vào email app
        i.setType("application/octet-stream");

        i.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});       //{mail}  --> mail nguoi nhan
        i.putExtra(Intent.EXTRA_SUBJECT, "");       //chu de mail
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

    @Override
    public void getContactSuccess(DataContact dataContact) {
        mail = dataContact.getEmail();
        phoneNo = dataContact.getPhone();
        txt_name.setText(dataContact.getName());
        txt_address.setText(dataContact.getAddress());
    }

    @Override
    public void getContactFail() {
    }
}