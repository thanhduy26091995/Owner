package com.hbbsolution.owner.more.phuc_tran.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.more.phuc_tran.presenter.AboutPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements AboutView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.about_title_toolbar)
    TextView txt_about_title_toolbar;
    @BindView(R.id.wbv_about)
    WebView wbv_about;
    private AboutPresenter mAboutPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);

        //config toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_about_title_toolbar.setText(getResources().getString(R.string.about));

        mAboutPresenter = new AboutPresenter(this);
        showProgress();
        mAboutPresenter.getAbout();

    }

    private void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
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
    public void getAbout(String content) {
        hideProgress();
        if (content.equals("")) {
            content = getResources().getString(R.string.content_null);
        }
        wbv_about.getSettings().setJavaScriptEnabled(true);
        wbv_about.loadDataWithBaseURL(null,
                content,
                "text/html",
                "utf-8",
                null);
    }
}