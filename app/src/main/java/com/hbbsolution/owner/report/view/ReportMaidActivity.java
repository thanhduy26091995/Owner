package com.hbbsolution.owner.report.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.model.helper.Datum;
import com.hbbsolution.owner.history.model.workhistory.WorkHistory;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.report.ReportView;
import com.hbbsolution.owner.report.presenter.ReportPresenter;
import com.hbbsolution.owner.utils.ShowAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        }
        if(workHistory!=null)
        {
            mTextMaidName.setText(workHistory.getStakeholders().getReceived().getInfo().getUsername());
            mTextMaidAddress.setText(workHistory.getStakeholders().getReceived().getInfo().getAddress().getName());
            idHelper = workHistory.getStakeholders().getReceived().getId();
        }
        if (datum != null) {
            mTextMaidName.setText(datum.getId().getInfo().getUsername());
            mTextMaidAddress.setText(datum.getId().getInfo().getAddress().getName());
            idHelper = datum.getId().getId();
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
        ShowAlertDialog.showAlert(message,this);
    }

    @Override
    public void reportFail() {

    }
}
