package com.hbbsolution.owner.more.viet_pham.base;

import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.OwnerApplication;

/**
 * Created by buivu on 03/07/2017.
 */

public class GoogleAuthController {
    private static GoogleAuthController instance;
    private GoogleApiClient mGoogleApiClient;
    private GoogleApiClient.Builder builder;

    private GoogleAuthController(FragmentActivity fragmentActivity, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        //config google
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(OwnerApplication.getInstance().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        builder = new GoogleApiClient.Builder(OwnerApplication.getInstance())
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .addApi(LocationServices.API)
                .enableAutoManage(fragmentActivity, onConnectionFailedListener);
        mGoogleApiClient = builder.build();


    }

    public static void install(FragmentActivity fragmentActivity, GoogleApiClient.OnConnectionFailedListener listener) {
        if (instance == null) {
            instance = new GoogleAuthController(fragmentActivity, listener);
        }
    }

    //hàm có kiểu trả về là Singleton
    public static GoogleAuthController getInstance() {
        return instance;
    }

    public void signOut(GoogleApiClient.ConnectionCallbacks callbacks) {
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(callbacks);
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }
}
