package com.hbbsolution.owner.history.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.WorkHistoryView;
import com.hbbsolution.owner.history.adapter.HistoryJobAdapter;
import com.hbbsolution.owner.history.model.workhistory.WorkHistory;
import com.hbbsolution.owner.history.presenter.WorkHistoryPresenter;
import com.hbbsolution.owner.utils.EndlessRecyclerViewScrollListener;
import com.hbbsolution.owner.utils.ShowAlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 15/05/2017.
 */

public class HistoryJobFragment extends Fragment implements WorkHistoryView {
    private View v, view;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private HistoryJobAdapter historyJobAdapter;
    private WorkHistoryPresenter workHistoryPresenter;
    private TextView tvStartDate, tvEndDate;
    private Calendar cal;
    private Date startDate, endDate, startDateTemp, endDateTemp;
    private String strStartDate, strEndDate;
    private int currentPage, currentPageTime;
    private EndlessRecyclerViewScrollListener scrollListener;
    private List<WorkHistory> mDocList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private ProgressBar progressBar;
    private LinearLayout lnNoData;
    private String tempStartDate, tempEndDate;
    private int startYear, startMonth, startDay, endYear, endMonth, endDay;
    private ViewPager viewPagerDatePicker;
    private TabLayout mTabLayoutDatePicker;

    public HistoryJobFragment() {
    }

    public static HistoryJobFragment newInstance() {
        HistoryJobFragment fragment = new HistoryJobFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_history_job, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progressPost);
        progressBar.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_history_job);
        lnNoData = (LinearLayout) v.findViewById(R.id.lnNoData);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        tvStartDate = (TextView) v.findViewById(R.id.tvStartDate);
        tvEndDate = (TextView) v.findViewById(R.id.tvEndDate);

        view = v.findViewById(R.id.view);
        view.setVisibility(View.INVISIBLE);
        //Gán adapter các thứ
        cal = Calendar.getInstance();
        getTime();

        currentPage = 1;
        currentPageTime = 1;


        workHistoryPresenter = new WorkHistoryPresenter(this);
        workHistoryPresenter.getInfoWorkHistory(currentPage, simpleDateFormat.format(endDate));
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog1();
            }
        });
        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog2();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                currentPageTime = 1;
                recyclerView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (tvStartDate.getText().toString().equals("- - / - - / - - - -")) {
                            workHistoryPresenter.getInfoWorkHistory(currentPage, simpleDateFormat.format(endDate));
                        } else {
                            workHistoryPresenter.getInfoWorkHistoryTime(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate), currentPageTime);
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });
        return v;
    }

    @Override
    public void getInfoWorkHistory(List<WorkHistory> listWorkHistory, final int pages) {
        mDocList.clear();
        mDocList = listWorkHistory;
        historyJobAdapter = new HistoryJobAdapter(getActivity(), mDocList);
        recyclerView.setAdapter(historyJobAdapter);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        progressBar.setVisibility(View.GONE);
        if (listWorkHistory.size() > 0) {
            view.setVisibility(View.VISIBLE);
            lnNoData.setVisibility(View.GONE);
        } else {
            lnNoData.setVisibility(View.VISIBLE);
        }
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // presenter.getAllResort(response.getCurrentPage() + 1);
                //get variables for load more
                if (currentPage < pages) {
                    workHistoryPresenter.getMoreInfoWorkHistory(currentPage + 1, simpleDateFormat.format(endDate));
                    currentPage++;
                }
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void getMoreInfoWorkHistory(List<WorkHistory> listWorkHistory) {
        mDocList.addAll(listWorkHistory);
        historyJobAdapter.notifyDataSetChanged();
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                historyJobAdapter.notifyItemRangeInserted(historyJobAdapter.getItemCount(), mDocList.size() - 1);
            }
        });
    }

    @Override
    public void getInfoWorkHistoryTime(List<WorkHistory> listWorkHistory, final String startAt, final String endAt, final int pages) {
        mDocList.clear();
        mDocList = listWorkHistory;
        historyJobAdapter = new HistoryJobAdapter(getActivity(), mDocList);
        recyclerView.setAdapter(historyJobAdapter);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        progressBar.setVisibility(View.GONE);
        if (listWorkHistory.size() > 0) {
            view.setVisibility(View.VISIBLE);
            lnNoData.setVisibility(View.GONE);
        } else {
            lnNoData.setVisibility(View.VISIBLE);
        }
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // presenter.getAllResort(response.getCurrentPage() + 1);
                //get variables for load more
                if (currentPageTime < pages) {
                    workHistoryPresenter.getMoreInfoWorkHistoryTime(startAt, endAt, currentPageTime + 1);
                    currentPageTime++;
                }
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void getMoreInfoWorkHistoryTime(List<WorkHistory> listWorkHistory, String startAt, String endAt) {
        mDocList.addAll(listWorkHistory);
        historyJobAdapter.notifyDataSetChanged();
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                historyJobAdapter.notifyItemRangeInserted(historyJobAdapter.getItemCount(), mDocList.size() - 1);
            }
        });
    }

    @Override
    public void getError() {
        lnNoData.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
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
                currentPageTime = 1;
                if (endDate.getTime() - startDateTemp.getTime() >= 0) {
                    startDate = startDateTemp;
                    view.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    workHistoryPresenter.getInfoWorkHistoryTime(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate), currentPageTime);
                } else {
                    ShowAlertDialog.showAlert(getResources().getString(R.string.rangetime), getActivity());
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
        DatePickerDialog pic = new DatePickerDialog(getActivity(), callback, nam, thang, ngay);
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
                currentPageTime = 1;
                if (startDate != null) {
                    if (endDateTemp.getTime() - startDate.getTime() >= 0) {
                        endDate = endDateTemp;
                        view.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        workHistoryPresenter.getInfoWorkHistoryTime(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate), currentPageTime);
                    } else {
                        ShowAlertDialog.showAlert(getResources().getString(R.string.rangetime), getActivity());
                        tvEndDate.setText(tempEndDate);
                    }
                } else {
                    endDate = endDateTemp;
                    view.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    workHistoryPresenter.getInfoWorkHistoryTime("", simpleDateFormat.format(endDate), currentPageTime);
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
        pic.setTitle(getResources().getString(R.string.chon_ngay_ket_thuc));
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
    public void connectServerFail() {
        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), getActivity());
    }
}
