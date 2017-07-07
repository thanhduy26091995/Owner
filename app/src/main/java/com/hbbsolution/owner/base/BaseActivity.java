package com.hbbsolution.owner.base;

import android.app.ProgressDialog;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.maid_near_by.view.MaidNearByActivity;
import com.hbbsolution.owner.utils.ShowSnackbar;

/**
 * Created by Administrator on 12/06/2017.
 */

public class BaseActivity extends AppCompatActivity {
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
}
