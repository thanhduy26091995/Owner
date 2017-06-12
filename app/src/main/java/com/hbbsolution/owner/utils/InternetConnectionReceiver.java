package com.hbbsolution.owner.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.hbbsolution.owner.base.BaseActivity;

/**
 * Created by Administrator on 12/06/2017.
 */

public class InternetConnectionReceiver extends BroadcastReceiver {
    public static  boolean isInternetConnect;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo data = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean isWifiConnect = wifi.isConnectedOrConnecting();
        boolean isDataConnect = data.isConnectedOrConnecting();

        isInternetConnect = isWifiConnect || isDataConnect;

        if (InternetConnectionReceiver.isInternetConnect) {
            if (BaseActivity.isInternetConnect != isInternetConnect) {
                Toast.makeText(context, "Internet is connected", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (BaseActivity.isInternetConnect != isInternetConnect) {
                Toast.makeText(context, "Internet is not connected", Toast.LENGTH_SHORT).show();
            }
        }
        BaseActivity.isInternetConnect = isInternetConnect;
    }

}
