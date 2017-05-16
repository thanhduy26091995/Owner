package com.hbbsolution.owner.maid_profile.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.ListCommentAdapter;
import com.hbbsolution.owner.model.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 15/05/2017.
 */

public class MaidProfileActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{
//
    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    @BindView(R.id.toolbar_coll)
//    Toolbar toolbar_coll;
//    @BindView(R.id.manager_info_user_title_toothbar)
    TextView txtManager_info_user_title_toothbar;
    @BindView(R.id.info_user_appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.recycler_comment)
    RecyclerView mRecycler;

    private ListCommentAdapter listCommentAdapter;
    private List<Comment> commentList = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_profile);
        ButterKnife.bind(this);
        //init
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        appBarLayout.addOnOffsetChangedListener(this);

        initDataComment();
    }


    private void initDataComment() {
        Comment comment = new Comment("A", 1, 4, "Lau dọn nhà", "Làm rất nhanh, công việc ổn");
        Comment comment1 = new Comment("A", 1, 4, "Lau dọn nhà", "Làm rất nhanh, công việc ổn");
        Comment comment2 = new Comment("A", 1, 4, "Lau dọn nhà", "Làm rất nhanh, công việc ổn");
        Comment comment3 = new Comment("A", 1, 4, "Lau dọn nhà", "Làm rất nhanh, công việc ổn");
        Comment comment4 = new Comment("A", 1, 4, "Lau dọn nhà", "Làm rất nhanh, công việc ổn");
        Comment comment5 = new Comment("A", 1, 4, "Lau dọn nhà", "Làm rất nhanh, công việc ổn");
        Comment comment6 = new Comment("A", 1, 4, "Lau dọn nhà", "Làm rất nhanh, công việc ổn");
        //add to list
        commentList.add(comment);
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);
        commentList.add(comment4);
        commentList.add(comment5);
        commentList.add(comment6);


        listCommentAdapter = new ListCommentAdapter(this, commentList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);

        mRecycler.setAdapter(listCommentAdapter);
        listCommentAdapter.notifyDataSetChanged();
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
        if(collapsingToolbarLayout.getHeight() + verticalOffset <=  1 * ViewCompat.getMinimumHeight(collapsingToolbarLayout)){
            toolbar.setVisibility(View.VISIBLE);
            toolbar.animate().alpha(1).setDuration(200);
        }else{
            toolbar.setVisibility(View.GONE);
            toolbar.animate().alpha(0).setDuration(200);
        }
    }
}
