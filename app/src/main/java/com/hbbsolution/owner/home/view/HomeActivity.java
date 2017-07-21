package com.hbbsolution.owner.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.TypeJobAdapter;
import com.hbbsolution.owner.base.AuthenticationBaseActivity;
import com.hbbsolution.owner.history.view.HistoryActivity;
import com.hbbsolution.owner.home.prsenter.HomePresenter;
import com.hbbsolution.owner.maid_near_by.view.MaidNearByActivity;
import com.hbbsolution.owner.model.TypeJob;
import com.hbbsolution.owner.more.viet_pham.View.MoreActivity;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.SessionManagerForLanguage;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.presenter.QuickPostPresenter;
import com.hbbsolution.owner.work_management.view.quickpost.QuickPostActivity;
import com.hbbsolution.owner.work_management.view.workmanager.WorkManagementActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AuthenticationBaseActivity implements HomeView, View.OnClickListener{

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
    @BindView(R.id.rcv_type_job)
    RecyclerView rcv_type_job;
    private SessionManagerForLanguage sessionManagerForLanguage;
    private boolean isPause = false;
    private HomePresenter mHomePresenter;
    private SessionManagerUser sessionManagerUser;
    private QuickPostPresenter quickPostPresenter;
    private List<String> listTypeJobName = new ArrayList<>();
    private List<TypeJob> listTypeJob = new ArrayList<>();

    private TypeJobAdapter typeJobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // setup toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtHome_title_toothbar.setText(getResources().getString(R.string.home_home));
        checkConnectionInterner();
        // on click
        mLayout_MaidAround.setOnClickListener(this);
        mLayout_YourTasks.setOnClickListener(this);
        mLayout_History.setOnClickListener(this);
        sessionManagerForLanguage = new SessionManagerForLanguage(this);
        String lang = sessionManagerForLanguage.getLanguage();

        if (lang.equals("Tiếng Việt")) {
            txt_work_maid_around.setText(changeCharInPosition(setTitle(txt_work_maid_around.getText().toString(), 2), '\n', txt_work_maid_around.getText().toString()));
            txt_work_management.setText(changeCharInPosition(setTitle(txt_work_management.getText().toString(), 2), '\n', txt_work_management.getText().toString()));
            txt_work_management_history.setText(changeCharInPosition(setTitle(txt_work_management_history.getText().toString(), 2), '\n', txt_work_management_history.getText().toString()));
        } else {
            txt_work_maid_around.setText(changeCharInPosition(setTitle(txt_work_maid_around.getText().toString(), 1), '\n', txt_work_maid_around.getText().toString()));
            txt_work_management.setText(changeCharInPosition(setTitle(txt_work_management.getText().toString(), 1), '\n', txt_work_management.getText().toString()));
            txt_work_management_history.setText(changeCharInPosition(setTitle(txt_work_management_history.getText().toString(), 1), '\n', txt_work_management_history.getText().toString()));
        }
        mHomePresenter = new HomePresenter(this);
        sessionManagerUser = new SessionManagerUser(HomeActivity.this);
        mHomePresenter.requestCheckToken();

        if(Constants.listTypeJob !=null) {
            rcv_type_job.setVisibility(View.VISIBLE);
            this.listTypeJob = Constants.listTypeJob;
            compareValueInModel(this.listTypeJob);
            for (TypeJob typeJob : this.listTypeJob) {
                listTypeJobName.add(typeJob.getName());
            }
            setRecyclerView();
        }
        else
        {
            ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), this);
        }
    }

    private void setRecyclerView()
    {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int mod = position % 6;
                if(position == 0)
                    return 2;
                else
                    return 1;
            }
        });
        rcv_type_job.setLayoutManager(layoutManager);
        typeJobAdapter=new TypeJobAdapter(HomeActivity.this,this.listTypeJob);
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

    private void showListTypeJobDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_quickpost, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle(getResources().getString(R.string.types_of_work));
        ListView lv = (ListView) convertView.findViewById(R.id.listTypeJobName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listTypeJobName);
        lv.setAdapter(adapter);
//
//        if(adapter.getCount() > 3){
//            View item = adapter.getView(0, null, lv);
//            item.measure(0, 0);
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));
//            lv.setLayoutParams(params);
//        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeActivity.this, QuickPostActivity.class);
                intent.putExtra("quickPost",listTypeJob.get(position));
                startActivity(intent);
            }
        });
        alertDialog.show();
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

    private List<TypeJob> compareValueInModel(List<TypeJob> list)
    {
        Collections.sort(list, new Comparator<TypeJob>() {
            public int compare(TypeJob obj1, TypeJob obj2) {
                return Integer.valueOf((int) obj2.getWeight()).compareTo((int) obj1.getWeight()); // To compare integer values
            }
        });
        return list;
    }
}
