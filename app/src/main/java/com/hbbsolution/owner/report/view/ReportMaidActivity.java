package com.hbbsolution.owner.report.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.model.helper.Datum;
import com.hbbsolution.owner.history.model.workhistory.WorkHistory;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.report.ReportView;
import com.hbbsolution.owner.report.presenter.ReportPresenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by buivu on 22/05/2017.
 */

public class ReportMaidActivity extends AppCompatActivity implements View.OnClickListener,ReportView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_maid_name)
    TextView mTextMaidName;
    @BindView(R.id.text_maid_address)
    TextView mTextMaidAddress;
    @BindView(R.id.tvSend)
    TextView tvSend;
    @BindView(R.id.tvSkip)
    TextView tvSkip;
    @BindView(R.id.edtReport)
    EditText edtReport;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;

    private Maid mMaidInfo;
    private WorkHistory workHistory;
    private Datum datum;
    private ReportPresenter reportPresenter;
    private String idHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        //init toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        reportPresenter = new ReportPresenter(this);
        //get intent
        mMaidInfo = (Maid) getIntent().getSerializableExtra("maid");
        workHistory = (WorkHistory)getIntent().getSerializableExtra("work");
        datum = (Datum) getIntent().getSerializableExtra("helper");
        //load data
        loadData();
        tvSend.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
    }

    private void loadData() {
        if (mMaidInfo != null) {
            mTextMaidName.setText(mMaidInfo.getInfo().getUsername());
            mTextMaidAddress.setText(mMaidInfo.getInfo().getAddress().getName());
            idHelper=mMaidInfo.getId();
            if(!mMaidInfo.getInfo().getImage().equals(""))
            {
                Picasso.with(this).load(mMaidInfo.getInfo().getImage())
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .into(imgAvatar);
            }
        }
        if(workHistory!=null)
        {
            mTextMaidName.setText(workHistory.getStakeholders().getReceived().getInfo().getUsername());
            mTextMaidAddress.setText(workHistory.getStakeholders().getReceived().getInfo().getAddress().getName());
            idHelper = workHistory.getStakeholders().getReceived().getId();
            if(!workHistory.getStakeholders().getReceived().getInfo().getImage().equals(""))
            {
                Picasso.with(this).load(workHistory.getStakeholders().getReceived().getInfo().getImage())
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .into(imgAvatar);
            }
        }
        if (datum != null) {
            mTextMaidName.setText(datum.getId().getInfo().getUsername());
            mTextMaidAddress.setText(datum.getId().getInfo().getAddress().getName());
            idHelper = datum.getId().getId();
            if(!datum.getId().getInfo().getImage().equals(""))
            {
                Picasso.with(this).load(datum.getId().getInfo().getImage())
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .into(imgAvatar);
            }
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvSend:
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if (edtReport.getText().toString().length() > 0) {
                    reportPresenter.reportMaid(idHelper, edtReport.getText().toString().trim());
                } else {
                    Toast.makeText(this, "Vui lòng nhập bình luận", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tvSkip:
                finish();
                break;
        }
    }

    @Override
    public void reportSuccess(String message) {
        Intent intent = new Intent();
        intent.putExtra("message",message);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void reportFail() {

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
}
