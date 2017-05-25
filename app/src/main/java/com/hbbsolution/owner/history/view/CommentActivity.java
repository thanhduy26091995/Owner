package com.hbbsolution.owner.history.view;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.work_management.view.payment.PaymentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_avatar)
    ImageView imgOwner;
    @BindView(R.id.tvOwner)
    TextView tvOwner;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.edtComment)
    EditText edtComment;
    @BindView(R.id.lnCheck)
    LinearLayout lnCheck;
    @BindView(R.id.txtNext)
    TextView txtNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        lnCheck.setVisibility(View.VISIBLE);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        lnCheck.setOnClickListener(this);
 //       setBackgroundRatingBar();

        txtNext.setOnClickListener(this);

    }

    public void setBackgroundRatingBar() {
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtNext:
//                Intent itPayment = new Intent(CommentActivity.this, PaymentActivity.class);
//                startActivity(itPayment);
//                finish();
                break;
        }
    }
}
