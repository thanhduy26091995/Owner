package com.hbbsolution.owner.work_management.view.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.utils.WorkTimeValidate;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 6/29/2017.
 */

public class DetailJobSentRequestActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.manager_doing_title_toothbar)
    TextView txtManager_doing_title_toothbar;
    @BindView(R.id.txtNameJobDoingInfoMaid)
    TextView txtNameJobDoingInfoMaid;
    @BindView(R.id.txtAddressJobDoingInfoMaid)
    TextView txtAddressJobDoingInfoMaid;
    @BindView(R.id.img_avatarJobDoingInfoMiad)
    ImageView img_avatarJobDoingInfoMiad;
    @BindView(R.id.img_job_type)
    ImageView img_job_type;
    @BindView(R.id.txtTitleJobDoing)
    TextView txtTitleJobDoing;
    @BindView(R.id.txtTypeJobDoing)
    TextView txtTypeJobDoing;
    @BindView(R.id.txtContentJobDoing)
    TextView txtContentJobDoing;
    @BindView(R.id.txtPriceJobDoing)
    TextView txtPriceJobDoing;
    @BindView(R.id.txtDateJobDoing)
    TextView txtDateJobDoing;
    @BindView(R.id.txtTimeDoWrokJobDoing)
    TextView txtTimeDoWrokJobDoing;
    @BindView(R.id.txtAddressJobDoing)
    TextView txtAddressJobDoing;
    @BindView(R.id.lo_infoMaidDoing)
    RelativeLayout lo_infoMaidDoing;
    @BindView(R.id.txt_lo_infoMail)
    TextView txt_lo_infoMail;
    @BindView(R.id.progressDetailJobDoing)
    ProgressBar progressDetailJobDoing;

    public static Activity mDetailJobDoingActivity = null;


    private Datum mDatum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_sent_request);
        mDetailJobDoingActivity = this;

        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lo_infoMaidDoing.setOnClickListener(this);


        final Intent intent = getIntent();
        mDatum = (Datum) intent.getSerializableExtra("mDatum");

        txtNameJobDoingInfoMaid.setText(mDatum.getStakeholders().getMaid().getInfo().getName());
        txtAddressJobDoingInfoMaid.setText(mDatum.getStakeholders().getMaid().getInfo().getAddress().getName());
        Picasso.with(this).load(mDatum.getStakeholders().getMaid().getInfo().getImage())
                .error(R.drawable.avatar)
                .placeholder(R.drawable.avatar)
                .into(img_avatarJobDoingInfoMiad);

        txtTitleJobDoing.setText(mDatum.getInfo().getTitle());
        txtTypeJobDoing.setText(mDatum.getInfo().getWork().getName());
        txtContentJobDoing.setText(mDatum.getInfo().getDescription());
        txtPriceJobDoing.setText(String.format("%s VND", NumberFormat.getNumberInstance(Locale.GERMANY).format(mDatum.getInfo().getPrice())));
        txtAddressJobDoing.setText(mDatum.getInfo().getAddress().getName());
        txtDateJobDoing.setText(WorkTimeValidate.getDatePostHistory(mDatum.getHistory().getUpdateAt()));
        String mStartTime = WorkTimeValidate.getTimeWork(mDatum.getInfo().getTime().getStartAt());
        String mEndTime = WorkTimeValidate.getTimeWork(mDatum.getInfo().getTime().getEndAt());
        txtTimeDoWrokJobDoing.setText( mStartTime + " - " + mEndTime);
//        txtTimeDoWrokJobDoing.setText(getTimerDoingWork(mDatum.getInfo().getTime().getStartAt(), mDatum.getInfo().getTime().getEndAt()));
        Picasso.with(this).load(mDatum.getInfo().getWork().getImage())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(img_job_type);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            EventBus.getDefault().postSticky(false);
            EventBus.getDefault().postSticky("2");
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lo_infoMaidDoing:
                Intent itInfoUser = new Intent(DetailJobSentRequestActivity.this, MaidProfileActivity.class);
                itInfoUser.putExtra("maid",mDatum.getStakeholders().getMaid());
                startActivity(itInfoUser);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().postSticky(false);
        EventBus.getDefault().postSticky("2");
    }



}
