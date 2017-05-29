package com.hbbsolution.owner.history.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.CommentView;
import com.hbbsolution.owner.history.presenter.CommentPresenter;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener, CommentView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.tvOwner)
    TextView tvNameHelper;
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
    @BindView(R.id.tvComment)
    TextView tvComment;
    @BindView(R.id.rlComment)
    RelativeLayout rlComment;
    private CommentPresenter commentPresenter;
    private String idHelper, nameHelper, imgHelper, addressHelper,idTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idTask=extras.getString("idTask");
            idHelper = extras.getString("idHelper");
            imgHelper = extras.getString("imgHelper");
            nameHelper = extras.getString("nameHelper");
            addressHelper = extras.getString("addressHelper");
        }
        commentPresenter = new CommentPresenter(this);
        lnCheck.setVisibility(View.VISIBLE);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //       setBackgroundRatingBar();
        tvNameHelper.setText(nameHelper);
        tvAddress.setText(addressHelper);
        Picasso.with(this).load(imgHelper)
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(imgAvatar);

        txtNext.setOnClickListener(this);
        lnCheck.setOnClickListener(this);
        rlComment.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtNext:
//                Intent itPayment = new Intent(CommentActivity.this, PaymentActivity.class);
//                startActivity(itPayment);
//                finish();
                break;
            case R.id.lnCheck:
                if (edtComment.getText().toString().length() > 0) {
                    commentPresenter.postComment(idTask,idHelper, edtComment.getText().toString().trim(), (int) ratingBar.getRating());
                } else {
                    Toast.makeText(this, "Vui lòng nhập bình luận", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.rlComment:
                edtComment.requestFocus();
                tvComment.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void commentSuccess(String message) {
        ShowAlertDialog.showAlert(message, this);
    }

    @Override
    public void commentFail(String message) {

    }
}
