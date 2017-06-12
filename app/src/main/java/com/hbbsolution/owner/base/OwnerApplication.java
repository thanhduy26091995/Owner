package com.hbbsolution.owner.base;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import com.hbbsolution.owner.api.ApiClient;
import com.hbbsolution.owner.utils.InternetConnectionReceiver;
import com.hbbsolution.owner.utils.SessionManagerForLanguage;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by buivu on 28/04/2017.
 */

public class OwnerApplication extends Application {
    private static OwnerApplication instance;
    public static InternetConnectionReceiver receiver;
    private HashMap<String, String> hashDataUser = new HashMap<>();
    SessionManagerUser sessionManagerUser;
    public static OwnerApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new FontAwesomeModule());
        instance = this;
        receiver = new InternetConnectionReceiver();
        BaseActivity.isInternetConnect=InternetConnectionReceiver.isInternetConnect;
        setLocale();
        sessionManagerUser = new SessionManagerUser(this);
        if (sessionManagerUser.isLoggedIn()) {
            hashDataUser = sessionManagerUser.getUserDetails();
//            setToken(hashDataUser.get(SessionManagerUser.KEY_TOKEN));
        }
        // register to be informed of activities starting up
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLocale();
    }

    private void setLocale() {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getLocale();
        if (!configuration.locale.equals(locale)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(locale);
            }
            resources.updateConfiguration(configuration, null);
        }
    }

    private void setToken(String token)
    {
        ApiClient.setToken(token);
    }

    private Locale getLocale() {
        SessionManagerForLanguage sessionManagerForLanguage = new SessionManagerForLanguage(this);
        String language = sessionManagerForLanguage.getLanguage();
        switch (language) {
            case "Tiếng Việt":
                ApiClient.setLanguage("vi");
                language = "vi";
                break;

            case "English":
                ApiClient.setLanguage("en");
                language = "en";
                break;
        }
        return new Locale(language);
    }
}
