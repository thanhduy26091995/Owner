package com.hbbsolution.owner.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hbbsolution.owner.R;


/**
 * Created by buivu on 03/03/2017.
 */

public class SessionManagerForLanguage {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_LANGUAGE = "Language";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_IS_CLICK_LANGUAGE = "clickedLanguage";
    public SessionManagerForLanguage(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_LANGUAGE, PRIVATE_MODE);
        editor = pref.edit();
    }

    //create language session
    public void createLanguageSession(String language) {
        editor.putString(KEY_LANGUAGE, language);
        //commit
        editor.commit();
    }

    public String getLanguage() {
        return pref.getString(KEY_LANGUAGE, _context.getResources().getString(R.string.default_language));
    }
    public void setIsClickedLanguage(boolean isFinished) {
        editor.putBoolean(KEY_IS_CLICK_LANGUAGE, isFinished);
        //commit
        editor.commit();
    }

    public void setChangeLanguage(){
        editor.putBoolean(KEY_IS_CLICK_LANGUAGE, false);
    }
    public boolean changeLanguage() {
        return pref.getBoolean(KEY_IS_CLICK_LANGUAGE, true);
    }
}
