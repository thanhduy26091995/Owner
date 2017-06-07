package com.hbbsolution.owner.more.phuc_tran;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.hbbsolution.owner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.abuot_title_toothbar)
    TextView txtTerms_title_toothbar;
    @BindView(R.id.wvAbout)
    WebView wvAbout;

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
        txtTerms_title_toothbar.setText(getResources().getString(R.string.about));

        addEvents();

        wvAbout.getSettings().setJavaScriptEnabled(true);
        wvAbout.loadDataWithBaseURL(null,
                "The world cellular, as it describes phone technology, was used by engineers Douglas H. Ring and W. Rae Young at Bell Labs. They diagrammed a network of wireless towers into what they called a cellular layout. Cellular was the chosen term because each tower and its coverage map looked like a biological cell. Eventually, phones that operated on this type of wireless network were called cellular phones.\n\n The term mobile phone predates its cellular counterpart. The first mobile phone call was placed in 1946 over Bell System's Mobile telephone service, a closed radiotelephone system. And the first commercial mobile phones were installed cars in the 1970s.\n\n Eventually, the two names, mobile phone and cellular phone, became synonymous, especially here in the US. But some people disagree with that usage. They consider the term  to be a misnomer because the phone is not cellular, the network is. The phone is a mobile phone and it operates on a cellular network."
                ,
                "text/html",
                "utf-8",
                null);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void addEvents() {

    }


}