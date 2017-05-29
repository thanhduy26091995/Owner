package com.hbbsolution.owner.history.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailLiabilitiesHistory extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lo_infoMaid)
    RelativeLayout lo_infoMaid;
    @BindView(R.id.txtTitleHistory)
    TextView txtTitleHistory;
    @BindView(R.id.txtWorkHistory)
    TextView txtWorkHistory;
    @BindView(R.id.txtContentHistory)
    TextView txtContentHistory;
    @BindView(R.id.txtPriceHistory)
    TextView txtPriceHistory;
    @BindView(R.id.txtDateHistory)
    TextView txtDateHistory;
    @BindView(R.id.txtTimeDoWorkHistory)
    TextView txtTimeDoWorkHistory;
    @BindView(R.id.txtAddressWorkHistory)
    TextView txtAddressHistory;
    @BindView(R.id.txtNameMaid)
    TextView txtNameMaid;
    @BindView(R.id.txtAddressMaid)
    TextView txtAddressMaid;
    @BindView(R.id.img_avatarMaid)
    ImageView img_avatarMaid;
    @BindView(R.id.img_TypeJob)
    ImageView img_TypeJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_liabilities_history);
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

}
