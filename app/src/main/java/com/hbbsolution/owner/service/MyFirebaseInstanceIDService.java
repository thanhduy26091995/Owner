package com.hbbsolution.owner.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by buivu on 28/10/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "InstanceIDService";

    @Override
    public void onTokenRefresh() {

        try {
            String tokenRefresh = FirebaseInstanceId.getInstance().getToken();
            //cập nhật database
            Log.d("TOKEN", tokenRefresh);
        } catch (Exception e) {
            Log.e(TAG, "error");
        }
    }
}
