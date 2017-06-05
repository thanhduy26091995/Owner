package com.hbbsolution.owner.history.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.LiabilitiesView;
import com.hbbsolution.owner.history.adapter.HistoryLiabilitiesAdapter;
import com.hbbsolution.owner.history.model.workhistory.WorkHistory;
import com.hbbsolution.owner.history.presenter.LiabilitiesPresenter;
import com.hbbsolution.owner.utils.ShowAlertDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 25/05/2017.
 */

public class HistoryLiabilitiesFragment extends Fragment implements LiabilitiesView {
    private View v, view;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryLiabilitiesAdapter historyLiabilitiesAdapter;
    private TextView tvStartDate, tvEndDate;
    private Calendar cal;
    private Date startDate, endDate;
    private String strStartDate, strEndDate;
    private LiabilitiesPresenter liabilitiesPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private ProgressBar progressBar;
    private LinearLayout lnNoData;

    public static HistoryLiabilitiesFragment newInstance() {
        HistoryLiabilitiesFragment fragment = new HistoryLiabilitiesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_history_liabilities, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progressPost);
        progressBar.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        lnNoData = (LinearLayout) v.findViewById(R.id.lnNoData);
//        Gán adapter các thứ
//        historyLiabilitiesAdapter = new HistoryLiabilitiesAdapter(getActivity());
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_history_liabilities);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        liabilitiesPresenter = new LiabilitiesPresenter(this);

        view = v.findViewById(R.id.view);
        view.setVisibility(View.INVISIBLE);

        cal = Calendar.getInstance();
        tvStartDate = (TextView) v.findViewById(R.id.tvStartDate);
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog1();
            }
        });
        tvEndDate = (TextView) v.findViewById(R.id.tvEndDate);
        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog2();
            }
        });

        getTime();

        liabilitiesPresenter.getInfoLiabilities(simpleDateFormat.format(endDate));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (startDate != null) {
                            liabilitiesPresenter.getInfoLiabilitiesTime(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
                        } else {
                            liabilitiesPresenter.getInfoLiabilities(simpleDateFormat.format(endDate));
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });
        return v;
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
                    view.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    liabilitiesPresenter.getInfoLiabilitiesTime(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
                } else {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.rangetime), getActivity());
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
        DatePickerDialog pic = new DatePickerDialog(getActivity(), callback, nam, thang, ngay);
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
                        view.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        liabilitiesPresenter.getInfoLiabilitiesTime(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
                    } else {
                        ShowAlertDialog.showAlert(getResources().getString(R.string.rangetime), getActivity());
                    }
                } else {
                    view.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    liabilitiesPresenter.getInfoLiabilitiesTime("", simpleDateFormat.format(endDate));
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
        DatePickerDialog pic = new DatePickerDialog(getActivity(), callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày kết thúc");
        pic.show();
    }

    public void getTime() {
        endDate = new Date();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date myDate = new Date();
        strEndDate = date.format(myDate);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(myDate);
//        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date newDate = new Date();
        strStartDate = date.format(newDate);
        tvStartDate.setText("- - / - - / - - - -");
        tvEndDate.setText(strEndDate);
    }

    @Override
    public void getLiabilitiesSuccess(List<WorkHistory> workHistories) {
        historyLiabilitiesAdapter = new HistoryLiabilitiesAdapter(getActivity(), workHistories);
        historyLiabilitiesAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(historyLiabilitiesAdapter);
        if (workHistories.size() > 0) {
            view.setVisibility(View.VISIBLE);
        } else {
            lnNoData.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void getLiabilitiesError() {
        lnNoData.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}