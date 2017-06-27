package com.hbbsolution.owner.maid_near_by.view.filter.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.BottomSheetAdapter;
import com.hbbsolution.owner.base.InternetConnection;
import com.hbbsolution.owner.maid_near_by.view.filter.presenter.FilterPresenter;
import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.model.TypeJob;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.ShowSnackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 22/05/2017.
 */

public class FilterActivity extends AppCompatActivity implements View.OnClickListener, FilterView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.seekBar_distance)
    SeekBar seekBarDistance;
    @BindView(R.id.text_distance)
    TextView txtDistance;
    @BindView(R.id.linear_gender)
    LinearLayout linearGender;
    @BindView(R.id.txt_filter_gender)
    TextView txtGender;
    @BindView(R.id.linear_type_job)
    LinearLayout linearTypeJob;
    @BindView(R.id.txt_type_job)
    TextView txtTypeJob;
    @BindView(R.id.linear_price)
    LinearLayout linearPrice;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.linear_old)
    LinearLayout linearOld;
    @BindView(R.id.txt_filter_old)
    TextView txtOld;
    @BindView(R.id.txt_filter)
    TextView txtFilter;

    private HashMap<String, String> hashMapTypeJob = new HashMap<>();
    private List<String> listTypeJobName = new ArrayList<>();
    private FilterPresenter presenter;
    private int fromOld = 18, toOld = 18;
    private Double lat, lng;
    private Integer gender = null, maxDistance = null, priceMin = null, priceMax = null;
    private String workId = null;
    private boolean isChooseOld = false, isChoosePrice = false;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        //init
        presenter = new FilterPresenter(this);
        presenter.getAllTypeJob();
        //get intent
        lat = (Double) getIntent().getDoubleExtra(Constants.LAT, 0);
        lng = (Double) getIntent().getDoubleExtra(Constants.LNG, 0);
        //init toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        //event change filter
        seekBarDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maxDistance = progress;
                txtDistance.setText(String.format("%d km", progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //event click
        linearGender.setOnClickListener(this);
        linearTypeJob.setOnClickListener(this);
        linearPrice.setOnClickListener(this);
        linearOld.setOnClickListener(this);
        txtFilter.setOnClickListener(this);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_done) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_filter_done, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        if (v == linearGender) {
            final List<String> listGender = new ArrayList<>();
            listGender.add(getResources().getString(R.string.pro_file_gender_male));
            listGender.add(getResources().getString(R.string.pro_file_gender_female));
            showBottomSheetGender(listGender, txtGender);
        } else if (v == linearTypeJob) {
            if (listTypeJobName.size() > 0) {
                showBottomSheetGender(listTypeJobName, txtTypeJob);
            }
        } else if (v == linearPrice) {
            List<String> listPrice = new ArrayList<>();
            listPrice.add("< 50.000d");
            listPrice.add("50.000d - 150.000d");
            listPrice.add("150.000d - 250.000d");
            listPrice.add("250.000d - 350.000d");
            listPrice.add("350.000d - 450.000d");
            listPrice.add("450.000d +");
            showBottomSheetGender(listPrice, txtPrice);
        } else if (v == linearOld) {
            showDialogFilterOld();
        } else if (v == txtFilter) {
            Integer ageMin = null, ageMax = null;
            //filter project
            workId = hashMapTypeJob.get(txtTypeJob.getText().toString());
            if (txtGender.getText().toString().equals("Nam") || txtGender.getText().toString().equals("Male")) {
                gender = 0;
            } else if (txtGender.getText().toString().equals("Nữ") || txtGender.getText().toString().equals("Female")) {
                gender = 1;
            }
            if (isChooseOld) {
                ageMin = fromOld;
                ageMax = toOld;
            }

            if (isChoosePrice) {
                if (txtPrice.getText().equals("< 50.000d")) {
                    priceMax = 50000;
                } else if (txtPrice.getText().equals("50.000d - 150.000d")) {
                    priceMin = 50000;
                    priceMax = 150000;
                } else if (txtPrice.getText().equals("150.000d - 250.000d")) {
                    priceMin = 150000;
                    priceMax = 250000;
                } else if (txtPrice.getText().equals("250.000d - 350.000d")) {
                    priceMin = 250000;
                    priceMax = 350000;
                } else if (txtPrice.getText().equals("350.000d - 450.000d")) {
                    priceMin = 350000;
                    priceMax = 450000;
                } else {
                    priceMin = 450000;
                }
            }
            if (InternetConnection.getInstance().isOnline(FilterActivity.this)) {
                //disable button
                txtFilter.setEnabled(false);
                showProgressDialog();
                //save
                presenter.filterMaid(lat, lng, ageMin, ageMax, gender, maxDistance, priceMin, priceMax, workId);
            } else {
                ShowSnackbar.showSnack(FilterActivity.this, getResources().getString(R.string.no_internet));
            }
        }
    }

    private void showDialogFilterOld() {
        final Dialog dialog = new Dialog(FilterActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_layout_choose_old);
        //reset variables
        fromOld = 18;
        toOld = 18;
       /*
       Init components
        */
        //format position dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);

        final NumberPicker numberPickerFrom = (NumberPicker) dialog.findViewById(R.id.num_picker_from);
        final NumberPicker numberPickerTo = (NumberPicker) dialog.findViewById(R.id.num_picker_to);
        //set max and min
        numberPickerFrom.setMinValue(18);
        numberPickerFrom.setMaxValue(60);

        numberPickerTo.setMinValue(18);
        numberPickerTo.setMaxValue(60);
        //event click
        numberPickerFrom.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                fromOld = newVal;
                if (fromOld > toOld) {
                    numberPickerTo.setValue(fromOld);
                    toOld = newVal;
                }
            }
        });

        numberPickerTo.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                toOld = newVal;
                if (toOld < fromOld) {
                    numberPickerFrom.setValue(toOld);
                    fromOld = newVal;
                }
            }
        });
        //event click dialog
        TextView txtCancel = (TextView) dialog.findViewById(R.id.txt_filter_cancel);
        TextView txtOk = (TextView) dialog.findViewById(R.id.txt_filter_ok);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromOld == toOld) {
                    txtOld.setText(String.format("%d %s", fromOld, getResources().getString(R.string.old)));
                } else {
                    txtOld.setText(String.format("%s %d %s %d", getResources().getString(R.string.from), fromOld, getResources().getString(R.string.to), toOld));
                }
                isChooseOld = true;
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void showBottomSheetGender(final List<String> listData, final TextView txtShow) {
        //init view
        View view = getLayoutInflater().inflate(R.layout.job_post_bottom_sheet, null);
        //map components
        TextView txtCancel = (TextView) view.findViewById(R.id.txt_cancel);
        RecyclerView mRecycler = (RecyclerView) view.findViewById(R.id.recy_type_job);

        mRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //create bottom sheet
        BottomSheetAdapter mTypeJobtAdapter = new BottomSheetAdapter(FilterActivity.this, listData);
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(mTypeJobtAdapter);
        mTypeJobtAdapter.notifyDataSetChanged();

        final Dialog mBottomSheetDialog = new Dialog(FilterActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        mTypeJobtAdapter.setCallback(new BottomSheetAdapter.Callback() {
            @Override
            public void onItemClick(int position) {
                if (txtShow.getId() == R.id.txt_price) {
                    isChoosePrice = true;
                }
                txtShow.setText(listData.get(position));
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

    }

    @Override
    public void filterMaid(MaidNearByResponse maidNearByResponse) {
        txtFilter.setEnabled(true);
        hideProgressDialog();
        if (maidNearByResponse.getData().size() >= 0) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(Constants.MAID_LIST, (Serializable) maidNearByResponse.getData());
            setResult(RESULT_OK, resultIntent);
        }
        finish();

    }
}
