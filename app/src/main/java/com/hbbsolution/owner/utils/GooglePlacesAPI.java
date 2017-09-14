package com.hbbsolution.owner.utils;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * Created by buivu on 14/09/2017.
 */

public class GooglePlacesAPI {
    public static void showGooglePlaces(Activity activity, int requestCode) {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent myIntent;
            myIntent = builder.build(activity);
            activity.startActivityForResult(myIntent, requestCode);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
}
