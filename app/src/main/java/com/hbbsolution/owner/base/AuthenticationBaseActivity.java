package com.hbbsolution.owner.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.home.prsenter.HomePresenter;
import com.hbbsolution.owner.home.view.HomeView;
import com.hbbsolution.owner.more.viet_pham.View.signin.SignInActivity;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowSnackbar;

/**
 * Created by tantr on 7/13/2017.
 */

public class AuthenticationBaseActivity extends AppCompatActivity implements HomeView {
    public static boolean isInternetConnect;
    protected ProgressDialog progressDialog;

    protected void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected void checkConnectionInterner() {
        if (!InternetConnection.getInstance().isOnline(this)) {
            ShowSnackbar.showSnack(this, getResources().getString(R.string.no_internet));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomePresenter mHomePresenter = new HomePresenter(this);
        mHomePresenter.requestCheckToken();
    }


    @Override
    protected void onStart() {
        super.onStart();
//        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        this.registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //this.unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void responseCheckToken() {
//        Toast.makeText(this, "unAuthorized", Toast.LENGTH_SHORT).show();
        if (this != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getResources().getString(R.string.notification));
            alertDialog.setMessage(getResources().getString(R.string.auth));
            alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SessionManagerUser sessionManagerUser = new SessionManagerUser(getApplicationContext());
                    sessionManagerUser.logoutUser();
                    Intent itBackSignIn = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(itBackSignIn);
                    finish();
                }
            });
            alertDialog.show();
        }
    }

    @Override
    public void errorConnectService() {

    }
}
