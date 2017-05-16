package com.hbbsolution.owner.more.viet_pham;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.hbbsolution.owner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 5/10/2017.
 */

public class SignUp1Activity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.button_next)
    Button buttonNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_1);
        ButterKnife.bind(this);
        setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addEvents();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addEvents() {
        // Event click next page sign up 2
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp1Activity.this, SignUp2Activity.class);
                startActivity(intent);
            }
        });
    }
}
