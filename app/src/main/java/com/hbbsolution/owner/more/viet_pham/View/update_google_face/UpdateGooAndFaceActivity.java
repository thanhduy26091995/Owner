package com.hbbsolution.owner.more.viet_pham.View.update_google_face;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.base.BaseActivity;
import com.hbbsolution.owner.base.ImageLoader;
import com.hbbsolution.owner.home.view.HomeActivity;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.BodyResponse;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.DataUpdateResponse;
import com.hbbsolution.owner.more.viet_pham.Presenter.UpdateInfoGooAndFacePresenter;
import com.hbbsolution.owner.more.viet_pham.View.MoreView;
import com.hbbsolution.owner.more.viet_pham.base.GoogleAuthController;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hbbsolution.owner.utils.ShowAlertDialog.mProgressDialog;

/**
 * Created by Administrator on 6/21/2017.
 */

public class UpdateGooAndFaceActivity extends BaseActivity implements MoreView, GoogleApiClient.OnConnectionFailedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_email)
    EditText edtEmail;
    @BindView(R.id.edit_full_name)
    EditText edtFullName;
    @BindView(R.id.edit_gender)
    EditText edtGender;
    @BindView(R.id.edit_number)
    EditText edtNumber;
    @BindView(R.id.edit_location)
    EditText edtLocation;
    @BindView(R.id.button_update)
    Button btnUpdate;
    @BindView(R.id.image_avatar)
    CircleImageView ivAvatar;
    private double mLat;
    private double mLng;
    private UpdateInfoGooAndFacePresenter mUpdateInfoGooAndFacePresenter;
    private String emailGoogle;
    private String nameGoogle;
    private String idUser;
    private String TokenId;
    private String deviceToken;
    private String imageGoogle;
    private String mFullName, mPhoneName, mLocation, mGender;
    private int iGender;
    private SessionManagerUser mSessionManagerUser;
    private HashMap<String, String> hashDataUser = new HashMap<>();
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        ButterKnife.bind(this);
        setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        loadInfoFromGooAndFace();
        mUpdateInfoGooAndFacePresenter = new UpdateInfoGooAndFacePresenter(this);
        mProgressDialog = new ProgressDialog(this);
        mSessionManagerUser = new SessionManagerUser(this);
        addEvents();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEvents() {

        edtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = getLayoutInflater().inflate(R.layout.gender_bottom_sheet, null);

                final Dialog mBottomSheetDialog = new Dialog(UpdateGooAndFaceActivity.this, R.style.MaterialDialogSheet);
                mBottomSheetDialog.setContentView(v);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();

                final TextView mTextViewMen = (TextView) v.findViewById(R.id.text_men);
                final TextView mTextViewWomen = (TextView) v.findViewById(R.id.text_women);

                mTextViewMen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String men = mTextViewMen.getText().toString();
                        edtGender.setText(men);
                        mBottomSheetDialog.dismiss();
                    }
                });

                mTextViewWomen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String wommen = mTextViewWomen.getText().toString();
                        edtGender.setText(wommen);
                        mBottomSheetDialog.dismiss();

                    }
                });
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameGoogle = edtFullName.getText().toString();
                mGender = edtGender.getText().toString();
                mPhoneName = edtNumber.getText().toString();
                mLocation = edtLocation.getText().toString();
                mFullName = "gm" + idUser;
                if (mGender.equals(getResources().getString(R.string.pro_file_gender_male))) {
                    iGender = 0;
                } else {
                    iGender = 1;
                }

                if (nameGoogle.trim().length() == 0 || mGender.length() == 0 || mPhoneName.trim().length() == 0 ||
                        mLocation.trim().length() == 0) {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.vui_long_dien_day_du), UpdateGooAndFaceActivity.this);
                } else {
//                    mProgressDialog.show();
//                    mProgressDialog.setCanceledOnTouchOutside(false);
                    showProgress();
                    mUpdateInfoGooAndFacePresenter.getLocaltionAddress(mLocation);
                }


            }
        });

    }

    public void loadInfoFromGooAndFace() {
        Intent iUpdate = getIntent();
        Bundle bUpdate = iUpdate.getBundleExtra("infoGoogle");
        emailGoogle = bUpdate.getString("emailGoogle");
        nameGoogle = bUpdate.getString("nameGoogle");
        if (nameGoogle == null) {
            nameGoogle = "";
        }
        idUser = bUpdate.getString("idUser");
        TokenId = bUpdate.getString("TokenId");
        deviceToken = bUpdate.getString("deviceToken");
        imageGoogle = bUpdate.getString("imageGoogle");
        ImageLoader.getInstance().loadImageAvatar(UpdateGooAndFaceActivity.this, imageGoogle,
                ivAvatar);
        edtEmail.setText(emailGoogle);
        edtFullName.setText(nameGoogle);
    }

    @Override
    public void displaySignUpAndSignIn(BodyResponse bodyResponse) {

    }

    @Override
    public void displayUpdate(DataUpdateResponse dataUpdateResponse) {

    }

    @Override
    public void displayError() {
        hideProgress();
    }

    @Override
    public void displayNotFoundLocaltion() {
        hideProgress();
        // mProgressDialog.dismiss();
        ShowAlertDialog.showAlert(getResources().getString(R.string.dia_chi_khong_tim_thay), UpdateGooAndFaceActivity.this);
    }

    @Override
    public void getLocaltionAddress(GeoCodeMapResponse geoCodeMapResponse) {
        mLat = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
        mLng = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
//        token = mDataHashUser.get(SessionManagerUser.KEY_TOKEN);
        if (mLat != 0 && mLng != 0) {
            mUpdateInfoGooAndFacePresenter.updateUserInfoGooAndFace(idUser, TokenId, deviceToken, emailGoogle, mFullName, mPhoneName
                    , nameGoogle, mLocation, mLat, mLng, iGender, imageGoogle);
        }

    }

    @Override
    public void displaySignInGooAndFace(BodyResponse bodyResponse) {
        hideProgress();
        //mProgressDialog.dismiss();
        if (bodyResponse.isStatus() == true) {
            mSessionManagerUser.createLoginSession(bodyResponse.getData());
            hashDataUser = mSessionManagerUser.getUserDetails();
            ApiClient.setToken(hashDataUser.get(SessionManagerUser.KEY_TOKEN));
            Intent intent = new Intent(UpdateGooAndFaceActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            clearCache();
            if (bodyResponse.getMessage().equals("DUPLICATED")) {
                ShowAlertDialog.showAlert(getResources().getString(R.string.email_duplicated), UpdateGooAndFaceActivity.this);
            }

        }
    }

    private void clearCache() {
        FirebaseAuth.getInstance().signOut();
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAuthController.install(this, this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
