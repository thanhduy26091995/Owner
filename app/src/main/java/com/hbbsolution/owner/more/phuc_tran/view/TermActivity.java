package com.hbbsolution.owner.more.phuc_tran.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.more.phuc_tran.presenter.AboutPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermActivity extends AppCompatActivity implements AboutView{

    @BindView(R.id.term_toolbar)
    Toolbar toolbar;
    @BindView(R.id.term_title_toolbar)
    TextView txt_term_pass_toolbar;
    @BindView(R.id.wbv_content_term)
    WebView wbv_content_term;

    private AboutPresenter mAboutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);


        ButterKnife.bind(this);

        //config toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_term_pass_toolbar.setText(getResources().getString(R.string.term_title));

        mAboutPresenter = new AboutPresenter(this);

        mAboutPresenter.getTerm();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getAbout(String content) {
        if (content.equals("")){
            content=getResources().getString(R.string.content_null);
        }
        wbv_content_term.getSettings().setJavaScriptEnabled(true);
        wbv_content_term.loadDataWithBaseURL(null,
                content,
                "text/html",
                "utf-8",
                null);
    }
}
