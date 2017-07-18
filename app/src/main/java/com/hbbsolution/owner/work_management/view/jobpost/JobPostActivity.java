package com.hbbsolution.owner.work_management.view.jobpost;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.BottomSheetAdapter;
import com.hbbsolution.owner.base.AuthenticationBaseActivity;
import com.hbbsolution.owner.base.BaseActivity;
import com.hbbsolution.owner.model.TypeJob;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;
import com.hbbsolution.owner.work_management.model.jobpost.JobPostResponse;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;
import com.hbbsolution.owner.work_management.presenter.JobPostPresenter;
import com.hbbsolution.owner.work_management.view.detail.DetailJobPostActivity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 5/14/2017.
 */

public class JobPostActivity extends AuthenticationBaseActivity implements JobPostView, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edtTitlePost)
    EditText edtTitlePost;
    @BindView(R.id.edtDescriptionPost)
    EditText edtDescriptionPost;
    @BindView(R.id.edtAddressPost)
    EditText edtAddressPost;
    @BindView(R.id.job_post_txtType_job)
    EditText edtType_job;
    @BindView(R.id.rad_type_money_work)
    RadioButton rad_type_money_work;
    @BindView(R.id.rad_type_money_khoan)
    RadioButton rad_type_money_khoan;
    @BindView(R.id.txtPostJob)
    TextView txt_post_complete;
    @BindView(R.id.edt_monney_work)
    EditText edt_monney_work;
    @BindView(R.id.txtTime_start)
    TextView txtTime_start;
    @BindView(R.id.txtTime_end)
    TextView txtTime_end;
    @BindView(R.id.txtDate_start_work)
    TextView txtDate_start_work;
    @BindView(R.id.chb_tools_work)
    CheckBox chb_tools_work;
    @BindView(R.id.progressPostJob)
    ProgressBar progressBar;
    @BindView(R.id.lo_job_post)
    EditText lo_job_post;


    public static Activity mJobPostActivity = null;
    private ProgressDialog progressDialog;

    private String mTitlePost, mTypeJob, mDescriptionPost, mAddressPost, mPackageId,
            mDateStartWork, mTimeStartWork, mTimeEndWork, mIdTask, mPrice, mHours;

    private boolean mChosenTools = false, isPost;

    private Datum infoJob;

    private HashMap<String, String> hashMapTypeJob = new HashMap<>();
    private List<String> listTypeJobName = new ArrayList<>();
    private JobPostPresenter mJobPostPresenter;
    private Date startTime, endTime, startTimeTemp, endTimeTemp, nowTime, nowDate, choseDate;
    private Calendar cal;
    private int clicked;
    private int date, month, year;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post);
        mJobPostActivity = this;
        ButterKnife.bind(this);

        checkConnectionInterner();
        //setup view
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        cal = Calendar.getInstance();
        cal.set(0, 0, 0);
        nowTime = cal.getTime();
        cal.set(0, 0, 0, 0, 0, 0);

        clicked = 0;

        mJobPostPresenter = new JobPostPresenter(this);
        txt_post_complete.setEnabled(false);
        mJobPostPresenter.getAllTypeJob();

        getDateCurrent();

        edtType_job.setOnClickListener(this);
        txt_post_complete.setOnClickListener(this);
        txtTime_start.setOnClickListener(this);
        txtTime_end.setOnClickListener(this);
        txtDate_start_work.setOnClickListener(this);
        rad_type_money_work.setOnClickListener(this);
        rad_type_money_khoan.setOnClickListener(this);


        if (rad_type_money_work.isChecked()) {
            edt_monney_work.setEnabled(true);
            mPackageId = "000000000000000000000001";
        } else if (rad_type_money_khoan.isChecked()) {
            edt_monney_work.setEnabled(false);
            mPackageId = "000000000000000000000002";
        }

        final Intent intent = getIntent();
        infoJob = (Datum) intent.getSerializableExtra("infoJobPost");

        if (infoJob != null) {
            isPost = false;
            txt_post_complete.setText(getResources().getString(R.string.update_job_post));
            mIdTask = infoJob.getId();

            edtTitlePost.setText(infoJob.getInfo().getTitle());
            int position = edtTitlePost.length();
            Editable etext = edtTitlePost.getText();
            Selection.setSelection(etext, position);

            edtDescriptionPost.setText(infoJob.getInfo().getDescription());
            edtAddressPost.setText(infoJob.getInfo().getAddress().getName());
            edtType_job.setText(infoJob.getInfo().getWork().getName());
            mTypeJob = infoJob.getInfo().getWork().getId();
            chb_tools_work.setChecked(infoJob.getInfo().getTools());
            mPackageId = infoJob.getInfo().getPackage().getId();
            mHours = String.valueOf(infoJob.getInfo().getTime().getHour());
//            edt_monney_work_hour.setText(mHours);

            if (mPackageId.equals("000000000000000000000001")) {
                edt_monney_work.setEnabled(true);
//                edt_monney_work_hour.setEnabled(false);
                rad_type_money_work.setChecked(true);
                edt_monney_work.setText(String.valueOf(infoJob.getInfo().getPrice()));
            } else if (mPackageId.equals("000000000000000000000002")) {
                edt_monney_work.setEnabled(false);
//                edt_monney_work_hour.setEnabled(true);
                rad_type_money_khoan.setChecked(true);
            }

            txtDate_start_work.setText(getDatePostHistory(infoJob.getInfo().getTime().getStartAt()));
            txtTime_start.setText(getTimeDoingPost(infoJob.getInfo().getTime().getStartAt()));
            txtTime_end.setText(getTimeDoingPost(infoJob.getInfo().getTime().getEndAt()));

            SimpleDateFormat editTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            try {
                startTime = editTime.parse(infoJob.getInfo().getTime().getStartAt());
                endTime = editTime.parse(infoJob.getInfo().getTime().getEndAt());
                choseDate = editTime.parse(infoJob.getInfo().getTime().getStartAt());
                clicked = 1;
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            isPost = true;
            txt_post_complete.setText(getResources().getString(R.string.detail_posted));
        }

        edt_monney_work.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    edt_monney_work.removeTextChangedListener(this);
                    String titleString = edt_monney_work.getText().toString().replace(".", "");
                    edt_monney_work.setText(NumberFormat.getNumberInstance(Locale.GERMANY).format(Long.parseLong(titleString)));
                    edt_monney_work.setSelection(edt_monney_work.getText().toString().length());
                    edt_monney_work.addTextChangedListener(this);
                } catch (Exception e) {
                    edt_monney_work.addTextChangedListener(this);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            EventBus.getDefault().postSticky(false);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().postSticky(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rad_type_money_work:
                mPackageId = "000000000000000000000001";
                edt_monney_work.setEnabled(true);
                break;

            case R.id.rad_type_money_khoan:
                mPackageId = "000000000000000000000002";
                edt_monney_work.setEnabled(false);
                edt_monney_work.setText("");
                edt_monney_work.setHint(getResources().getString(R.string.enter_the_salary));
                break;

            case R.id.job_post_txtType_job:
                eventClickTypeWork(listTypeJobName, edtType_job);
                break;

            case R.id.txtTime_start:
                getTimePicker();
                break;

            case R.id.txtTime_end:
                getTimePicker2();
                break;

            case R.id.txtDate_start_work:
                getDatePicker();
                break;

            case R.id.txtPostJob:
//                progressBar.setVisibility(View.VISIBLE);
//                lo_job_post.setVisibility(View.VISIBLE);
//                showProgressDialog();
//                checkLocaltionOfOwner();
                showProgressDialog();
                if (checkDataComplete()) {
                    mJobPostPresenter.getLocaltionAddress(edtAddressPost.getText().toString());
                }
                break;

        }
    }

    @Override
    public void getAllTypeJob(TypeJobResponse typeJobResponse) {
        txt_post_complete.setEnabled(true);

        for (TypeJob typeJob : typeJobResponse.getData()) {
            hashMapTypeJob.put(typeJob.getName(), typeJob.getId());
            listTypeJobName.add(typeJob.getName());
        }
    }

    @Override
    public void getLocaltionAddress(GeoCodeMapResponse geoCodeMapResponse) {
        double lat = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
        double lng = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
        mAddressPost = edtAddressPost.getText().toString();
        mTitlePost = edtTitlePost.getText().toString();
        mDescriptionPost = edtDescriptionPost.getText().toString();
        mTimeStartWork = getTimeWork(txtTime_start.getText().toString());
        mTimeEndWork = getTimeWork(txtTime_end.getText().toString());
        if (!edt_monney_work.getText().toString().isEmpty()) {
            mPrice = edt_monney_work.getText().toString().replace(".", "");
        } else {
            mPrice = "0";
        }

        if (chb_tools_work.isChecked()) {
            mChosenTools = true;
        }

        txt_post_complete.setEnabled(false);
//        progressBar.setVisibility(View.VISIBLE);

        if (isPost) {
            mJobPostPresenter.postJob(mTitlePost, mTypeJob, mDescriptionPost, mAddressPost, lat, lng,
                    mChosenTools, mPackageId, mPrice, mTimeStartWork, mTimeEndWork);
        } else {
            mJobPostPresenter.updatePostJob(mIdTask, mTitlePost, mTypeJob, mDescriptionPost, mAddressPost, lat, lng,
                    mChosenTools, mPackageId, mPrice, mTimeStartWork, mTimeEndWork);
        }
//        if (checkDataComplete()) {
//            posData(geoCodeMapResponse);
//        }
    }

    @Override
    public void displayNotifyJobPost(JobPostResponse isJobPost) {
        txt_post_complete.setEnabled(true);
//        lo_job_post.setVisibility(View.GONE);
//        progressBar.setVisibility(View.GONE);
        hideProgressDialog();
        if (isJobPost.getStatus()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getResources().getString(R.string.notification));
            alertDialog.setMessage(getResources().getString(R.string.post_successfully));
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EventBus.getDefault().postSticky(true);
                    EventBus.getDefault().postSticky("0");
                    if (mJobPostActivity != null) {
                        JobPostActivity.mJobPostActivity.finish();
                        try {
                            if (DetailJobPostActivity.mDetailJobPostActivity != null) {
                                DetailJobPostActivity.mDetailJobPostActivity.finish();
                            }
                        } catch (Exception e) {

                        }
                    }
                }
            });

            alertDialog.show();
        } else {
            if (isJobPost.getMessage().equals("TASK_OUT_OF_LIMIT")) {
                ShowAlertDialog.showAlert(getResources().getString(R.string.check_number_job_post), JobPostActivity.this);
            } else {
//            hideProgressDialog();
//            lo_job_post.setVisibility(View.GONE);
//            progressBar.setVisibility(View.GONE);
                ShowAlertDialog.showAlert(getResources().getString(R.string.post_unsuccessfully), JobPostActivity.this);
            }
        }
    }

    @Override
    public void displayNotFoundLocaltion() {
        hideProgressDialog();
//        lo_job_post.setVisibility(View.GONE);
//        progressBar.setVisibility(View.GONE);
        ShowAlertDialog.showAlert(getResources().getString(R.string.check_your_home_address), JobPostActivity.this);
    }

    @Override
    public void displayError(String error) {
        hideProgressDialog();
    }

    private void eventClickTypeWork(final List<String> listData, final EditText txtShow) {

        View view = getLayoutInflater().inflate(R.layout.job_post_bottom_sheet, null);
        //map components
        TextView txtCancel = (TextView) view.findViewById(R.id.txt_cancel);
        RecyclerView mRecycler = (RecyclerView) view.findViewById(R.id.recy_type_job);

        mRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //create bottom sheet
        BottomSheetAdapter mTypeJobtAdapter = new BottomSheetAdapter(JobPostActivity.this, listData);
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(mTypeJobtAdapter);
        mTypeJobtAdapter.notifyDataSetChanged();

        final Dialog mBottomSheetDialog = new Dialog(JobPostActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        mTypeJobtAdapter.setCallback(new BottomSheetAdapter.Callback() {
            @Override
            public void onItemClick(int position) {
                txtShow.setText(listData.get(position));
                String item = listData.get(position);
                String idTypeJob = hashMapTypeJob.get(item);
                mTypeJob = idTypeJob;
                mBottomSheetDialog.dismiss();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
    }


    private boolean checkDataComplete() {

        if (edtTitlePost.getText().toString().isEmpty() || edtDescriptionPost.getText().toString().isEmpty() ||
                edtAddressPost.getText().toString().isEmpty() || edtType_job.getText().toString().isEmpty()) {
            hideProgressDialog();
//            progressBar.setVisibility(View.GONE);
//            lo_job_post.setVisibility(View.GONE);
            ShowAlertDialog.showAlert(getResources().getString(R.string.check_complete_all_information), JobPostActivity.this);
            return false;
        }

        if (rad_type_money_work.isChecked() && edt_monney_work.getText().toString().isEmpty()) {
            hideProgressDialog();
//            progressBar.setVisibility(View.GONE);
//            lo_job_post.setVisibility(View.GONE);
            ShowAlertDialog.showAlert(getResources().getString(R.string.no_amount), JobPostActivity.this);
            return false;
        }
        if (CompareTimeStart(getTimeWork(txtTime_start.getText().toString()))) {
            hideProgressDialog();
//            progressBar.setVisibility(View.GONE);
//            lo_job_post.setVisibility(View.GONE);
            ShowAlertDialog.showAlert(getResources().getString(R.string.check_working_time), JobPostActivity.this);
            return false;
        }

        if (!validateTimeWork()) {
            hideProgressDialog();
//            progressBar.setVisibility(View.GONE);
//            lo_job_post.setVisibility(View.GONE);
            ShowAlertDialog.showAlert(getResources().getString(R.string.check_working_time), JobPostActivity.this);
            return false;

        }

        return true;
    }

    private void getTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(0, 0, 0, hourOfDay, minute, 0);
                compareTimeStart(calendar, simpleDateFormat);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void getTimePicker2() {
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(0, 0, 0, hourOfDay, minute, 0);
                compareTimeEnd(calendar, simpleDateFormat);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void getDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        final int chDate = calendar.get(Calendar.DATE);
        final int chMonth = calendar.get(Calendar.MONTH);
        final int chYear = calendar.get(Calendar.YEAR);
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2, 0, 0, 0);
                choseDate = calendar.getTime();
                if (date == i2 && month == i1 && year == i) {
                    nowDate = choseDate;
                    clicked=0;
                }
//                DateTime dateTime = new DateTime(calendar);
//                mDateStartWork = dateTime.toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtDate_start_work.setText(simpleDateFormat.format(calendar.getTime()));
                if (CompareDays(txtDate_start_work.getText().toString())) {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.check_date_post), JobPostActivity.this);
                }
            }
        }, chYear, chMonth, chDate);
        mDatePickerDialog.show();
    }

    private void getDateCurrent() {
        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        calendar.set(year, month, date, 0, 0, 0);
        nowDate = calendar.getTime();
        choseDate = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtDate_start_work.setText(simpleDateFormat.format(calendar.getTime()));

    }

    public void compareTimeStart(Calendar calendar, SimpleDateFormat simpleDateFormat) {
        startTimeTemp = calendar.getTime();
        if (choseDate.getTime() == nowDate.getTime()) {
            if (clicked == 1 && endTime!=null) {
                if (endTime.getTime() - startTimeTemp.getTime() >= 0 && startTimeTemp.getTime() >= nowTime.getTime()) {
                    txtTime_start.setText(simpleDateFormat.format(calendar.getTime()));
                    startTime = startTimeTemp;
                } else if (endTime.getTime() - startTimeTemp.getTime() < 0){
                    ShowAlertDialog.showAlert(getResources().getString(R.string.rangetime), JobPostActivity.this);
                }
                else if (startTimeTemp.getTime() < nowTime.getTime())
                {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.check_working_time), JobPostActivity.this);
                }
            } else {
                if (startTimeTemp.getTime() >= nowTime.getTime()) {
                    txtTime_start.setText(simpleDateFormat.format(calendar.getTime()));
                    startTime = startTimeTemp;
                    clicked = 1;
                } else {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.check_working_time), JobPostActivity.this);
                }
            }
        } else {
            if (clicked == 1 && endTime!=null) {
                if (endTime.getTime() - startTimeTemp.getTime() >= 0) {
                    txtTime_start.setText(simpleDateFormat.format(calendar.getTime()));
                    startTime = startTimeTemp;
                } else {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.rangetime), JobPostActivity.this);

                }
            } else {
                txtTime_start.setText(simpleDateFormat.format(calendar.getTime()));
                startTime = startTimeTemp;
                clicked = 1;
            }
        }
    }

    public void compareTimeEnd(Calendar calendar, SimpleDateFormat simpleDateFormat) {
        endTimeTemp = calendar.getTime();
        if (choseDate.getTime() == nowDate.getTime()) {
            if (clicked == 1 && startTime!=null) {
                if (endTimeTemp.getTime() - startTime.getTime() >= 0 && endTimeTemp.getTime() >= nowTime.getTime()) {
                    txtTime_end.setText(simpleDateFormat.format(calendar.getTime()));
                    endTime = endTimeTemp;
                } else if(endTimeTemp.getTime() - startTime.getTime() < 0){
                    ShowAlertDialog.showAlert(getResources().getString(R.string.rangetime), JobPostActivity.this);
                }
                else if (endTimeTemp.getTime() < nowTime.getTime()){
                    ShowAlertDialog.showAlert(getResources().getString(R.string.check_working_time), JobPostActivity.this);
                }
            } else {
                if (endTimeTemp.getTime() >= nowTime.getTime()) {
                    txtTime_end.setText(simpleDateFormat.format(calendar.getTime()));
                    endTime = endTimeTemp;
                    clicked = 1;
                } else {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.check_working_time), JobPostActivity.this);
                }
            }
        } else {
            if (clicked == 1 && startTime!=null) {
                if (endTimeTemp.getTime() - startTime.getTime() >= 0) {
                    txtTime_end.setText(simpleDateFormat.format(calendar.getTime()));
                    endTime = endTimeTemp;
                } else {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.rangetime), JobPostActivity.this);
                }
            } else {
                txtTime_end.setText(simpleDateFormat.format(calendar.getTime()));
                endTime = endTimeTemp;
                clicked = 1;
            }
        }
    }

    private boolean CompareDays(String dateStartWork) {
        Date date1 = null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            date1 = sdf.parse(dateStartWork);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date1.after(date)) {
            return false;
        }
        return true;
    }

    private boolean CompareTimeStart(String start) {
        Date dateNow = new Date();
        DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
        Date dateStart = parser.parseDateTime(start).toDate();
        long elapsed = dateNow.getTime() - dateStart.getTime();
        if (elapsed > 0) {
            return true;
        }
        return false;
    }

    private boolean CompareTime(String start, String end) {
        String startTime = start;
        String endTime = end;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Date d1 = null, d2 = null;
        try {
            d1 = sdf.parse(startTime);
            d2 = sdf.parse(endTime);
            long elapsed = d2.getTime() - d1.getTime();
            if (elapsed > 0) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return false;
    }

    private void checkLocaltionOfOwner() {
        mAddressPost = edtAddressPost.getText().toString();
        if (!mAddressPost.isEmpty()) {
            mJobPostPresenter.getLocaltionAddress(mAddressPost);
        } else {
            hideProgressDialog();
//            progressBar.setVisibility(View.GONE);
//            lo_job_post.setVisibility(View.GONE);
            ShowAlertDialog.showAlert(getResources().getString(R.string.check_address), JobPostActivity.this);
        }
    }

    private String getDatePostHistory(String createDatePostHistory) {
        Date date = new DateTime(createDatePostHistory).toDate();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String mDateTimePostHistory = df.format(date);
        return mDateTimePostHistory;
    }

    private String getTimeDoingPost(String mTimeWork) {
        Date date = new DateTime(mTimeWork).toDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        return simpleDateFormat.format(date);
    }

    private String getTimeWork(String mTimeWork) {
        DateFormat mCreateTime = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        String _TimeWork = txtDate_start_work.getText().toString() + " " + mTimeWork;
        Date mTimeAt = null;
        try {
            mTimeAt = mCreateTime.parse(_TimeWork);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new DateTime(mTimeAt).toString();
    }

    private boolean validateTimeWork() {
        if (CompareTime(txtTime_start.getText().toString(), txtTime_end.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(JobPostActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
