package com.hbbsolution.owner.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.hbbsolution.owner.R;


/**
 * Created by buivu on 14/01/2017.
 */
public class ShowAlertDialog {

    public static ProgressDialog mProgressDialog;

    public static void showAlert(String mess, Context context) {
        if (context != null) {
            try {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage(mess);
                alertDialogBuilder.setPositiveButton(context.getResources().getText(R.string.okAlert),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                alertDialogBuilder.create().dismiss();
                            }

                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } catch (Exception e) {

            }
        }

    }
}
