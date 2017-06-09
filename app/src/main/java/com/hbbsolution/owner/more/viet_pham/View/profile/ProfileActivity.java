package com.hbbsolution.owner.more.viet_pham.View.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.ImageLoader;
import com.hbbsolution.owner.more.viet_pham.View.MoreActivity;
import com.hbbsolution.owner.more.viet_pham.View.update.UpdateUserInfoActivity;
import com.hbbsolution.owner.utils.SessionManagerUser;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

import static android.view.View.GONE;

/**
 * Created by buivu on 29/05/2017.
 */

public class ProfileActivity extends AppCompatActivity  {

    @BindView(R.id.info_user_appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar_header)
    Toolbar toolbarHeader;
    @BindView(R.id.txt_profile_name)
    TextView txtProfileName;
    @BindView(R.id.txt_profile_address)
    TextView txtProfileAddress;
    @BindView(R.id.txt_profile_gender)
    TextView txtProfileGender;
    @BindView(R.id.txt_profile_phone)
    TextView txtProfilePhone;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.img_blur_image)
    ImageView imgBlurImage;
    @BindView(R.id.textview_update)
    TextView txtUpdate;

    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashDataUser = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

//        appBarLayout.addOnOffsetChangedListener(this);
        //set up toolbar
        setSupportActionBar(toolbarHeader);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        //init
        sessionManagerUser = new SessionManagerUser(this);
        hashDataUser = sessionManagerUser.getUserDetails();
        loadData();
        addEvent();
    }

    private void loadData() {
        txtProfileName.setText(hashDataUser.get(SessionManagerUser.KEY_NAME));
        txtProfileAddress.setText(hashDataUser.get(SessionManagerUser.KEY_ADDRESS));
        if (Integer.parseInt(hashDataUser.get(SessionManagerUser.KEY_GENDER)) == 0) {
            txtProfileGender.setText("Nam");
        } else {
            txtProfileGender.setText("Nữ");
        }
        txtProfilePhone.setText(hashDataUser.get(SessionManagerUser.KEY_PHONE));
        ImageLoader.getInstance().loadImageAvatar(ProfileActivity.this, hashDataUser.get(SessionManagerUser.KEY_IMAGE),
                imgAvatar);

        // from Bitmap
        Glide.with(ProfileActivity.this)
                .load(hashDataUser.get(SessionManagerUser.KEY_IMAGE))
                .asBitmap()
                .error(R.drawable.avatar)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Blurry.with(ProfileActivity.this)
                                .radius(10)
                                .from(resource)
                                .into(imgBlurImage);
                    }
                });

    }

    private void addEvent()
    {
        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iUpdate = new Intent(ProfileActivity.this, UpdateUserInfoActivity.class);
                startActivity(iUpdate);
            }
        });
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent iMore = new Intent(ProfileActivity.this, MoreActivity.class);
            startActivity(iMore);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
