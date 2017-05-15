package com.hbbsolution.owner.maid_profile.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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

public class MaidProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
        initDataComment();
    }

    private void initDataComment() {
        Comment comment = new Comment("A", 1, 4, "Lau dọn nhà", "Làm rất nhanh, công việc ổn");
        Comment comment1 = new Comment("A", 1, 4, "Lau dọn nhà", "Làm rất nhanh, công việc ổn");
        Comment comment2 = new Comment("A", 1, 4, "Lau dọn nhà", "Làm rất nhanh, công việc ổn");
        //add to list
        commentList.add(comment);
        commentList.add(comment1);
        commentList.add(comment2);

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
}
