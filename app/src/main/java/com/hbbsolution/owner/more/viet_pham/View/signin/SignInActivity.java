package com.hbbsolution.owner.more.viet_pham.View.signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.common.api.GoogleApiClient;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.base.OwnerApplication;
import com.hbbsolution.owner.home.view.HomeActivity;
import com.hbbsolution.owner.maid_near_by.view.MaidNearByActivity;
import com.hbbsolution.owner.more.phuc_tran.ForgotPassActivity;
import com.hbbsolution.owner.more.viet_pham.Model.BodyResponse;
import com.hbbsolution.owner.more.viet_pham.Model.UpdateResponse;
import com.hbbsolution.owner.more.viet_pham.Presenter.SignInPresenter;
import com.hbbsolution.owner.more.viet_pham.View.MoreView;
import com.hbbsolution.owner.more.viet_pham.View.signup.SignUp1Activity;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 5/9/2017.
 */

public class SignInActivity extends AppCompatActivity implements MoreView
//        , GoogleApiClient.OnConnectionFailedListener
{
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
    private HashMap<String, String> hashDataUser = new HashMap<>();

    public static GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private static final int RC_SIGN_IN = 007;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        sessionManagerUser = new SessionManagerUser(this);
        ownerApplication = new OwnerApplication();
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addEvents();

        mSignInPresenter = new SignInPresenter(this);
  //      loginGoogle();

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

                mSignInPresenter.signIn(username, password);
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
 //               signIn();
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
    public void displayUpdate(UpdateResponse updateResponse) {

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

    //LogIn_google----Start---
//    private void loginGoogle() {
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
////
////        // Tùy biến nút đăng nhập Google plus
////        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
////        btnSignIn.setScopes(gso.getScopeArray());
//    }
//
//
//    //===============google=====================
//    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    //===============google=====================
//    private void signOut() {
//        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(Status status) {
////                        updateUI(false);
//                    }
//                });
//    }
//
//    //===============google=====================
//    private void revokeAccess() {
//        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(Status status) {
////                        updateUI(false);
//                    }
//                });
//    }
//
//    //===============google=====================
//    private void handleSignInResult(GoogleSignInResult result) {
//        Log.d("TAG", "handleSignInResult:" + result.isSuccess());
//        if (result.isSuccess()) {
//            //Cập nhật giao diện khi đăng nhập thành công
//            GoogleSignInAccount acct = result.getSignInAccount();
//
//            Log.e("TAG", "display name: " + acct.getDisplayName());
//
////            String personName = acct.getDisplayName();
////            String personPhotoUrl = acct.getPhotoUrl().toString();
////            String email = acct.getEmail();
//
////            Log.e("TAG", "Name: " + personName + ", email: " + email
////                    + ", Image: " + personPhotoUrl);
//            hideProgressDialog();
//
//            Intent iHome = new Intent(SignInActivity.this, HomeActivity.class);
//            startActivity(iHome);
//
//        } else {
//            // Signed out, show unauthenticated UI.
////            updateUI(false);
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // Nhập kết quả trả về khi đăng nhập
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        //===============google=====================
//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if (opr.isDone()) {
//            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
//            // and the GoogleSignInResult will be available instantly.
//            Log.d("TAG", "Got cached sign-in");
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
//        } else {
//            // If the user has not previously signed in on this device or the sign-in has expired,
//            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
//            // single sign-on will occur in this branch.
//            showProgressDialog();
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
//                    handleSignInResult(googleSignInResult);
//                }
//            });
//            hideProgressDialog();
//        }
//        //===============google=====================
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.d("TAG", "onConnectionFailed:" + connectionResult);
//    }
//
//    private void showProgressDialog() {
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage("Loading...");
//            mProgressDialog.setIndeterminate(true);
//        }
//
//        mProgressDialog.show();
//    }
//
//    //===============google=====================
//    private void hideProgressDialog() {
//        if (mProgressDialog != null && mProgressDialog.isShowing()) {
//            mProgressDialog.hide();
//        }
//    }
//

    //LogIn_google----End---

}
