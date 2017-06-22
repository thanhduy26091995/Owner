package com.hbbsolution.owner.more.duy_nguyen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.more.duy_nguyen.model.DataContact;
import com.hbbsolution.owner.more.duy_nguyen.presenter.ContactPresenter;
import com.hbbsolution.owner.paymentonline.ui.activity.PaymentOnlineActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener,ContactView{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rlRecharge)
    RelativeLayout rlRecharge;
    @BindView(R.id.rlInfoBank)
    RelativeLayout rlInfoBank;
    @BindView(R.id.address_recharge)
    TextView tvAddressRecharge;
    @BindView(R.id.phone_recharge)
    TextView tvPhoneRecharge;
    private ContactPresenter contactPresenter;
    private String mNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        contactPresenter = new ContactPresenter(this);
        setToolbar();
        contactPresenter.getContact();
        setEventClick();
    }

    public void setToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setEventClick() {
        rlRecharge.setOnClickListener(this);
        rlInfoBank.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rlRecharge:
                intent = new Intent(RechargeActivity.this,PaymentOnlineActivity.class);
                startActivity(intent);
                break;
            case R.id.rlInfoBank:
                intent = new Intent(RechargeActivity.this,InfoBankActivity.class);
                intent.putExtra("note",mNote);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void getContactSuccess(DataContact dataContact) {
        tvAddressRecharge.setText(dataContact.getAddress());
        tvPhoneRecharge.setText(dataContact.getPhone());
        mNote = dataContact.getNote();
    }

    @Override
    public void getContactFail() {

    }
}
