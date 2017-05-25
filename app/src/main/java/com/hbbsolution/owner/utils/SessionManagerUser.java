package com.hbbsolution.owner.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hbbsolution.owner.more.viet_pham.Model.User;
import com.hbbsolution.owner.more.viet_pham.View.SignInActivity;

import java.util.HashMap;

/**
 * Created by DOBYNT on 18/02/2017.
 */

public class SessionManagerUser {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "User";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME = "name";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_PHONE = "phone";

    // Constructor
    public SessionManagerUser(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /**
     * Create login session
     */
    public void createLoginSession(User user) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing in pref
        editor.putString(KEY_ID, user.get_id());
        editor.putString(KEY_USERNAME, user.getInfo().getUsername());
        editor.putString(KEY_NAME, user.getInfo().getName());
        editor.putInt(KEY_GENDER, user.getInfo().getGender());
        editor.putString(KEY_ADDRESS, user.getInfo().getAddress().getName());
        editor.putLong(KEY_LAT, Double.doubleToRawLongBits(user.getInfo().getAddress().getCoordinates().getLat()));
        editor.putLong(KEY_LNG, Double.doubleToRawLongBits(user.getInfo().getAddress().getCoordinates().getLng()));
        editor.putString(KEY_ADDRESS, user.getInfo().getAddress().getName());
        editor.putString(KEY_PHONE, user.getInfo().getPhone());
        editor.putString(KEY_EMAIL, user.getInfo().getEmail());
        editor.putString(KEY_AVATAR, user.getInfo().getImage());
        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */

    public boolean checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, SignInActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
            return false;
        }
        return true;
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name

        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_GENDER, String.valueOf(pref.getInt(KEY_GENDER, 0)));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        user.put(KEY_LAT,String.valueOf(pref.getLong(KEY_LAT,0)));
        user.put(KEY_LNG,String.valueOf(pref.getLong(KEY_LNG,0)));

        user.put(KEY_AVATAR, pref.getString(KEY_AVATAR, null));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
//        Intent i = new Intent(_context, SignInActivity.class);
//        // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // Add new Flag to start new Activity
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        // Staring Login Activity
//        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
