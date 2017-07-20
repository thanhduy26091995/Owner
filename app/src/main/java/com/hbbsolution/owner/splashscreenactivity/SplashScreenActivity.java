package com.hbbsolution.owner.splashscreenactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.home.view.HomeActivity;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.more.viet_pham.View.signin.SignInActivity;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.work_management.presenter.QuickPostPresenter;
import com.hbbsolution.owner.work_management.view.quickpost.QuickPostView;

import static java.lang.Thread.sleep;

/**
 * Created by tantr on 6/1/2017.
 */

public class SplashScreenActivity extends AppCompatActivity implements QuickPostView{
    private QuickPostPresenter quickPostPresenter;
    public boolean destroyActivity = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        quickPostPresenter = new QuickPostPresenter(SplashScreenActivity.this);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    quickPostPresenter.getAllTypeJob();
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startAct();
                }
            }
        });
        thread.start();

    }

    private void startAct(){
        if(!destroyActivity) {
            SessionManagerUser sessionManagerUser = new SessionManagerUser(SplashScreenActivity.this);
            if (sessionManagerUser.isLoggedIn()) {
                Intent i = new Intent(SplashScreenActivity.this,
                        HomeActivity.class);
                startActivity(i);
                destroyActivity=true;
                finish();
            } else {
                Intent i = new Intent(SplashScreenActivity.this,
                        SignInActivity.class);
                startActivity(i);
                destroyActivity=true;
                finish();
            }
        }
    }
    @Override
    public void connectServerFail() {
    }

    @Override
    public void getAllTypeJob(TypeJobResponse typeJobResponse) {
        Constants.listTypeJob = typeJobResponse.getData();
    }

    @Override
    public void displayError(String error) {
    }
}
