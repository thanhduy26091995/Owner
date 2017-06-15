package com.hbbsolution.owner.base;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hbbsolution.owner.utils.InternetConnectionReceiver;

import static com.hbbsolution.owner.base.OwnerApplication.receiver;

/**
 * Created by Administrator on 12/06/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public static boolean isInternetConnect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (InternetConnectionReceiver.isInternetConnect) {
            Toast.makeText(getBaseContext(), "Internet is connected", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(getBaseContext(), "Internet is not connected", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
