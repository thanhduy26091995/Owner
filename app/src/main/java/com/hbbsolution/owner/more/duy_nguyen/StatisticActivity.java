package com.hbbsolution.owner.more.duy_nguyen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.more.duy_nguyen.model.Task;
import com.hbbsolution.owner.more.duy_nguyen.presenter.StatisticPresenter;
import com.hbbsolution.owner.utils.SessionManagerForLanguage;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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

    @BindView(R.id.tvWallet)
    TextView tvWallet;

    @BindView(R.id.lnStatistic)
    LinearLayout lnStatistic;
    @BindView(R.id.rela_info)
    RelativeLayout rela_info;
    @BindView(R.id.img_history_avatar)
    CircleImageView imgAvatar;

    @BindView(R.id.postedWork)
    TextView tvPostedWork;
    @BindView(R.id.workInProcess)
    TextView tvWorkInProcess;
    @BindView(R.id.workHistory)
    TextView tvWorkHistory;

    private Calendar cal;
    private Date startDate, endDate, startDateTemp, endDateTemp;
    private String strStartDate, strEndDate;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashDataUser = new HashMap<>();
    private StatisticPresenter statisticPresenter;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private ProgressBar progressBar;
    private int onCreate, pending, reserved, onDoing, done, immediate;
    private String tempStartDate, tempEndDate;
    private boolean resume;
    private SessionManagerForLanguage sessionManagerForLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        ButterKnife.bind(this);
        lnStatistic.setVisibility(View.INVISIBLE);
        progressBar = (ProgressBar) findViewById(R.id.progressPost);
        progressBar.setVisibility(View.VISIBLE);
        resume = false;
        sessionManagerUser = new SessionManagerUser(this);
        hashDataUser = sessionManagerUser.getUserDetails();
        statisticPresenter = new StatisticPresenter(this);
        setToolbar();
        cal = Calendar.getInstance();
        getTime();
        setNumber();
        if (hashDataUser != null) {
            getData();
        }
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
        //       rela_info.setOnClickListener(this);
    }

    public void setNumber() {
        onCreate = 0;
        pending = 0;
        reserved = 0;
        onDoing = 0;
        done = 0;
        immediate = 0;
    }

    public void getData() {
        Glide.with(this).load(hashDataUser.get(SessionManagerUser.KEY_IMAGE))
                .thumbnail(0.5f)
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .centerCrop()
                .dontAnimate()
                .into(imgAvatar);
        tvOwnerName.setText(hashDataUser.get(SessionManagerUser.KEY_NAME));
        statisticPresenter.getStatistic("", simpleDateFormat.format(endDate));

        sessionManagerForLanguage = new SessionManagerForLanguage(this);
        String lang = sessionManagerForLanguage.getLanguage();
        if (lang.equals("Tiếng Việt")) {
            tvPostedWork.setText(changeCharInPosition(setTitle(tvPostedWork.getText().toString(), 2), '\n', tvPostedWork.getText().toString()));
            tvWorkInProcess.setText(changeCharInPosition(setTitle(tvWorkInProcess.getText().toString(), 2), '\n', tvWorkInProcess.getText().toString()));
            tvWorkHistory.setText(changeCharInPosition(setTitle(tvWorkHistory.getText().toString(), 2), '\n', tvWorkHistory.getText().toString()));
//        } else {
//            tvPostedWork.setText(changeCharInPosition(setTitle(tvPostedWork.getText().toString(),1),'\n',tvPostedWork.getText().toString()));
//            tvWorkInProcess.setText(changeCharInPosition(setTitle(tvWorkInProcess.getText().toString(),1),'\n',tvWorkInProcess.getText().toString()));
//            tvWorkHistory.setText(changeCharInPosition(setTitle(tvWorkHistory.getText().toString(),1),'\n',tvWorkHistory.getText().toString()));
//        }
        }
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
                tempStartDate = tvStartDate.getText().toString();
                tvStartDate.setText(
                        day + "/" + month + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                startDateTemp = cal.getTime();
                if (endDate.getTime() - startDateTemp.getTime() >= 0) {
                    setNumber();
                    startDate = startDateTemp;
                    statisticPresenter.getStatistic(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
                } else {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.rangetime), StatisticActivity.this);
                    tvStartDate.setText(tempStartDate);
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
        pic.setTitle(getResources().getString(R.string.chon_ngay_bat_dau));
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
                tempEndDate = tvEndDate.getText().toString();
                tvEndDate.setText(
                        day + "/" + month + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                endDateTemp = cal.getTime();
                if (startDate != null) {
                    if (endDateTemp.getTime() - startDate.getTime() >= 0) {
                        setNumber();
                        endDate = endDateTemp;
                        statisticPresenter.getStatistic(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
                    } else {
                        ShowAlertDialog.showAlert(getResources().getString(R.string.rangetime), StatisticActivity.this);
                        tvEndDate.setText(tempEndDate);
                    }
                } else {
                    setNumber();
                    endDate = endDateTemp;
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
        pic.setTitle(getResources().getString(R.string.chon_ngay_ket_thuc));
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
        Intent intent;
        switch (v.getId()) {
            case R.id.tvStartDate:
                showDatePickerDialog1();
                break;
            case R.id.tvEndDate:
                showDatePickerDialog2();
                break;
//            case R.id.rela_info:
//                intent = new Intent(StatisticActivity.this, ProfileActivity.class);
//                startActivity(intent);
//                break;
            case R.id.txt_statistic_payment:
                resume = true;
                intent = new Intent(StatisticActivity.this, RechargeActivity.class);
                startActivity(intent);
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
    public void getStatisticSuccess(List<Task> listTask, long total, long wallet) {
        int i = 0;
        if (listTask != null) {
            while (i < listTask.size()) {
                if (listTask.get(i).getId().equals("000000000000000000000001")) {
                    onCreate = listTask.get(i).getCount();
                }
                if (listTask.get(i).getId().equals("000000000000000000000002")) {
                    pending = listTask.get(i).getCount();
                }
                if (listTask.get(i).getId().equals("000000000000000000000003")) {
                    reserved = listTask.get(i).getCount();
                }
                if (listTask.get(i).getId().equals("000000000000000000000004")) {
                    onDoing = listTask.get(i).getCount();
                }
                if (listTask.get(i).getId().equals("000000000000000000000005")) {
                    done = listTask.get(i).getCount();
                }
                if (listTask.get(i).getId().equals("000000000000000000000006")) {
                    immediate = listTask.get(i).getCount();
                }
                i++;
            }
        }
        numberPostedTask.setText(String.valueOf(onCreate));
        numberDoingTask.setText(String.valueOf(reserved + onDoing + immediate));
        numberDoneTask.setText(String.valueOf(done));

        totalPrice.setText(NumberFormat.getNumberInstance(Locale.GERMANY).format(total));

        tvWallet.setText(NumberFormat.getNumberInstance(Locale.GERMANY).format(wallet) + " VND ");
        progressBar.setVisibility(View.GONE);
        lnStatistic.setVisibility(View.VISIBLE);
    }

    @Override
    public void getStatisticFail() {
        ShowAlertDialog.showAlert(getResources().getString(R.string.error), StatisticActivity.this);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (resume) {
            statisticPresenter.getStatistic("", simpleDateFormat.format(endDate));
            resume = false;
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
}
