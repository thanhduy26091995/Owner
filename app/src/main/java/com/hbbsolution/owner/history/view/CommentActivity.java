package com.hbbsolution.owner.history.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.InternetConnection;
import com.hbbsolution.owner.history.CommentView;
import com.hbbsolution.owner.history.presenter.CommentPresenter;
import com.hbbsolution.owner.home.view.HomeActivity;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.utils.ShowSnackbar;

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
    private String idHelper, nameHelper, imgHelper, addressHelper, idTask;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        commentPresenter = new CommentPresenter(this);
        Bundle extras = getIntent().getExtras();
        Bundle mbundleComment = getIntent().getBundleExtra("mbundleComment");
        Bundle infoMaid = getIntent().getBundleExtra("infoMaid");
        if (mbundleComment != null) {
            idTask = mbundleComment.getString("idTask");
            idHelper = mbundleComment.getString("idHelper");
            imgHelper = mbundleComment.getString("imgHelper");
            nameHelper = mbundleComment.getString("nameHelper");
            addressHelper = mbundleComment.getString("addressHelper");


            //       setBackgroundRatingBar();
            tvNameHelper.setText(nameHelper);
            tvAddress.setText(addressHelper);
            if (!imgHelper.equals("")) {
                Glide.with(this).load(imgHelper)
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .centerCrop()
                        .dontAnimate()
                        .into(imgAvatar);
            }

        } else if(infoMaid != null) {
            idTask = infoMaid.getString("idTask");
            idHelper = infoMaid.getString("idHelper");
            imgHelper = infoMaid.getString("imgHelper");
            nameHelper = infoMaid.getString("nameHelper");
            addressHelper = infoMaid.getString("addressHelper");

            //       setBackgroundRatingBar();
            tvNameHelper.setText(nameHelper);
            tvAddress.setText(addressHelper);
            if (!imgHelper.equals("")) {
                Glide.with(this).load(imgHelper)
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .centerCrop()
                        .dontAnimate()
                        .into(imgAvatar);
            }
        }
        txtNext.setOnClickListener(this);
        lnCheck.setOnClickListener(this);
        edtComment.setOnClickListener(this);
        mProgressDialog = new ProgressDialog(this);
        //check internet
        if (!InternetConnection.getInstance().isOnline(CommentActivity.this)) {
            ShowSnackbar.showSnack(CommentActivity.this, getResources().getString(R.string.no_internet));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtNext:
                if (DetailWorkHistoryActivity.detailWorkHistory != null) {
                    finish();
                }
                else
                {
                    Intent intent = new Intent(CommentActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                break;
            case R.id.lnCheck:
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if (edtComment.getText().toString().length() > 0) {
                    if(ratingBar.getRating()!=0) {
                        commentPresenter.postComment(idTask, idHelper, edtComment.getText().toString().trim(), (int) ratingBar.getRating());
                    }
                    else
                    {

                    }
                    showProgress();
                } else {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.add_comment), CommentActivity.this);
                }
                break;
            case R.id.edtComment:
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
        hideProgress();
        if (DetailWorkHistoryActivity.detailWorkHistory != null) {
            Intent intent = new Intent();
            intent.putExtra("message", message);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        else {
            Intent intent = new Intent(CommentActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void commentFail(String message) {
        hideProgress();
        ShowAlertDialog.showAlert(getResources().getString(R.string.commentfail),this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void showProgress() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.loading));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (DetailWorkHistoryActivity.detailWorkHistory != null) {
            finish();
        }
        else
        {
            Intent intent = new Intent(CommentActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void connectServerFail() {
        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), this);
    }
}
