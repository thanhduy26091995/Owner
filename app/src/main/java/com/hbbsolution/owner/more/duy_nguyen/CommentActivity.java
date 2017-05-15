package com.hbbsolution.owner.more.duy_nguyen;

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

import butterknife.BindView;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.toolbar)
    private Toolbar toolbar;
    @BindView(R.id.img_avatar)
    private ImageView imgOwner;;
    @BindView(R.id.tvOwner)
    private TextView tvOwner;
    @BindView(R.id.tvAddress)
    private TextView tvAddress;;
    @BindView(R.id.ratingBar)
    private RatingBar ratingBar;
    @BindView(R.id.edtComment)
    private EditText edtComment;
    @BindView(R.id.lnCheck)
    private LinearLayout lnCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        lnCheck.setOnClickListener(this);
        setBackgroundRatingBar();

    }
    public void setBackgroundRatingBar()
    {
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
    }
    @Override
    public void onClick(View v) {

    }
}
