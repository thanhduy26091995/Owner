package com.hbbsolution.owner.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 29/08/2017.
 */

public class SessionShortcutBadger {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "shortcutBadger";

    private static final String IS_COUNT = "IsCount";
    public static final String COUNT_SHORTCUT_BADGER = "count";

    // Constructor
    public SessionShortcutBadger(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    public void createCountSession(int count) {
        editor.putBoolean(IS_COUNT, true);
        editor.putInt(COUNT_SHORTCUT_BADGER, count);
        //commit
        editor.commit();
    }

    public int getCount() {
        return pref.getInt(COUNT_SHORTCUT_BADGER, 0);
    }

    public void removeCount() {
        editor.clear();
        editor.commit();
    }

    public void setCount(int count){
        editor.putInt(COUNT_SHORTCUT_BADGER, count);
        //commit
        editor.commit();
    }
    // Get Login State
    public boolean isCounted() {
        return pref.getBoolean(IS_COUNT, false);
    }
}
