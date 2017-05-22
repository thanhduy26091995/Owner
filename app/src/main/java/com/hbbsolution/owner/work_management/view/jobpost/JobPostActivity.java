package com.hbbsolution.owner.work_management.view.jobpost;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.TypeJobAdapter;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tantr on 5/14/2017.
 */

public class JobPostActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.job_post_title_toothbar)
    TextView txtJob_post_title_toothbar;
    @BindView(R.id.job_post_txtType_job)
    EditText txtType_job;
    @BindView(R.id.rad_type_money_work)
    RadioButton rad_type_money_work;
    @BindView(R.id.rad_type_money_khoan)
    RadioButton rad_type_money_khoan;
    @BindView(R.id.btn_post_complete)
    Button btn_post_complete;
    @BindView(R.id.edt_monney_work)
    EditText edt_monney_work;
    @BindView(R.id.txtTime_start)
    TextView txtTime_start;
    @BindView(R.id.txtTime_end)
    TextView txtTime_end;
    @BindView(R.id.txtDate_start_work)
    TextView txtDate_start_work;

    private String mDateStartWork, mTimeStartWork, mTimeEndWork;
    private boolean isTimeStart;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post);
        ButterKnife.bind(this);

        //setup view
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtJob_post_title_toothbar.setText("Đăng bài");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getDateCurrent();

        txtType_job.setOnClickListener(this);
        btn_post_complete.setOnClickListener(this);
        txtTime_start.setOnClickListener(this);
        txtTime_end.setOnClickListener(this);
        txtDate_start_work.setOnClickListener(this);
        rad_type_money_work.setOnClickListener(this);
        rad_type_money_khoan.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
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
        switch (view.getId()) {
            case R.id.rad_type_money_work:
                edt_monney_work.setEnabled(true);
                break;

            case R.id.rad_type_money_khoan:
                edt_monney_work.setEnabled(false);
                break;

            case R.id.job_post_txtType_job:
                eventClickTypeWork();
                break;

            case R.id.txtTime_start:
                isTimeStart = true;
                SgetTimePicker(txtTime_start);
                break;

            case R.id.txtTime_end:
                isTimeStart = false;
                SgetTimePicker(txtTime_end);
                break;

            case R.id.txtDate_start_work:
                getDatePicker();
                break;

            case R.id.btn_post_complete:
//                saveDataBeforeMoving();
                validateTimeWork();

                break;

        }
    }

    private void eventClickTypeWork() {
        View view = getLayoutInflater().inflate(R.layout.job_post_bottom_sheet, null);
        TextView txtCancle = (TextView) view.findViewById(R.id.txt_cancel);
        RecyclerView mRecycler = (RecyclerView) view.findViewById(R.id.recy_type_job);
        final List<String> listTypeJob = new ArrayList<>();
        listTypeJob.add("Dọn dẹp");
        listTypeJob.add("Nấu ăn");
        listTypeJob.add("Trông em bé");
        listTypeJob.add("Chăm sóc thú cưng");
        listTypeJob.add("Khác");
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        TypeJobAdapter mTypeJobtAdapter = new TypeJobAdapter(JobPostActivity.this, listTypeJob);
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(mTypeJobtAdapter);
        mTypeJobtAdapter.notifyDataSetChanged();

        final Dialog mBottomSheetDialog = new Dialog(JobPostActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        mTypeJobtAdapter.setCallback(new TypeJobAdapter.Callback() {
            @Override
            public void onItemClick(int position) {
                txtType_job.setText(listTypeJob.get(position));
                mBottomSheetDialog.dismiss();
            }
        });

        txtCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });

    }

    private void saveDataBeforeMoving() {
        if (rad_type_money_work.isChecked()) {
            edt_monney_work.setEnabled(true);
        } else {
            edt_monney_work.setEnabled(false);
        }
    }

    private void SgetTimePicker(final TextView txtTime) {
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(0, 0, 0, hourOfDay, minute);
                DateTime dateTime = new DateTime(calendar);
                if(isTimeStart){
                    mTimeStartWork = dateTime.toString();
                    Toast.makeText(JobPostActivity.this, "AAA",Toast.LENGTH_SHORT).show();
                } else {
                    mTimeEndWork = dateTime.toString();
                    Toast.makeText(JobPostActivity.this, "BBB",Toast.LENGTH_SHORT).show();
                }
                txtTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();

    }


    private void getTimePicker(final TextView txtTime) {
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(0, 0, 0, hourOfDay, minute);
                txtTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void getDatePicker(){
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                DateTime dateTime = new DateTime(calendar);
                mDateStartWork = dateTime.toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtDate_start_work.setText(simpleDateFormat.format(calendar.getTime()));
                if (CompareDays(txtDate_start_work.getText().toString())) {
                    Toast.makeText(JobPostActivity.this, "Sai ngày", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(JobPostActivity.this, "Đúng ngày", Toast.LENGTH_LONG).show();
                }
            }
        }, year, month , date);
        mDatePickerDialog.show();
    }

    private void getDateCurrent(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtDate_start_work.setText(simpleDateFormat.format(calendar.getTime()));
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

        if ( date1.after(date))
        {
            return false;
        }
        return true;
    }

    private  boolean CompareTime(String start, String end){

        String startTime = start;
        String endTime = end;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Date d1 = null, d2 = null;
        try {
            d1 = sdf.parse(startTime);
            d2 = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long elapsed = d2.getTime() - d1.getTime();
        if(elapsed > 0){
            return true;
        }
        return false;
    }

    private void validateTimeWork(){
        if (CompareTime(txtTime_start.getText().toString(), txtTime_end.getText().toString())){
            Toast.makeText(JobPostActivity.this, "Đúng giờ " + mTimeStartWork + mTimeEndWork, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(JobPostActivity.this, "Sai giờ", Toast.LENGTH_LONG).show();
        }
    }

}
