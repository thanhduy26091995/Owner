package com.hbbsolution.owner.home.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.BaseActivity;
import com.hbbsolution.owner.history.view.HistoryActivity;
import com.hbbsolution.owner.home.prsenter.HomePresenter;
import com.hbbsolution.owner.maid_near_by.view.MaidNearByActivity;
import com.hbbsolution.owner.more.viet_pham.View.MoreActivity;
import com.hbbsolution.owner.more.viet_pham.View.signin.SignInActivity;
import com.hbbsolution.owner.utils.SessionManagerForLanguage;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.view.workmanager.WorkManagementActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements HomeView, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.home_title_toothbar)
    TextView txtHome_title_toothbar;
    @BindView(R.id.lo_maid_around)
    RelativeLayout mLayout_MaidAround;
    @BindView(R.id.lo_your_tasks)
    RelativeLayout mLayout_YourTasks;
    @BindView(R.id.lo_history)
    RelativeLayout mLayout_History;
    @BindView(R.id.txt_work_management)
    TextView txt_work_management;
    @BindView(R.id.txt_work_management_history)
    TextView txt_work_management_history;
    @BindView(R.id.txt_work_maid_around)
    TextView txt_work_maid_around;
    private SessionManagerForLanguage sessionManagerForLanguage;
    private boolean isPause = false;
    private HomePresenter mHomePresenter;
    private SessionManagerUser sessionManagerUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // setup toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtHome_title_toothbar.setText(getResources().getString(R.string.home_home));

        // on click
        mLayout_MaidAround.setOnClickListener(this);
        mLayout_YourTasks.setOnClickListener(this);
        mLayout_History.setOnClickListener(this);
        sessionManagerForLanguage = new SessionManagerForLanguage(this);
        String lang = sessionManagerForLanguage.getLanguage();
//        if (lang.equals("Tiếng Việt")) {
////            RelativeLayout.LayoutParams  parameter =  (RelativeLayout.LayoutParams) txt_work_management.getLayoutParams();
////            parameter.setMargins(getResources().geti(R.dimen.home_margin_work), parameter.topMargin, parameter.rightMargin, parameter.bottomMargin); // left, top, right, bottom
////            txtField.setLayoutParams(parameter);
//            txt_work_management.setPadding(20, 0, 20, 5);
//            txt_work_management_history.setPadding(15, 0, 15, 5);
//        } else if (lang.equals("English")) {
//            txt_work_management.setPadding(15, 0, 15, 5);
//            txt_work_management_history.setPadding(5, 0, 5, 5);
//        }

        if (lang.equals("Tiếng Việt")) {
            txt_work_maid_around.setText(changeCharInPosition(setTitle(txt_work_maid_around.getText().toString(),2),'\n',txt_work_maid_around.getText().toString()));
            txt_work_management.setText(changeCharInPosition(setTitle(txt_work_management.getText().toString(),2),'\n',txt_work_management.getText().toString()));
            txt_work_management_history.setText(changeCharInPosition(setTitle(txt_work_management_history.getText().toString(),2),'\n',txt_work_management_history.getText().toString()));
        } else{
            txt_work_maid_around.setText(changeCharInPosition(setTitle(txt_work_maid_around.getText().toString(),1),'\n',txt_work_maid_around.getText().toString()));
            txt_work_management.setText(changeCharInPosition(setTitle(txt_work_management.getText().toString(),1),'\n',txt_work_management.getText().toString()));
            txt_work_management_history.setText(changeCharInPosition(setTitle(txt_work_management_history.getText().toString(),1),'\n',txt_work_management_history.getText().toString()));
        }
        mHomePresenter = new HomePresenter(this);
        sessionManagerUser = new SessionManagerUser(HomeActivity.this);
        mHomePresenter.requestCheckToken();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actio_more) {
            transActivity(MoreActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.lo_maid_around:
                transActivity(MaidNearByActivity.class);
                finish();
                //ShowToast("Maid Around");
                break;
            case R.id.lo_your_tasks:
                transActivity(WorkManagementActivity.class);
                finish();
                break;
            case R.id.lo_history:
                transActivity(HistoryActivity.class);
                finish();
                break;
        }
    }

    // Transition Activity
    private void transActivity(Class activity) {
        Intent itTransActivity = new Intent(HomeActivity.this, activity);
        startActivity(itTransActivity);
    }

    private void ShowToast(String msg) {
        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void responseCheckToken() {
        if (HomeActivity.this != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getResources().getString(R.string.notification));
            alertDialog.setMessage(getResources().getString(R.string.auth));
            alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sessionManagerUser.logoutUser();
                    Intent itBackSignIn = new Intent(HomeActivity.this, SignInActivity.class);
                    startActivity(itBackSignIn);
                    finish();
                }
            });
            alertDialog.show();
        }
    }

    @Override
    public void errorConnectService() {
        ShowAlertDialog.showAlert(getResources().getString(R.string.thatbai), HomeActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPause) {
            SessionManagerForLanguage sessionManagerForLanguage = new SessionManagerForLanguage(HomeActivity.this);
            boolean isChangeLanguage = sessionManagerForLanguage.changeLanguage();
            if (isChangeLanguage) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(this.getIntent());
                overridePendingTransition(0, 0);
            }
        }
    }

    private int setTitle(String title,int positionSpace)
    {
        int i = 0,spaceCount = 0;
        while( i < title.length() && spaceCount <positionSpace ){
            if( title.charAt(i) == ' ' ) {
                spaceCount++;
            }
            i++;
        }
        return i-1;
    }

    public String changeCharInPosition(int position, char ch, String str){
        char[] charArray = str.toCharArray();
        charArray[position] = ch;
        return new String(charArray);
    }
}
