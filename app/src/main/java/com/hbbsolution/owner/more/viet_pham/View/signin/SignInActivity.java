package com.hbbsolution.owner.more.viet_pham.View.signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.base.OwnerApplication;
import com.hbbsolution.owner.home.view.HomeActivity;
import com.hbbsolution.owner.maid_near_by.view.MaidNearByActivity;
import com.hbbsolution.owner.more.phuc_tran.ForgotPassActivity;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.BodyResponse;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.DataUpdateResponse;
import com.hbbsolution.owner.more.viet_pham.Presenter.SignInGooAndFacePresenter;
import com.hbbsolution.owner.more.viet_pham.Presenter.SignInPresenter;
import com.hbbsolution.owner.more.viet_pham.View.MoreView;
import com.hbbsolution.owner.more.viet_pham.View.signup.SignUp1Activity;
import com.hbbsolution.owner.more.viet_pham.View.update_google_face.UpdateGooAndFaceActivity;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 5/9/2017.
 */

public class SignInActivity extends AppCompatActivity implements MoreView, FirebaseAuth.AuthStateListener {
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
    private HashMap<String, String> hashDataUser = new HashMap<>();
    public static final int CODE_SIGN_IN_GOOGLE = 1;
    public static int CHECK_AUTH_PROVIDER_GOOGLE = 101;
    public static GoogleApiClient mGoogleApiClient;
    private SignInGooAndFacePresenter mSignInGooAndFacePresenter;
    private ProgressDialog mProgressDialog;
    private String TokenID ;
    private String IdUser ;
    private String DeviceToken ;
    private String emailGoogle ;
    private String nameGoogle ;
    private String imageGoogle ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        sessionManagerUser = new SessionManagerUser(this);
        ownerApplication = new OwnerApplication();
        toolbar.setTitle("");
        mFirebaseAuth  = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addEvents();

        mSignInGooAndFacePresenter = new SignInGooAndFacePresenter(this);
        mSignInPresenter = new SignInPresenter(this);
        loginGoogle();
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
                String deviceToken = FirebaseInstanceId.getInstance().getToken();
                btnSignIn.setEnabled(false);
                mSignInPresenter.signIn(username, password, deviceToken);
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
                SignInGoogle(mGoogleApiClient);
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
            ShowAlertDialog.showAlert(bodyResponse.getMessage().toString(), SignInActivity.this);
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
    public void displaySignInGooAndFace(DataUpdateResponse dataUpdateResponse) {
        if (dataUpdateResponse.isStatus() == true)
        {
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else {
            Bundle bUpdate = new Bundle();
            bUpdate.putString("idUser",IdUser);
            bUpdate.putString("TokenId",TokenID);
            bUpdate.putString("deviceToken",DeviceToken);
            bUpdate.putString("emailGoogle",emailGoogle);
            bUpdate.putString("imageGoogle",imageGoogle);
            bUpdate.putString("nameGoogle",nameGoogle);
            Intent iUpdate = new Intent(SignInActivity.this, UpdateGooAndFaceActivity.class);
            iUpdate.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            iUpdate.putExtra("infoGoogle",bUpdate);
            startActivity(iUpdate);
            finish();
        }
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
    private void SignInGoogle(GoogleApiClient apiClient) {
        CHECK_AUTH_PROVIDER_GOOGLE = 101;
        Intent iDNGoogle = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(iDNGoogle, CODE_SIGN_IN_GOOGLE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
                TokenID = googleSignInAccount.getIdToken();
                IdUser = googleSignInAccount.getId();
                DeviceToken = FirebaseInstanceId.getInstance().getToken();
                emailGoogle = googleSignInAccount.getEmail();
                nameGoogle = googleSignInAccount.getDisplayName();
                imageGoogle = googleSignInAccount.getPhotoUrl().toString();
                mSignInGooAndFacePresenter.signInGooAndFace(IdUser,TokenID,DeviceToken);
                ConfirmSignInFireBase(TokenID);
            }
        }
    }

    private void ConfirmSignInFireBase(String TokenID) {
        // Confirm google
        if (CHECK_AUTH_PROVIDER_GOOGLE == 101) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(TokenID, null);
            mFirebaseAuth.signInWithCredential(authCredential);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){

        }
    }

}
