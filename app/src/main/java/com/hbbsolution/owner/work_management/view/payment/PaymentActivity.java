package com.hbbsolution.owner.work_management.view.payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.view.CommentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tantr on 5/19/2017.
 */

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.lo_Gv24)
    RelativeLayout lo_Gv24;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_impossible);
        ButterKnife.bind(this);

        lo_Gv24.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lo_Gv24:
                confirm();
                break;
        }
    }

    private void confirm(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Thông báo");
        alertDialog.setMessage("Vui lòng xác nhận thanh toán bằng cách nhấn " + "OK");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent itPayment = new Intent(PaymentActivity.this, CommentActivity.class);
                startActivity(itPayment);
                finish();
            }
        });
        alertDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }
}
