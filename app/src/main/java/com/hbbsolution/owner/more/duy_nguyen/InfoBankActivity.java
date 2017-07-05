package com.hbbsolution.owner.more.duy_nguyen;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.hbbsolution.owner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoBankActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_note_infobank)
    TextView tvNote;
    @BindView(R.id.wbvInfo)
    WebView wbvInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bank);
        ButterKnife.bind(this);
        setToolbar();
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            tvNote.setText(extras.getString("note"));
            wbvInfo.loadData(extras.getString("bank"), "text/html; charset=UTF-8", null);
            wbvInfo.setBackgroundColor(Color.TRANSPARENT);
        }
    }
    public void setToolbar() {
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
