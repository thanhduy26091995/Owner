package com.hbbsolution.owner.maid_profile.choose_maid.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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
import com.hbbsolution.owner.maid_profile.choose_maid.model.SendRequestResponse;
import com.hbbsolution.owner.maid_profile.choose_maid.presenter.ChooseMaidPresenter;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.model.TypeJob;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 30/05/2017.
 */

public class ChooseMaidActivity extends AppCompatActivity implements View.OnClickListener, ChooseMaidView {

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
    @BindView(R.id.txtPost)
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

    private HashMap<String, String> hashMapTypeJob = new HashMap<>();
    private List<String> listTypeJobName = new ArrayList<>();
    private String mTypeJob = null, mPackageId;
    private ChooseMaidPresenter presenter;
    private Maid mMaidInfo;
    private boolean mChosenTools = false;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_maid);
        ButterKnife.bind(this);
        mMaidInfo = (Maid) getIntent().getSerializableExtra("maid");
        presenter = new ChooseMaidPresenter(this);
        //config toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        getDateCurrent();
        //event click
        edtType_job.setOnClickListener(this);
        txt_post_complete.setOnClickListener(this);
        txtTime_start.setOnClickListener(this);
        txtTime_end.setOnClickListener(this);
        txtDate_start_work.setOnClickListener(this);
        rad_type_money_work.setOnClickListener(this);
        rad_type_money_khoan.setOnClickListener(this);
        mPackageId = "000000000000000000000001";
        //get data
        presenter.getAllTypeJob();
    }

    //show dialog to choose date
    private void getDatePicker() {

        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtDate_start_work.setText(simpleDateFormat.format(calendar.getTime()));
                if (compareDay(txtDate_start_work.getText().toString())) {
                    ShowAlertDialog.showAlert("Sai ngày", ChooseMaidActivity.this);
                }
            }
        }, year, month, date);
        mDatePickerDialog.show();
    }

    //show timepicker
    private void getTimePicker(final TextView txtTime) {

        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(0, 0, 0, hourOfDay, minute, 0);
                txtTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();

    }

    private boolean compareDay(String dateStartWork) {

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

    //get current date
    private void getDateCurrent() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtDate_start_work.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(ChooseMaidActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == edtType_job) {
            eventClickTypeWork(listTypeJobName, edtType_job);
        } else if (v == txtDate_start_work) {
            getDatePicker();
        } else if (v == txtTime_start) {
            getTimePicker(txtTime_start);
        } else if (v == txtTime_end) {
            getTimePicker(txtTime_end);
        } else if (v == rad_type_money_work) {
            mPackageId = "000000000000000000000001";
            edt_monney_work.setEnabled(true);
        } else if (v == rad_type_money_khoan) {
            mPackageId = "000000000000000000000002";
            edt_monney_work.setEnabled(false);
        } else if (v == txt_post_complete) {
            if (checkDataComplete()) {
                showProgressDialog();
                presenter.getLocationAddress(edtAddressPost.getText().toString());
            }
        }
    }

    private boolean checkDataComplete() {

        if (edtTitlePost.getText().toString().isEmpty() || edtDescriptionPost.getText().toString().isEmpty() ||
                edtAddressPost.getText().toString().isEmpty() || edtType_job.getText().toString().isEmpty()) {
            progressBar.setVisibility(View.GONE);
            ShowAlertDialog.showAlert(getResources().getString(R.string.check_complete_all_information), ChooseMaidActivity.this);
            return false;
        }

        if (rad_type_money_work.isChecked() && edt_monney_work.getText().toString().isEmpty()) {
            progressBar.setVisibility(View.GONE);
            ShowAlertDialog.showAlert("Chưa nhập số tiền", ChooseMaidActivity.this);
            return false;
        }

        if (!validateTimeWork()) {
            progressBar.setVisibility(View.GONE);
            ShowAlertDialog.showAlert("Chọn giờ chưa phù hợp", ChooseMaidActivity.this);
            return false;
        }

        return true;
    }

    private boolean validateTimeWork() {
        if (compareTime(txtTime_start.getText().toString(), txtTime_end.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean compareTime(String start, String end) {

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
        if (elapsed > 0) {
            return true;
        }

        return false;
    }

    private void eventClickTypeWork(final List<String> listData, final EditText txtShow) {

        View view = getLayoutInflater().inflate(R.layout.job_post_bottom_sheet, null);
        //map components
        TextView txtCancel = (TextView) view.findViewById(R.id.txt_cancel);
        RecyclerView mRecycler = (RecyclerView) view.findViewById(R.id.recy_type_job);

        mRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //create bottom sheet
        BottomSheetAdapter mTypeJobtAdapter = new BottomSheetAdapter(ChooseMaidActivity.this, listData);
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(mTypeJobtAdapter);
        mTypeJobtAdapter.notifyDataSetChanged();

        final Dialog mBottomSheetDialog = new Dialog(ChooseMaidActivity.this, R.style.MaterialDialogSheet);
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

    @Override
    public void getAllTypeJob(TypeJobResponse typeJobResponse) {
        for (TypeJob typeJob : typeJobResponse.getData()) {
            hashMapTypeJob.put(typeJob.getName(), typeJob.getId());
            listTypeJobName.add(typeJob.getName());
        }
    }

    @Override
    public void displayError(String error) {
        Log.d("ERROR", error);
        hideProgressDialog();
        ShowAlertDialog.showAlert("Gửi yêu cầu thất bại", ChooseMaidActivity.this);
    }

    @Override
    public void getLocationAddress(GeoCodeMapResponse geoCodeMapResponse) {
        Double price = null, hour = null;
        double lat = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
        double lng = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
        String title = edtTitlePost.getText().toString();
        String description = edtDescriptionPost.getText().toString();
        String address = edtAddressPost.getText().toString();
        if (chb_tools_work.isChecked()) {
            mChosenTools = true;
        }
        if (rad_type_money_work.isChecked()) {
            price = Double.parseDouble(edt_monney_work.getText().toString());
        } else {
            // hour = Double.parseDouble(edtHourWork.getText().toString());
        }
        String dateStartWork = getTimeWork(txtTime_start.getText().toString());
        String dateEndWork = getTimeWork(txtTime_end.getText().toString());
        //send request
        presenter.sendRequest(mMaidInfo.getId(), title, mPackageId, mTypeJob, description, price, address, lat, lng, dateStartWork, dateEndWork, hour, mChosenTools);

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

    @Override
    public void displayNotFoundLocation(String error) {
        hideProgressDialog();
        ShowAlertDialog.showAlert(error, ChooseMaidActivity.this);
    }

    @Override
    public void sendRequestJob(SendRequestResponse sendRequestResponse) {
        hideProgressDialog();
        boolean status = sendRequestResponse.isStatus();
        if (status) {
            try {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChooseMaidActivity.this);
                alertDialogBuilder.setMessage("Gửi yêu cầu thành công");
                alertDialogBuilder.setPositiveButton(getResources().getText(R.string.okAlert),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                if (MaidProfileActivity.maidProfileActivity != null) {
                                    MaidProfileActivity.maidProfileActivity.finish();
                                }
                                finish();
                                alertDialogBuilder.create().dismiss();
                            }

                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } catch (Exception e) {

            }
        } else {
            ShowAlertDialog.showAlert("Có lỗi, vui lòng thử lại", ChooseMaidActivity.this);
        }
    }
}
