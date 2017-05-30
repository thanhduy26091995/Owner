package com.hbbsolution.owner.maid_profile.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.ListCommentAdapter;
import com.hbbsolution.owner.base.IconTextView;
import com.hbbsolution.owner.history.model.helper.Datum;
import com.hbbsolution.owner.history.model.workhistory.WorkHistory;
import com.hbbsolution.owner.maid_profile.choose_maid.view.ChooseMaidActivity;
import com.hbbsolution.owner.maid_profile.presenter.MaidProfilePresenter;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.report.view.ReportMaidActivity;
import com.hbbsolution.owner.work_management.model.listcommentmaid.CommentMaidResponse;
import com.hbbsolution.owner.work_management.model.listcommentmaid.Doc;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

import static android.view.View.GONE;

/**
 * Created by buivu on 15/05/2017.
 */

public class MaidProfileActivity extends AppCompatActivity implements MaidProfileView, View.OnClickListener, AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.lo_toolbar)
    LinearLayout toolbar;
    @BindView(R.id.info_user_appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.recycler_comment)
    RecyclerView mRecycler;
    @BindView(R.id.txtNameInfoMaid)
    TextView txtNameInfoMaid;
    @BindView(R.id.txtPriceInfoMaid)
    TextView txtPriceInfoMaid;
    @BindView(R.id.txtGenderInfoMaid)
    TextView txtGenderInfoMaid;
    @BindView(R.id.txtPhoneInfoMaid)
    TextView txtPhoneInfoMaid;
    @BindView(R.id.txtAddressInfoMaid)
    TextView txtAddressInfoMaid;
    @BindView(R.id.ratingInfoMaid)
    RatingBar ratingInfoMaid;
    @BindView(R.id.lo_ChosenMaidInfo)
    RelativeLayout lo_ChosenMaidInfo;
    @BindView(R.id.toolbar_header)
    Toolbar toolbarHeader;
    @BindView(R.id.txtBackInfoMaid)
    IconTextView txtBackInfoMaid;
    @BindView(R.id.linear_report_maid)
    LinearLayout linearReportMaid;
    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.img_avatarMaid)
    ImageView img_avatarMaid;
    @BindView(R.id.img_blur_image)
    ImageView imgBlurImage;

    private MaidProfilePresenter mMaidProfilePresenter;
    private List<Doc> commentList = new ArrayList<>();
    private ListCommentAdapter listCommentAdapter;
    private Maid mMaidInfo;
    private WorkHistory workHistory;
    private Datum datum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_profile);
        ButterKnife.bind(this);
        //init

        setSupportActionBar(toolbarHeader);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mMaidProfilePresenter = new MaidProfilePresenter(this);


        appBarLayout.addOnOffsetChangedListener(this);
        //event click
        lo_ChosenMaidInfo.setOnClickListener(this);
        txtBackInfoMaid.setOnClickListener(this);
        linearReportMaid.setOnClickListener(this);

        mMaidInfo = (Maid) getIntent().getSerializableExtra("maid");
        datum = (Datum) getIntent().getSerializableExtra("helper");
        workHistory = (WorkHistory) getIntent().getSerializableExtra("work");
        if (mMaidInfo != null) {
            txtNameInfoMaid.setText(mMaidInfo.getInfo().getUsername());
            txtPriceInfoMaid.setText(String.valueOf(mMaidInfo.getWorkInfo().getPrice()));
            txtGenderInfoMaid.setText(getGenderMaid(mMaidInfo.getInfo().getGender()));
            txtPhoneInfoMaid.setText(mMaidInfo.getInfo().getPhone());
            txtAddressInfoMaid.setText(mMaidInfo.getInfo().getAddress().getName());
            ratingInfoMaid.setRating(mMaidInfo.getWorkInfo().getEvaluationPoint());
            Picasso.with(this).load(mMaidInfo.getInfo().getImage())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(img_avatarMaid);
            mMaidProfilePresenter.getInfoListMaid(mMaidInfo.getId(), 1);

            // from Bitmap
            Glide.with(MaidProfileActivity.this)
                    .load(mMaidInfo.getInfo().getImage())
                    .asBitmap()
                    .error(R.drawable.avatar)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Blurry.with(MaidProfileActivity.this)
                                    .radius(15)
                                    .from(resource)
                                    .into(imgBlurImage);
                        }
                    });
        }
        if (workHistory != null) {
            txtNameInfoMaid.setText(workHistory.getStakeholders().getReceived().getInfo().getName());
            txtPriceInfoMaid.setText(String.valueOf(workHistory.getStakeholders().getReceived().getWorkInfo().getPrice()));
            txtGenderInfoMaid.setText(getGenderMaid(workHistory.getStakeholders().getReceived().getInfo().getGender()));
            txtPhoneInfoMaid.setText(workHistory.getStakeholders().getReceived().getInfo().getPhone());
            txtAddressInfoMaid.setText(workHistory.getStakeholders().getReceived().getInfo().getAddress().getName());
            ratingInfoMaid.setRating(workHistory.getStakeholders().getReceived().getWorkInfo().getEvaluationPoint());
            mMaidProfilePresenter.getInfoListMaid(workHistory.getStakeholders().getReceived().getId(), 1);
            Picasso.with(this).load(workHistory.getStakeholders().getReceived().getInfo().getImage())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(img_avatarMaid);
            Glide.with(MaidProfileActivity.this)
                    .load(workHistory.getStakeholders().getReceived().getInfo().getImage())
                    .asBitmap()
                    .error(R.drawable.avatar)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Blurry.with(MaidProfileActivity.this)
                                    .radius(15)
                                    .from(resource)
                                    .into(imgBlurImage);
                        }
                    });
            lo_ChosenMaidInfo.setVisibility(View.GONE);
            vLine.setVisibility(View.GONE);
        }
        if (datum != null) {
            txtNameInfoMaid.setText(datum.getId().getInfo().getName());
            //       txtPriceInfoMaid.setText(String.valueOf(datum.getId().getWorkInfo().getPrice()));
            txtGenderInfoMaid.setText(getGenderMaid(datum.getId().getInfo().getGender()));
            txtPhoneInfoMaid.setText(datum.getId().getInfo().getPhone());
            txtAddressInfoMaid.setText(datum.getId().getInfo().getAddress().getName());
            //       ratingInfoMaid.setRating(workHistory.getStakeholders().getReceived().getWorkInfo().getEvaluationPoint());
            mMaidProfilePresenter.getInfoListMaid(datum.getId().getId(), 1);
            Picasso.with(this).load(datum.getId().getInfo().getImage())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(img_avatarMaid);
            Glide.with(MaidProfileActivity.this)
                    .load(datum.getId().getInfo().getImage())
                    .asBitmap()
                    .error(R.drawable.avatar)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Blurry.with(MaidProfileActivity.this)
                                    .radius(15)
                                    .from(resource)
                                    .into(imgBlurImage);
                        }
                    });
            lo_ChosenMaidInfo.setVisibility(View.GONE);
            vLine.setVisibility(View.GONE);
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
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (collapsingToolbarLayout.getHeight() + verticalOffset <= 1 * ViewCompat.getMinimumHeight(collapsingToolbarLayout)) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.animate().alpha(1).setDuration(200);
        } else {
            toolbar.setVisibility(GONE);
            toolbar.animate().alpha(0).setDuration(200);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lo_ChosenMaidInfo:
                Intent intentChooseMaid = new Intent(MaidProfileActivity.this, ChooseMaidActivity.class);
                startActivity(intentChooseMaid);
                break;
            case R.id.txtBackInfoMaid:
                finish();
                break;
            case R.id.linear_report_maid:
                Intent intent = new Intent(MaidProfileActivity.this, ReportMaidActivity.class);
                intent.putExtra("maid", mMaidInfo);
                intent.putExtra("work", workHistory);
                intent.putExtra("helper", datum);
                startActivity(intent);
                break;

        }
    }

    private String getGenderMaid(int gender) {
        if (gender == 0) {
            return getResources().getString(R.string.pro_file_gender_male);
        }
        return getResources().getString(R.string.pro_file_gender_female);
    }

    @Override
    public void getListCommentMaid(CommentMaidResponse mCommentMaidResponse) {

        commentList = mCommentMaidResponse.getData().getDocs();
        listCommentAdapter = new ListCommentAdapter(this, commentList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(listCommentAdapter);
        listCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMessager() {

    }
}
