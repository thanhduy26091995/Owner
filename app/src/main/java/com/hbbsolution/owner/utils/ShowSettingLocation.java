package com.hbbsolution.owner.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import com.hbbsolution.owner.R;

/**
 * Created by buivu on 08/09/2017.
 */

public class ShowSettingLocation {
    public static void showSettingLocationAlert(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //set title
        builder.setTitle(activity.getResources().getString(R.string.GPSTitle));
        //set message
        builder.setMessage(activity.getResources().getString(R.string.GPSContent));
        //on press
        builder.setPositiveButton(activity.getResources().getString(R.string.setting), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent settingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivity(settingIntent);
            }
        });
        //on cancel
        builder.setNegativeButton(activity.getResources().getString(R.string.huy), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
