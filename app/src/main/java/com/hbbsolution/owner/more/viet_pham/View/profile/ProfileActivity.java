package com.hbbsolution.owner.more.viet_pham.View.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.hbbsolution.owner.R;

import butterknife.BindView;

import static android.view.View.GONE;

/**
 * Created by buivu on 29/05/2017.
 */

public class ProfileActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener  {

    @BindView(R.id.lo_toolbar)
    LinearLayout toolbar;
    @BindView(R.id.info_user_appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
}
