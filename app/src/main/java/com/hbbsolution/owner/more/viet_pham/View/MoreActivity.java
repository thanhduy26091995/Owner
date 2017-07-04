package com.hbbsolution.owner.more.viet_pham.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.ImageLoader;
import com.hbbsolution.owner.model.AnnouncementResponse;
import com.hbbsolution.owner.more.duy_nguyen.LanguageActivity;
import com.hbbsolution.owner.more.duy_nguyen.StatisticActivity;
import com.hbbsolution.owner.more.phuc_tran.view.AboutActivity;
import com.hbbsolution.owner.more.phuc_tran.view.ContactActivity;
import com.hbbsolution.owner.more.phuc_tran.view.TermActivity;
import com.hbbsolution.owner.more.viet_pham.Presenter.MorePresenter;
import com.hbbsolution.owner.more.viet_pham.View.profile.ProfileActivity;
import com.hbbsolution.owner.more.viet_pham.View.shareapp.ShareCodeActivity;
import com.hbbsolution.owner.more.viet_pham.View.signin.SignInActivity;
import com.hbbsolution.owner.more.viet_pham.base.GoogleAuthController;
import com.hbbsolution.owner.utils.SessionManagerForAnnouncement;
import com.hbbsolution.owner.utils.SessionManagerForLanguage;
import com.hbbsolution.owner.utils.SessionManagerUser;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 04/05/2017.
 */

public class MoreActivity extends AppCompatActivity implements View.OnClickListener, MoreForAnnouncementView, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.more_title_toothbar)
    TextView txtMore_title_toothbar;
    @BindView(R.id.cv_sign_in)
    CardView cvSignIn;
    @BindView(R.id.cardview_statistic)
    CardView cvStatistic;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.lnLanguage)
    LinearLayout lnLanguage;
    @BindView(R.id.lnLogOut)
    LinearLayout lnLogOut;
    @BindView(R.id.linearlayout_follow_facebook)
    LinearLayout lnlFollowFacebook;
    @BindView(R.id.lo_about)
    RelativeLayout lo_about;
    @BindView(R.id.lo_terms1)
    RelativeLayout lo_terms1;
    @BindView(R.id.lo_terms)
    RelativeLayout lo_terms;
    @BindView(R.id.lo_share_app)
    LinearLayout lo_share_app;
    @BindView(R.id.switch_announcement)
    SwitchCompat switch_announcement;
    private SessionManagerUser sessionManagerUser;
    private SessionManagerForAnnouncement sessionManagerForAnnouncement;
    private HashMap<String, String> hashDataUser = new HashMap<>();
    private boolean isPause = false;
    private MorePresenter morePresenter;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        sessionManagerUser = new SessionManagerUser(this);
        sessionManagerForAnnouncement = new SessionManagerForAnnouncement(this);
        morePresenter = new MorePresenter(this);
        initData();
        //config toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtMore_title_toothbar.setText(getResources().getString(R.string.more));

        lo_share_app.setOnClickListener(this);
        addEvents();
        //event for switch
        switch_announcement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (sessionManagerUser.isLoggedIn()) {
                        sessionManagerForAnnouncement.createStateAnnouncement(true);
                        //call api to save deviceToken
                        String deviceToken = String.format("%s@//@android", FirebaseInstanceId.getInstance().getToken());
                        morePresenter.onAnnouncement(deviceToken);
                    }
                } else {
                    if (sessionManagerUser.isLoggedIn()) {
                        sessionManagerForAnnouncement.createStateAnnouncement(false);
                        morePresenter.offAnnouncement();
                    }
                }
            }
        });
    }

    private void initData() {
        hashDataUser = sessionManagerUser.getUserDetails();
        //showing data
        txtName.setText(hashDataUser.get(SessionManagerUser.KEY_NAME));
        txtAddress.setText(hashDataUser.get(SessionManagerUser.KEY_ADDRESS));
        ImageLoader.getInstance().loadImageAvatar(MoreActivity.this, hashDataUser.get(SessionManagerUser.KEY_IMAGE),
                imgAvatar);
        //check state of announcement
        if (sessionManagerForAnnouncement.getStateAnnouncement()) {
            switch_announcement.setChecked(true);
        } else {
            switch_announcement.setChecked(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            Intent iHome = new Intent(MoreActivity.this, HomeActivity.class);
//            startActivity(iHome);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAuthController.install(this, this);
    }

    public void addEvents() {
        cvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManagerUser.isLoggedIn()) {
                    Intent intent = new Intent(MoreActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MoreActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            }
        });

//        lo_terms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(MoreActivity.this, "AAA", Toast.LENGTH_SHORT).show();
//                Intent itTerms = new Intent(MoreActivity.this, TermsActivity.class);
//                startActivity(itTerms);
//            }
//        });
        cvStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iStatistic = new Intent(MoreActivity.this, StatisticActivity.class);
                startActivity(iStatistic);
            }
        });
        lnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreActivity.this, LanguageActivity.class);
                startActivity(intent);
            }
        });
        lnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MoreActivity.this);
                builder.setTitle(getResources().getString(R.string.signout))
                        .setMessage(getResources().getString(R.string.signoutContent)).setCancelable(true)
                        .setNegativeButton(getResources().getString(R.string.cancel), null)
                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                FirebaseAuth.getInstance().signOut();
                                sessionManagerUser.logoutUser();
                                mGoogleApiClient = GoogleAuthController.getInstance().getGoogleApiClient();
                                if (mGoogleApiClient != null) {
                                    GoogleAuthController.getInstance().signOut(new GoogleApiClient.ConnectionCallbacks() {
                                        @Override
                                        public void onConnected(@Nullable Bundle bundle) {
                                            if (mGoogleApiClient.isConnected()) {
                                                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                                                    @Override
                                                    public void onResult(@NonNull Status status) {
                                                        if (status.isSuccess()) {
                                                        }
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onConnectionSuspended(int i) {
                                            Log.d("Error", "GoogleSubmitter API Client Connection Suspended");
                                        }
                                    });
                                }
                                Intent intent = new Intent(MoreActivity.this, SignInActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
            }
        });
        lo_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iStatistic = new Intent(MoreActivity.this, AboutActivity.class);
                startActivity(iStatistic);
            }
        });
        lo_terms1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iStatistic = new Intent(MoreActivity.this, TermActivity.class);
                startActivity(iStatistic);
            }
        });
        lo_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iStatistic = new Intent(MoreActivity.this, ContactActivity.class);
                startActivity(iStatistic);
            }
        });
        lnlFollowFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView webview = new WebView(MoreActivity.this);
                webview.getSettings().setJavaScriptEnabled(true);
                webview.loadUrl("https://www.facebook.com/Ng%C6%B0%E1%BB%9Di-Gi%C3%BAp-Vi%E1%BB%87c-247-122998571630965/");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPause) {
            SessionManagerForLanguage sessionManagerForLanguage = new SessionManagerForLanguage(MoreActivity.this);
            boolean isChangeLanguage = sessionManagerForLanguage.changeLanguage();
            if (isChangeLanguage) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(this.getIntent());
                overridePendingTransition(0, 0);
                sessionManagerForLanguage.setChangeLanguage();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lo_share_app:
                Intent itShareCode = new Intent(this, ShareCodeActivity.class);
                startActivity(itShareCode);
                break;
        }
    }

    @Override
    public void onAnnouncement(AnnouncementResponse announcementResponse) {

    }

    @Override
    public void offAnnouncement(AnnouncementResponse announcementResponse) {

    }

    @Override
    public void getError(String error) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
