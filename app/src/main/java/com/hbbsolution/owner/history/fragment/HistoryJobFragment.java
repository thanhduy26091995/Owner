package com.hbbsolution.owner.history.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.history.WorkHistoryView;
import com.hbbsolution.owner.history.adapter.HistoryJobAdapter;
import com.hbbsolution.owner.history.presenter.WorkHistoryPresenter;
import com.hbbsolution.owner.work_management.model.workmanager.Datum;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 15/05/2017.
 */

public class HistoryJobFragment extends Fragment implements WorkHistoryView {
    private View v;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryJobAdapter historyJobAdapter;
    private WorkHistoryPresenter workHistoryPresenter;
    private TextView tvStartDate, tvEndDate;
    private Calendar cal;
    private Date startDate, endDate;
    private String strStartDate, strEndDate;

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
        //Gán adapter các thứ
        workHistoryPresenter = new WorkHistoryPresenter(this);
        workHistoryPresenter.getInfoWorkHistory("0eb910010d0252eb04296d7dc32e657b402290755a85367e8b7a806c7e8bd14b0902e541763a67ef41f2dfb3b9b4919869b609e34dbf6bace4525fa6731d1046", "000000000000000000000005");
        cal = Calendar.getInstance();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_history_job);
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
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        return v;
    }

    @Override
    public void getInfoWorkHistory(List<Datum> listWorkHistory) {

        historyJobAdapter = new HistoryJobAdapter(getActivity(), listWorkHistory);
        historyJobAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(historyJobAdapter);
    }

    @Override
    public void getError() {

    }

    public void showDatePickerDialog1() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                tvStartDate.setText(
                        (dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                startDate = cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s = tvStartDate.getText() + "";
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
                tvEndDate.setText(
                        (dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                endDate = cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s = tvEndDate.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(getActivity(), callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày kết thúc");
        pic.show();
    }

    public void getTime() {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date myDate = new Date();
        strEndDate = date.format(myDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date newDate = calendar.getTime();
        strStartDate=date.format(newDate);
        tvStartDate.setText(strStartDate);
        tvEndDate.setText(strEndDate);
    }
}
