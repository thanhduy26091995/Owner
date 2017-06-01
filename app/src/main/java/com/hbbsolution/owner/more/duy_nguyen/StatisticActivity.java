package com.hbbsolution.owner.more.duy_nguyen;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.more.duy_nguyen.model.Task;
import com.hbbsolution.owner.more.duy_nguyen.presenter.StatisticPresenter;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticActivity extends AppCompatActivity implements View.OnClickListener, StatisticView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvStartDate)
    TextView tvStartDate;
    @BindView(R.id.tvEndDate)
    TextView tvEndDate;
    @BindView(R.id.txt_statistic_payment)
    TextView tvPayment;
    @BindView(R.id.txt_statistic_owner_name)
    TextView tvOwnerName;

    @BindView(R.id.totalPrice)
    TextView totalPrice;
    @BindView(R.id.numberPostedTask)
    TextView numberPostedTask;
    @BindView(R.id.numberDoingTask)
    TextView numberDoingTask;
    @BindView(R.id.numberDoneTask)
    TextView numberDoneTask;

    @BindView(R.id.lnStatistic)
    LinearLayout lnStatistic;

    private Calendar cal;
    private Date startDate, endDate;
    private String strStartDate, strEndDate;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashDataUser = new HashMap<>();
    private StatisticPresenter statisticPresenter;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private ProgressBar progressBar;
    private int onCreate,pending,reserved,onDoing,done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        ButterKnife.bind(this);
        lnStatistic.setVisibility(View.INVISIBLE);
        progressBar = (ProgressBar) findViewById(R.id.progressPost);
        progressBar.setVisibility(View.VISIBLE);

        sessionManagerUser = new SessionManagerUser(this);
        hashDataUser = sessionManagerUser.getUserDetails();
        statisticPresenter = new StatisticPresenter(this);

        setToolbar();
        cal = Calendar.getInstance();
        getTime();
        setNumber();
        getData();
        setEventClick();
    }

    public void setToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setEventClick() {
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);
        tvPayment.setOnClickListener(this);
    }
    public void setNumber()
    {
        onCreate=0;
        pending=0;
        reserved=0;
        onDoing=0;
        done=0;
    }
    public void getData() {

        statisticPresenter.getStatistic("", simpleDateFormat.format(endDate));
    }

    public void showDatePickerDialog1() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                String day = String.valueOf(dayOfMonth), month = String.valueOf(monthOfYear);
                if (dayOfMonth < 10) {
                    day = "0" + dayOfMonth;
                }
                if (monthOfYear + 1 < 10) {
                    month = "0" + (monthOfYear + 1);
                }
                tvStartDate.setText(
                        day + "/" + month + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                startDate = cal.getTime();
                if (endDate.getTime() - startDate.getTime() >= 0) {
                    setNumber();
                    statisticPresenter.getStatistic(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
                } else {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.rangetime), StatisticActivity.this);
                }
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s = tvStartDate.getText().toString();
        if (tvStartDate.getText().toString().equals("- - / - - / - - - -")) {
            s = strStartDate;
        }
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(this, callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày bắt đầu");
        pic.show();
    }

    public void showDatePickerDialog2() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                String day = String.valueOf(dayOfMonth), month = String.valueOf(monthOfYear);
                if (dayOfMonth < 10) {
                    day = "0" + dayOfMonth;
                }
                if (monthOfYear + 1 < 10) {
                    month = "0" + (monthOfYear + 1);
                }
                tvEndDate.setText(
                        day + "/" + month + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                endDate = cal.getTime();
                if (startDate != null) {
                    if (endDate.getTime() - startDate.getTime() >= 0) {
                        setNumber();
                        statisticPresenter.getStatistic(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
                    } else {
                        ShowAlertDialog.showAlert(getResources().getString(R.string.rangetime), StatisticActivity.this);
                    }
                } else {
                    setNumber();
                    statisticPresenter.getStatistic("", simpleDateFormat.format(endDate));
                }
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s = tvEndDate.getText().toString();
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(this, callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày kết thúc");
        pic.show();
    }

    public void getTime() {
        endDate = new Date();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date myDate = new Date();
        strEndDate = date.format(myDate);
        Date newDate = new Date();
        strStartDate = date.format(newDate);
        tvStartDate.setText("- - / - - / - - - -");
        tvEndDate.setText(strEndDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStartDate:
                showDatePickerDialog1();
                break;
            case R.id.tvEndDate:
                showDatePickerDialog2();
                break;
            case R.id.txt_statistic_payment:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getStatisticSuccess(List<Task> listTask, int total) {
        int i=0;
        while (i<listTask.size()){
            if (listTask.get(i).getId().equals("000000000000000000000001")) {
                onCreate = listTask.get(i).getTask().size();
            }
            if (listTask.get(i).getId().equals("000000000000000000000002")) {
                pending = listTask.get(i).getTask().size();
            }
            if (listTask.get(i).getId().equals("000000000000000000000003")) {
                reserved = listTask.get(i).getTask().size();
            }
            if (listTask.get(i).getId().equals("000000000000000000000004")) {
                onDoing = listTask.get(i).getTask().size();
            }
            if (listTask.get(i).getId().equals("000000000000000000000005")) {
                done = listTask.get(i).getTask().size();
            }
            i++;
        }
        numberPostedTask.setText(String.valueOf(onCreate+pending));
        numberDoingTask.setText(String.valueOf(reserved+onDoing));
        numberDoneTask.setText(String.valueOf(done));
        totalPrice.setText(String.valueOf(total) + " " + getResources().getString(R.string.million));
        progressBar.setVisibility(View.GONE);
        lnStatistic.setVisibility(View.VISIBLE);
    }

    @Override
    public void getStatisticFail() {

    }
}
