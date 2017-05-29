package com.hbbsolution.owner.history.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.HelperHistoryView;
import com.hbbsolution.owner.history.adapter.HistoryHelperAdapter;
import com.hbbsolution.owner.history.model.helper.Datum;
import com.hbbsolution.owner.history.presenter.HelperHistoryPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 15/05/2017.
 */

public class HistoryHelperFragment extends Fragment implements HelperHistoryView {
    private View v;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryHelperAdapter historyHelperAdapter;
    private TextView tvStartDate, tvEndDate;
    private Calendar cal;
    private Date startDate,endDate;
    private String strStartDate, strEndDate;
    private HelperHistoryPresenter helperHistoryPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public static HistoryHelperFragment newInstance() {
        HistoryHelperFragment fragment = new HistoryHelperFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_history_helper, container, false);
        //Gán adapter các thứ
        mSwipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_history_helper);
        layoutManager = new LinearLayoutManager(getActivity());
        helperHistoryPresenter = new HelperHistoryPresenter(this);
        recyclerView.setLayoutManager(layoutManager);

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

        helperHistoryPresenter.getInfoHelperHistory(simpleDateFormat.format(endDate));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        return v;
    }
    void refreshItems() {
        // Load items
        // ...
        if(tvStartDate.getText().toString().equals("- - / - - / - - - -")) {
            helperHistoryPresenter.getInfoHelperHistory(simpleDateFormat.format(endDate));
        }
        else
        {
            helperHistoryPresenter.getInfoHelperHistoryTime(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
        }
        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
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
                view.setVisibility(View.INVISIBLE);
                if(endDate!=null) {
                    helperHistoryPresenter.getInfoHelperHistoryTime(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
                }
                else
                {
                    helperHistoryPresenter.getInfoHelperHistoryTime(simpleDateFormat.format(startDate), "");
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
                view.setVisibility(View.INVISIBLE);
                if(startDate!=null) {
                    helperHistoryPresenter.getInfoHelperHistoryTime(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
                }
                else
                {
                    helperHistoryPresenter.getInfoHelperHistoryTime("", simpleDateFormat.format(endDate));
                }
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=tvEndDate.getText().toString();
        if(tvEndDate.getText().toString().equals("- - / - - / - - - -")) {
            s = strEndDate;
        }
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
    public void getInfoHelperHistory(List<Datum> datumList) {
        historyHelperAdapter = new HistoryHelperAdapter(getActivity(),datumList);
        historyHelperAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(historyHelperAdapter);
    }

    @Override
    public void getInfoHelperHistoryFail() {

    }
}
