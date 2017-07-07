package com.hbbsolution.owner.more.viet_pham.View.signin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.base.BaseActivity;
import com.hbbsolution.owner.base.OwnerApplication;
import com.hbbsolution.owner.home.view.HomeActivity;
import com.hbbsolution.owner.maid_near_by.view.MaidNearByActivity;
import com.hbbsolution.owner.more.phuc_tran.view.ForgotPassActivity;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.BodyResponse;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.DataUpdateResponse;
import com.hbbsolution.owner.more.viet_pham.Presenter.SignInGooAndFacePresenter;
import com.hbbsolution.owner.more.viet_pham.Presenter.SignInPresenter;
import com.hbbsolution.owner.more.viet_pham.View.MoreView;
import com.hbbsolution.owner.more.viet_pham.View.signup.SignUp1Activity;
import com.hbbsolution.owner.more.viet_pham.View.update_google_face.UpdateGooAndFaceActivity;
import com.hbbsolution.owner.more.viet_pham.base.GoogleAuthController;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 5/9/2017.
 */

public class SignInActivity extends BaseActivity implements MoreView, FirebaseAuth.AuthStateListener, GoogleApiClient.OnConnectionFailedListener {
    @BindView(R.id.toobar)
    Toolbar toolbar;
    @BindView(R.id.bt_work_around_here)
    Button btnWorkAroundHere;
    @BindView(R.id.bt_sign_in)
    Button btnSignIn;
    @BindView(R.id.bt_forget_password)
    Button btnForgetPassword;
    @BindView(R.id.bt_sign_up_now)
    Button btnSignUpNow;
    @BindView(R.id.edit_username)
    EditText editUserName;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.imb_facebook)
    ImageButton imbFacebook;
    @BindView(R.id.imb_google)
    ImageButton imbGoogle;
    private SignInPresenter mSignInPresenter;
    private SessionManagerUser sessionManagerUser;
    private OwnerApplication ownerApplication;
    private FirebaseAuth mFirebaseAuth;
    private LoginManager mLoginManager;
    private CallbackManager mCallbackManager;
    private HashMap<String, String> hashDataUser = new HashMap<>();
    public static final int CODE_SIGN_IN_GOOGLE = 1;
    public static int CHECK_AUTH = 101;
    public static GoogleApiClient mGoogleApiClient;
    private SignInGooAndFacePresenter mSignInGooAndFacePresenter;
    private ProgressDialog mProgressDialog;

    private String TokenID;
    private String IdUser;
    private String DeviceToken;
    private String emailGoogleOrFace;
    private String nameGoogleOrFace;
    private String imageGoogleOrFace;
    private List<String> listPermissonFacebook = Arrays.asList("email", "public_profile");
    private GoogleAuthController googleAuthController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        checkConnectionInterner();

        mCallbackManager = CallbackManager.Factory.create();
        mLoginManager = LoginManager.getInstance();
        sessionManagerUser = new SessionManagerUser(this);
        ownerApplication = new OwnerApplication();
        toolbar.setTitle("");
        mFirebaseAuth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        mProgressDialog = new ProgressDialog(this);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addEvents();

        mSignInGooAndFacePresenter = new SignInGooAndFacePresenter(this);
        mSignInPresenter = new SignInPresenter(this);
        // loginGoogle();

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addEvents() {
        // Sign up now
        btnSignUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUp1Activity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUserName.getText().toString();
                String password = editPassword.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.check_complete_all_information), SignInActivity.this);
                } else {
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();
                    String realToken = String.format("%s@//@android", deviceToken);
                    Log.d("DEVICE_TOKEN", realToken);
                    btnSignIn.setEnabled(false);
                    showProgress();
                    mSignInPresenter.signIn(username, password, realToken);
                }

            }
        });
        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itForgot = new Intent(SignInActivity.this, ForgotPassActivity.class);
                startActivity(itForgot);
            }
        });
        btnWorkAroundHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMaidNearBy = new Intent(SignInActivity.this, MaidNearByActivity.class);
                startActivity(intentMaidNearBy);
            }
        });

        imbGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleApiClient = GoogleAuthController.getInstance().getGoogleApiClient();
//                mProgressDialog.show();
//                mProgressDialog.setCanceledOnTouchOutside(false);
                signInGoogle(mGoogleApiClient);
            }
        });

        imbFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginManager.logInWithReadPermissions(SignInActivity.this, listPermissonFacebook);
                mLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        CHECK_AUTH = 102;
                        TokenID = loginResult.getAccessToken().getToken();
                        confirmSignInFireBase(TokenID);
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    IdUser = object.getString("id");
                                    nameGoogleOrFace = object.getString("name");
                                    emailGoogleOrFace = object.getString("email");
                                    imageGoogleOrFace = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                    DeviceToken = String.format("%s@//@android", FirebaseInstanceId.getInstance().getToken());
                                    Log.d("DEVICE_TOKEN", DeviceToken);
                                    mSignInGooAndFacePresenter.signInGooAndFace(IdUser, TokenID, DeviceToken);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Bundle parameter = new Bundle();
                        parameter.putString("fields", "id,name,gender,email,picture");
                        request.setParameters(parameter);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void displaySignUpAndSignIn(BodyResponse bodyResponse) {
        btnSignIn.setEnabled(true);
        hideProgress();
        if (bodyResponse.getStatus() == true) {
                //save session
                sessionManagerUser.createLoginSession(bodyResponse.getData());
                hashDataUser = sessionManagerUser.getUserDetails();
                ApiClient.setToken(hashDataUser.get(SessionManagerUser.KEY_TOKEN));
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
        } else {
            ShowAlertDialog.showAlert(getResources().getString(R.string.invalid), SignInActivity.this);
//            if(bodyResponse.getMessage().equals("DATA_NOT_EXISTS")|| bodyResponse.getMessage().equals("INVALID_PASSWORD")){
////                ShowAlertDialog.showAlert(getResources().getString(R.string.forgot_password_failed), SignInActivity.this);
//            }
        }
    }

    @Override
    public void displayUpdate(DataUpdateResponse dataUpdateResponse) {

    }


    @Override
    public void displayError() {

    }

    @Override
    public void displayNotFoundLocaltion() {

    }

    @Override
    public void getLocaltionAddress(GeoCodeMapResponse geoCodeMapResponse) {

    }

    @Override
    public void displaySignInGooAndFace(BodyResponse bodyResponse) {
        hideProgress();
        if (bodyResponse.isStatus() == true) {
            sessionManagerUser.createLoginSession(bodyResponse.getData());
            hashDataUser = sessionManagerUser.getUserDetails();
            ApiClient.setToken(hashDataUser.get(SessionManagerUser.KEY_TOKEN));
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            pushInforGoogleAndFace();
        }
    }

    public void pushInforGoogleAndFace() {
        Bundle bUpdate = new Bundle();
        bUpdate.putString("idUser", IdUser);
        bUpdate.putString("TokenId", TokenID);
        bUpdate.putString("deviceToken", DeviceToken);
        bUpdate.putString("emailGoogle", emailGoogleOrFace);
        bUpdate.putString("imageGoogle", imageGoogleOrFace);
        bUpdate.putString("nameGoogle", nameGoogleOrFace);
        Intent iUpdate = new Intent(SignInActivity.this, UpdateGooAndFaceActivity.class);
        iUpdate.putExtra("infoGoogle", bUpdate);
        startActivity(iUpdate);
       // finish();
    }

    private void loginGoogle() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

    }

    private void signInGoogle(GoogleApiClient apiClient) {
        CHECK_AUTH = 101;
        Intent iDNGoogle = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(iDNGoogle, CODE_SIGN_IN_GOOGLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_SIGN_IN_GOOGLE) {
            if (resultCode == RESULT_OK) {
                showProgress();
                GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
                TokenID = googleSignInAccount.getIdToken();
                IdUser = googleSignInAccount.getId();
                DeviceToken = String.format("%s@//@android", FirebaseInstanceId.getInstance().getToken());
                Log.d("DEVICE_TOKEN", DeviceToken);
                emailGoogleOrFace = googleSignInAccount.getEmail();
                nameGoogleOrFace = googleSignInAccount.getDisplayName();
                if (googleSignInAccount.getPhotoUrl() != null) {
                    imageGoogleOrFace = googleSignInAccount.getPhotoUrl().toString();
                }
                else{
                    imageGoogleOrFace = "";
                }
                mSignInGooAndFacePresenter.signInGooAndFace(IdUser, TokenID, DeviceToken);
                confirmSignInFireBase(TokenID);
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void confirmSignInFireBase(String TokenID) {
        // Confirm google
        if (CHECK_AUTH == 101) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(TokenID, null);
            mFirebaseAuth.signInWithCredential(authCredential);

        } else if (CHECK_AUTH == 102) {
            AuthCredential authCredential = FacebookAuthProvider.getCredential(TokenID);
            mFirebaseAuth.signInWithCredential(authCredential);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(this);
        GoogleAuthController.install(this, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {

        }
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
