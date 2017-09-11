package com.hbbsolution.owner.home.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.TypeJobAdapter;
import com.hbbsolution.owner.base.AuthenticationBaseActivity;
import com.hbbsolution.owner.base.ImageLoader;
import com.hbbsolution.owner.history.view.HistoryActivity;
import com.hbbsolution.owner.home.prsenter.HomePresenter;
import com.hbbsolution.owner.maid_near_by_new_version.view.MaidNearByNewActivity;
import com.hbbsolution.owner.model.TypeJob;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.more.viet_pham.View.MoreActivity;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.SessionManagerForLanguage;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.SessionShortcutBadger;
import com.hbbsolution.owner.work_management.presenter.QuickPostPresenter;
import com.hbbsolution.owner.work_management.view.quickpost.QuickPostView;
import com.hbbsolution.owner.work_management.view.workmanager.WorkManagementActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.leolin.shortcutbadger.ShortcutBadger;

public class HomeActivity extends AuthenticationBaseActivity implements HomeView, View.OnClickListener, QuickPostView {


    @BindView(R.id.lo_maid_around)
    Button mLayout_MaidAround;
    @BindView(R.id.lo_your_tasks)
    Button mLayout_YourTasks;
    @BindView(R.id.lo_history)
    Button mLayout_History;
    @BindView(R.id.imgAvatar)
    CircleImageView imgAvatar;
//    @BindView(R.id.rcv_type_job)
//    RecyclerView rcv_type_job;

    private RecyclerView rcv_type_job;

    private SessionManagerForLanguage sessionManagerForLanguage;
    private boolean isPause = false;
    private HomePresenter mHomePresenter;
    private SessionManagerUser sessionManagerUser;
    private QuickPostPresenter quickPostPresenter;
    private List<String> listTypeJobName = new ArrayList<>();
    private List<TypeJob> listTypeJob = new ArrayList<>();

    private TypeJobAdapter typeJobAdapter;
    private boolean isChangeLanguage = false;

    private SessionShortcutBadger sessionShortcutBadger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //Clear shortcutBadger
        sessionShortcutBadger = new SessionShortcutBadger(this);
        sessionShortcutBadger.removeCount();
        ShortcutBadger.removeCount(this); //for 1.1.4+

        rcv_type_job = (RecyclerView) findViewById(R.id.rcv_type_job);
        // setup toolbar
        checkConnectionInterner();
        // on click
        mLayout_MaidAround.setOnClickListener(this);
        mLayout_YourTasks.setOnClickListener(this);
        mLayout_History.setOnClickListener(this);
        imgAvatar.setOnClickListener(this);

        sessionManagerForLanguage = new SessionManagerForLanguage(this);
        String lang = sessionManagerForLanguage.getLanguage();


        sessionManagerForLanguage = new SessionManagerForLanguage(HomeActivity.this);
        mHomePresenter = new HomePresenter(this);
        sessionManagerUser = new SessionManagerUser(HomeActivity.this);
        mHomePresenter.requestCheckToken();
        quickPostPresenter = new QuickPostPresenter(this);
//
        if (Constants.listTypeJob.size() > 0) {
            rcv_type_job.setVisibility(View.VISIBLE);
            this.listTypeJob = Constants.listTypeJob;
            compareValueInModel(this.listTypeJob);
            for (TypeJob typeJob : this.listTypeJob) {
                listTypeJobName.add(typeJob.getName());
            }
//            setRecyclerView();
        } else {
            quickPostPresenter.getAllTypeJob();
        }
        setRecyclerView();

        //Set Avatar
        ImageLoader.getInstance().loadImageAvatar(HomeActivity.this,sessionManagerUser.getUserDetails().get(SessionManagerUser.KEY_IMAGE),imgAvatar);

    }

    private void setRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
//                int mod = position % 6;
                if (position == 0)
                    return 2;
                else
                    return 1;
            }
        });
        rcv_type_job.setLayoutManager(layoutManager);
        typeJobAdapter = new TypeJobAdapter(HomeActivity.this, this.listTypeJob);

        typeJobAdapter.notifyDataSetChanged();
        rcv_type_job.setAdapter(typeJobAdapter);
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
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.lo_maid_around:
                transActivity(MaidNearByNewActivity.class);
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
            case R.id.imgAvatar:
                transActivity(MoreActivity.class);
                break;
        }
    }

    // Transition Activity
    private void transActivity(Class activity) {
        Intent itTransActivity = new Intent(HomeActivity.this, activity);
        startActivity(itTransActivity);
    }

    @Override
    public void responseCheckToken() {
        super.responseCheckToken();
    }

    @Override
    public void errorConnectService() {
//        ShowAlertDialog.showAlert(getResources().getString(R.string.no_internet), HomeActivity.this);
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
//            SessionManagerForLanguage sessionManagerForLanguage = new SessionManagerForLanguage(HomeActivity.this);
            isChangeLanguage = sessionManagerForLanguage.changeLanguage();
            if (isChangeLanguage) {
                Constants.listTypeJob.clear();
                finish();
                overridePendingTransition(0, 0);
                startActivity(this.getIntent());
                overridePendingTransition(0, 0);
            }
        }
    }

    private int setTitle(String title, int positionSpace) {
        int i = 0, spaceCount = 0;
        while (i < title.length() && spaceCount < positionSpace) {
            if (title.charAt(i) == ' ') {
                spaceCount++;
            }
            i++;
        }
        return i - 1;
    }

    public String changeCharInPosition(int position, char ch, String str) {
        char[] charArray = str.toCharArray();
        charArray[position] = ch;
        return new String(charArray);
    }

    private List<TypeJob> compareValueInModel(List<TypeJob> list) {
        Collections.sort(list, new Comparator<TypeJob>() {
            public int compare(TypeJob obj1, TypeJob obj2) {
                return Integer.valueOf((int) obj1.getWeight()).compareTo((int) obj2.getWeight()); // To compare integer values
            }
        });
        return list;
    }

    @Override
    public void connectServerFail() {

    }

    @Override
    public void getAllTypeJob(TypeJobResponse typeJobResponse) {
        if (typeJobResponse.getStatus()) {
            Constants.listTypeJob = typeJobResponse.getData();
            rcv_type_job.setVisibility(View.VISIBLE);
            if (Constants.listTypeJob.size() == 0) {
                Constants.listTypeJob = typeJobResponse.getData();
                this.listTypeJob = Constants.listTypeJob;
                compareValueInModel(this.listTypeJob);
                for (TypeJob typeJob : Constants.listTypeJob) {
                    listTypeJobName.add(typeJob.getName());
                }
            } else {
                this.listTypeJob = Constants.listTypeJob;
                compareValueInModel(this.listTypeJob);
                for (TypeJob typeJob : Constants.listTypeJob) {
                    listTypeJobName.add(typeJob.getName());
                }
            }
            typeJobAdapter = new TypeJobAdapter(HomeActivity.this, Constants.listTypeJob);
            typeJobAdapter.notifyDataSetChanged();
            rcv_type_job.setAdapter(typeJobAdapter);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getResources().getString(R.string.notification));
            alertDialog.setMessage(getResources().getString(R.string.home_error_type_job));
            alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alertDialog.show();
        }

    }

    @Override
    public void displayError(String error) {

    }
}
