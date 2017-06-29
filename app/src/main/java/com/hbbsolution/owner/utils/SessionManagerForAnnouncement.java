package com.hbbsolution.owner.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by buivu on 29/06/2017.
 */

public class SessionManagerForAnnouncement {
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_ANNOUNCEMENT = "Announcement";
    public static final String KEY_IS_HAS_ANNOUNCEMENT = "isHasAnnouncement";

    public SessionManagerForAnnouncement(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_ANNOUNCEMENT, PRIVATE_MODE);
        editor = pref.edit();
    }

    //create language session
    public void createStateAnnouncement(boolean state) {
        editor.putBoolean(KEY_IS_HAS_ANNOUNCEMENT, state);
        //commit
        editor.commit();
    }

    public boolean getStateAnnouncement() {
        return pref.getBoolean(KEY_IS_HAS_ANNOUNCEMENT, true);
    }

}
