package com.hbbsolution.owner.work_management.view.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.view.CommentActivity;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.work_management.model.workmanagerpending.DatumPending;
import com.hbbsolution.owner.work_management.view.payment.PaymentActivity;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 5/14/2017.
 */

public class DetailJobDoingActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.manager_doing_title_toothbar)
    TextView txtManager_doing_title_toothbar;
    @BindView(R.id.lo_ChosenCheckin)
    RelativeLayout lo_ChosenCheckin;
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


    private DatumPending mDatum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_doing);

        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//        txtManager_doing_title_toothbar.setText("Đang làm");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lo_ChosenCheckin.setOnClickListener(this);
        lo_infoMaidDoing.setOnClickListener(this);

        final Intent intent = getIntent();
        mDatum = (DatumPending) intent.getSerializableExtra("mDatum");

        txtNameJobDoingInfoMaid.setText(mDatum.getStakeholders().getMadi().getInfo().getName());
        txtAddressJobDoingInfoMaid.setText(mDatum.getStakeholders().getMadi().getInfo().getAddress().getName());
        Picasso.with(this).load(mDatum.getStakeholders().getMadi().getInfo().getImage())
                .error(R.drawable.avatar)
                .placeholder(R.drawable.avatar)
                .into(img_avatarJobDoingInfoMiad);

        txtTitleJobDoing.setText(mDatum.getInfo().getTitle());
        txtTypeJobDoing.setText(mDatum.getInfo().getWork().getName());
        txtContentJobDoing.setText(mDatum.getInfo().getDescription());
        txtPriceJobDoing.setText(String.valueOf(mDatum.getInfo().getPrice()));
        txtAddressJobDoing.setText(mDatum.getInfo().getAddress().getName());
        txtDateJobDoing.setText(getDateStartWork(mDatum.getHistory().getUpdateAt()));
        txtTimeDoWrokJobDoing.setText(getTimerDoingWork(mDatum.getInfo().getTime().getStartAt(), mDatum.getInfo().getTime().getEndAt()));
        Picasso.with(this).load(mDatum.getInfo().getWork().getImage())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(img_job_type);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            EventBus.getDefault().postSticky(false);
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
            case R.id.lo_ChosenCheckin:
                Intent itComment = new Intent(DetailJobDoingActivity.this, PaymentActivity.class);
                startActivity(itComment);
                break;
            case R.id.lo_infoMaidDoing:
                Intent itInfoUser = new Intent(DetailJobDoingActivity.this, MaidProfileActivity.class);
                itInfoUser.putExtra("maid",mDatum.getStakeholders().getMadi());
                startActivity(itInfoUser);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().postSticky(false);
    }

    private String getTimerDoingWork(String startAt, String endAt) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        Date dateStartAt = new DateTime(startAt).toDate();
        Date dateEndtAt = new DateTime(endAt).toDate();
        String mDateStartAt = simpleDateFormat.format(dateStartAt);
        String mDateEndAt = simpleDateFormat.format(dateEndtAt);
        String mTimeDoing = mDateStartAt + " - " + mDateEndAt;

        return mTimeDoing;
    }

    private String getDateStartWork(String dateStartWork) {
        Date date0 = new DateTime(dateStartWork).toDate();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String mDateStartWork = df.format(date0);
        return mDateStartWork;
    }
}
