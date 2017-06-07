package com.hbbsolution.owner.splashscreenactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.home.view.HomeActivity;
import com.hbbsolution.owner.more.viet_pham.View.signin.SignInActivity;
import com.hbbsolution.owner.utils.SessionManagerUser;

import static java.lang.Thread.sleep;

/**
 * Created by tantr on 6/1/2017.
 */

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SessionManagerUser sessionManagerUser = new SessionManagerUser(SplashScreenActivity.this);
                    if(sessionManagerUser.isLoggedIn()){
                        Intent i = new Intent(SplashScreenActivity.this,
                                HomeActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Intent i = new Intent(SplashScreenActivity.this,
                                SignInActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
        thread.start();
    }
}
