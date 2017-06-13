package com.hbbsolution.owner.maid_profile.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import com.hbbsolution.owner.history.model.helper.MaidHistory;
import com.hbbsolution.owner.history.model.workhistory.WorkHistory;
import com.hbbsolution.owner.maid_profile.adapter.TypeJobAdapter;
import com.hbbsolution.owner.maid_profile.choose_maid.view.ChooseMaidActivity;
import com.hbbsolution.owner.maid_profile.presenter.MaidProfilePresenter;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.report.view.ReportMaidActivity;
import com.hbbsolution.owner.utils.EndlessRecyclerViewScrollListener;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.listcommentmaid.CommentMaidResponse;
import com.hbbsolution.owner.work_management.model.listcommentmaid.Doc;
import com.hbbsolution.owner.work_management.view.detail.DetailJobPostActivity;
import com.hbbsolution.owner.work_management.view.listmaid.ListUserRecruitmentActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
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
    @BindView(R.id.recy_listTypeJob)
    RecyclerView recy_listTypeJob;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private MaidProfilePresenter mMaidProfilePresenter;
    private List<Doc> commentList = new ArrayList<>();
    private ListCommentAdapter listCommentAdapter;
    private Maid mMaidInfo;
    private WorkHistory workHistory;
    private MaidHistory datum;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int currentPage = 1;
    private String idTaskProcess;
    private boolean isChosenMaidFromRecruitment = false;

    public static MaidProfileActivity maidProfileActivity;

    private List<String> list;
    public static Activity mMaidProfileActivity = null;

    private static final int REPORT = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_profile);
        mMaidProfileActivity = this;
        ButterKnife.bind(this);
        //init
        maidProfileActivity = this;
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

        list = new ArrayList<>();
        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818990/don_dep_nha_z2lny1.png");
        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818997/nau_an_copy_ogjsu6.png");
        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818990/don_dep_nha_z2lny1.png");
        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818997/nau_an_copy_ogjsu6.png");
        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818997/nau_an_copy_ogjsu6.png");
        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818990/don_dep_nha_z2lny1.png");
//        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818997/nau_an_copy_ogjsu6.png");
//        list.add("http://res.cloudinary.com/einzweidrei2/image/upload/v1494818990/don_dep_nha_z2lny1.png");


        mMaidInfo = (Maid) getIntent().getSerializableExtra("maid");
        datum = (MaidHistory) getIntent().getSerializableExtra("helper");
        workHistory = (WorkHistory) getIntent().getSerializableExtra("work");
        isChosenMaidFromRecruitment = getIntent().getBooleanExtra("chosenMaidFromListRecruitment", false);

        if (mMaidInfo != null) {
            idTaskProcess = getIntent().getStringExtra("idTaskProcess");
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
            mMaidProfilePresenter.getInfoListMaid(mMaidInfo.getId(), currentPage);

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
//            Picasso.with(this).load(workHistory.getStakeholders().getReceived().getInfo().getImage())
//                    .placeholder(R.drawable.avatar)
//                    .error(R.drawable.avatar)
//                    .into(img_avatarMaid);
            Picasso.with(this)
                    .load(workHistory.getStakeholders().getReceived().getInfo().getImage())
                    .fit()
                    .noFade()
                    .centerCrop()
                    .into(img_avatarMaid, new Callback() {
                        @Override
                        public void onSuccess() {
                            supportStartPostponedEnterTransition();
                        }

                        @Override
                        public void onError() {
                            supportStartPostponedEnterTransition();
                        }
                    });
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
//            Picasso.with(this).load(datum.getId().getInfo().getImage())
//                    .placeholder(R.drawable.avatar)
//                    .error(R.drawable.avatar)
//                    .into(img_avatarMaid);
            supportPostponeEnterTransition();
            Picasso.with(this)
                    .load(datum.getId().getInfo().getImage())
                    .fit()
                    .noFade()
                    .centerCrop()
                    .into(img_avatarMaid, new Callback() {
                        @Override
                        public void onSuccess() {
                            supportStartPostponedEnterTransition();
                        }

                        @Override
                        public void onError() {
                            supportStartPostponedEnterTransition();
                        }
                    });
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
        TypeJobAdapter typeJobAdapter = new TypeJobAdapter(this,list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        recy_listTypeJob.setLayoutManager(layoutManager);
        recy_listTypeJob.setAdapter(typeJobAdapter);
        typeJobAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
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
        if (collapsingToolbarLayout.getHeight() + verticalOffset <= 1.5 * ViewCompat.getMinimumHeight(collapsingToolbarLayout)) {
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
                if (isChosenMaidFromRecruitment) {
                    mMaidProfilePresenter.sentRequestChosenMaid(idTaskProcess, mMaidInfo.getId());
                }else {
                    Intent intentChooseMaid = new Intent(MaidProfileActivity.this, ChooseMaidActivity.class);
                    intentChooseMaid.putExtra("maid", mMaidInfo);
                    startActivity(intentChooseMaid);
                }
                break;
            case R.id.txtBackInfoMaid:
                finish();
                break;
            case R.id.linear_report_maid:
                Intent intent = new Intent(MaidProfileActivity.this, ReportMaidActivity.class);
                intent.putExtra("maid", mMaidInfo);
                intent.putExtra("work", workHistory);
                intent.putExtra("helper", datum);
                startActivityForResult(intent,REPORT);
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
        final int pages = mCommentMaidResponse.getData().getPages();
        commentList = mCommentMaidResponse.getData().getDocs();
        listCommentAdapter = new ListCommentAdapter(this, commentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(listCommentAdapter);
        listCommentAdapter.notifyDataSetChanged();
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (currentPage < pages) {
                    mMaidProfilePresenter.getMoreInfoListMaid(mMaidInfo.getId(), currentPage);
                }
            }
        };
    }

    @Override
    public void getMoreListCommentMaid(CommentMaidResponse mCommentMaidResponse) {
        commentList.addAll( mCommentMaidResponse.getData().getDocs());
        currentPage++;
        listCommentAdapter.notifyDataSetChanged();
        mRecycler.post(new Runnable() {
            @Override
            public void run() {
                listCommentAdapter.notifyItemRangeInserted(listCommentAdapter.getItemCount(), commentList.size() - 1);
            }
        });
    }

    @Override
    public void responseChosenMaid(boolean isResponseChosenMaid) {
        if (isResponseChosenMaid){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle("Thông báo");
            alertDialog.setMessage("Bạn đã chọn người giúp việc thành công !");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EventBus.getDefault().postSticky(true);
                    if(mMaidProfileActivity != null){
                        MaidProfileActivity.mMaidProfileActivity.finish();
                        try{
                            if(DetailJobPostActivity.mDetailJobPostActivity != null){
                                DetailJobPostActivity.mDetailJobPostActivity.finish();
                            }
                            if(ListUserRecruitmentActivity.mListUserRecruitmentActivity != null){
                                ListUserRecruitmentActivity.mListUserRecruitmentActivity.finish();
                            }
                        }catch (Exception e){

                        }
                    }

                }
            });

            alertDialog.show();
        }else {
            ShowAlertDialog.showAlert("Thất bại", MaidProfileActivity.this);
        }
    }

    @Override
    public void getMessager() {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REPORT)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                ShowAlertDialog.showAlert(data.getStringExtra("message"),this);
            }
        }
    }
}
