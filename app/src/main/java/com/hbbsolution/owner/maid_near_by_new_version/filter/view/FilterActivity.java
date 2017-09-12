package com.hbbsolution.owner.maid_near_by_new_version.filter.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.adapter.BottomSheetAdapter;
import com.hbbsolution.owner.base.AuthenticationBaseActivity;
import com.hbbsolution.owner.base.InternetConnection;
import com.hbbsolution.owner.maid_near_by_new_version.filter.model.FilterModel;
import com.hbbsolution.owner.maid_near_by_new_version.filter.model.FilterModelSingleton;
import com.hbbsolution.owner.maid_near_by_new_version.filter.presenter.FilterPresenter;
import com.hbbsolution.owner.model.Item;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.model.TypeJob;
import com.hbbsolution.owner.model.TypeJobResponse;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.utils.ShowSnackbar;
import com.hbbsolution.owner.utils.messagedialog.DialogResulltItem;
import com.hbbsolution.owner.utils.messagedialog.MessageDialogHelper;
import com.hbbsolution.owner.utils.messagedialog.MessageDialogManger;
import com.hbbsolution.owner.utils.messagedialog.OnClickDialogListener;
import com.hbbsolution.owner.utils.messagedialog.TypeMessageDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 22/05/2017.
 */

public class FilterActivity extends AuthenticationBaseActivity implements View.OnClickListener, FilterView, OnClickDialogListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.linear_location)
    LinearLayout mLinearLocation;
    @BindView(R.id.txt_location)
    TextView mTextViewLocation;
    @BindView(R.id.txt_distance)
    TextView mTextViewDistance;
    @BindView(R.id.linear_distance)
    LinearLayout mLinearDistance;
    @BindView(R.id.linear_gender)
    LinearLayout mLinearGender;
    @BindView(R.id.txt_filter_gender)
    TextView mTextViewGender;
    @BindView(R.id.linear_type_job)
    LinearLayout mLinearTypeJob;
    @BindView(R.id.txt_type_job)
    TextView mTextViewTypeJob;
    @BindView(R.id.linear_price)
    LinearLayout mLinearPrice;
    @BindView(R.id.txt_price)
    TextView mTextViewPrice;
    @BindView(R.id.linear_old)
    LinearLayout mLinearOld;
    @BindView(R.id.txt_filter_old)
    TextView mTextViewOld;
    @BindView(R.id.btn_update)
    Button mButtonUpdate;

    private FilterPresenter presenter;
    private int fromOld = 18, toOld = 18;
    private Double mLat, mLng;
    private Integer gender = null, maxDistance = null, priceMin = null, priceMax = null;
    private String workId = null;
    private boolean isChooseOld = false, isChoosePrice = false;
    private ProgressDialog progressDialog;
    private MessageDialogManger mMessageDialogManger;
    private static final int dialogDistance = 100, PLACE_PICKER_REQUEST = 101, dialogTypeJob = 102, dialogPrice = 103, dialogGender = 104;
    private List<Item> mListItemDistance, mListItemPrice, mListItemTypeOfWork, mListItemGender;
    private Integer ageMin = null, ageMax = null;
    private String mAddress, mWorkName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        mMessageDialogManger = new MessageDialogManger();
        initDataList();
        showDataFilter();
        //init
        presenter = new FilterPresenter(this);
        presenter.getAllTypeJob();
        //init toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        //event click
        mLinearGender.setOnClickListener(this);
        mLinearTypeJob.setOnClickListener(this);
        mLinearPrice.setOnClickListener(this);
        mLinearOld.setOnClickListener(this);
        mLinearDistance.setOnClickListener(this);
        mLinearLocation.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
    }

    private void initDataList() {
        mListItemDistance = new ArrayList<>();
        mListItemGender = new ArrayList<>();
        mListItemPrice = new ArrayList<>();
        mListItemTypeOfWork = new ArrayList<>();

        //save data to list distance
        for (int i = 0; i <= 99; i++) {
            mListItemDistance.add(new Item(String.valueOf(i), String.valueOf(i)));
        }
        //save data to list gender
        Item itemMale = new Item(String.valueOf(0), getResources().getString(R.string.pro_file_gender_male));
        Item itemFemale = new Item(String.valueOf(1), getResources().getString(R.string.pro_file_gender_female));
        mListItemGender.add(itemMale);
        mListItemGender.add(itemFemale);
        //save data to list price
        for (String price : getResources().getStringArray(R.array.maid_near_by_price)) {
            mListItemPrice.add(new Item(String.valueOf(0), price));
        }
    }

    private void showDataFilter() {
        if (FilterModelSingleton.getInstance().getIsSaved()) {
            FilterModel filterModel = FilterModelSingleton.getInstance().getFilterModel();
            //load data
            mLat = filterModel.getLat();
            mLng = filterModel.getLng();
            mAddress = filterModel.getAddress();
            if (!TextUtils.isEmpty(filterModel.getAddress())) {
                mTextViewLocation.setText(filterModel.getAddress());
            }
            maxDistance = filterModel.getDistance();
            if (maxDistance != null) {
                mTextViewDistance.setText(String.format("%d %s", maxDistance, "km"));
            }
            priceMin = filterModel.getPriceMin();
            priceMax = filterModel.getPriceMax();
            if (priceMax != null && priceMin != null) {
                mTextViewPrice.setText(String.format("%dd - %dd", priceMin, priceMax));
            } else {
                if (priceMax == null && priceMin == null) {
                    mTextViewPrice.setText(getResources().getString(R.string.price));
                } else {
                    if (priceMin == null) {
                        mTextViewPrice.setText("< 50000d");
                    } else {
                        mTextViewPrice.setText("> 450000d");
                    }
                }
            }
            workId = filterModel.getWorkId();
            if (filterModel.getWorkName() != null) {
                mTextViewTypeJob.setText(filterModel.getWorkName());
            } else {
                mTextViewTypeJob.setText(getResources().getString(R.string.types_of_work));
            }
            gender = filterModel.getGender();
            if (gender != null) {
                if (gender == 1) {
                    mTextViewGender.setText(getResources().getString(R.string.pro_file_gender_female));
                } else {
                    mTextViewGender.setText(getResources().getString(R.string.pro_file_gender_male));
                }
            } else {
                mTextViewGender.setText(getResources().getString(R.string.gender));
            }
            ageMin = filterModel.getAgeMin();
            ageMax = filterModel.getAgeMax();
            if (ageMax != null && ageMin != null) {
                if (ageMin == ageMax) {
                    mTextViewOld.setText(String.format("%d %s", ageMax, getResources().getString(R.string.old)));
                } else {
                    mTextViewOld.setText(String.format("%s %d %s %d", getResources().getString(R.string.from), ageMin, getResources().getString(R.string.to), ageMax));
                }
            } else {
                mTextViewOld.setText(getResources().getString(R.string.sign_up_age));
            }
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
        switch (v.getId()) {
            case R.id.linear_gender: {
                showDialogChooseItem(dialogGender, getResources().getString(R.string.gender), mListItemGender);
                break;
            }
            case R.id.linear_type_job: {
                showDialogChooseItem(dialogTypeJob, getResources().getString(R.string.types_of_work), mListItemTypeOfWork);
                break;
            }
            case R.id.linear_price: {
                showDialogChooseItem(dialogPrice, getResources().getString(R.string.price), mListItemPrice);
                break;
            }
            case R.id.linear_old: {
                showDialogFilterOld();
                break;
            }
            case R.id.linear_distance: {
                showDialogChooseItem(dialogDistance, getResources().getString(R.string.distance), mListItemDistance);
                break;
            }
            case R.id.linear_location: {
                mLinearLocation.setEnabled(false);
                showGooglePlaces();
                break;
            }
            case R.id.btn_update: {
                if (isChooseOld) {
                    ageMin = fromOld;
                    ageMax = toOld;
                }

                if (InternetConnection.getInstance().isOnline(this)) {
                    //save data into filtermodel
                    FilterModel filterModel = new FilterModel(mLat, mLng, mAddress, maxDistance, priceMin, priceMax, workId, mWorkName,
                            gender, ageMax, ageMin);
                    FilterModelSingleton.getInstance().saveFilterModel(filterModel);
                    mButtonUpdate.setEnabled(false);
                    showProgress();
                    presenter.filterMaid(mLat, mLng, ageMin, ageMax, gender, maxDistance, priceMin, priceMax, workId);
                } else {
                    ShowSnackbar.showSnack(FilterActivity.this, getResources().getString(R.string.no_internet));
                }
                Log.d("RESULT", "" + priceMin + " / " + priceMax + "/ " + gender + "/ " + workId + "/ " + maxDistance + "/ " + mLat + "/ " + mLng + "/ " + ageMin + "/ " + ageMax);
                break;
            }
        }
//        if (v == mLinearGender) {
//            final List<String> listGender = new ArrayList<>();
//            listGender.add(getResources().getString(R.string.pro_file_gender_male));
//            listGender.add(getResources().getString(R.string.pro_file_gender_female));
//            showBottomSheetGender(listGender, mTextViewGender);
//        } else if (v == mLinearTypeJob) {
//            if (listTypeJobName.size() > 0) {
//                showBottomSheetGender(listTypeJobName, mTextViewTypeJob);
//            }
//        } else if (v == mLinearPrice) {
//            List<String> listPrice = new ArrayList<>();
//            listPrice.add("< 50.000d");
//            listPrice.add("50.000d - 150.000d");
//            listPrice.add("150.000d - 250.000d");
//            listPrice.add("250.000d - 350.000d");
//            listPrice.add("350.000d - 450.000d");
//            listPrice.add("450.000d +");
//            showBottomSheetGender(listPrice, mTextViewPrice);
//        } else if (v == mLinearOld) {
//            showDialogFilterOld();
//        } else if (v == mButtonUpdate) {
//            Integer ageMin = null, ageMax = null;
//            //filter project
//            workId = hashMapTypeJob.get(mTextViewTypeJob.getText().toString());
//            Log.d("WORK_FILTER", mTextViewTypeJob.getText().toString() + "/" + workId);
//            if (mTextViewGender.getText().toString().equals("Nam") || mTextViewGender.getText().toString().equals("Male")) {
//                gender = 0;
//            } else if (mTextViewGender.getText().toString().equals("Nữ") || mTextViewGender.getText().toString().equals("Female")) {
//                gender = 1;
//            }
//            if (isChooseOld) {
//                ageMin = fromOld;
//                ageMax = toOld;
//            }
//
//            if (isChoosePrice) {
//                if (mTextViewPrice.getText().equals("< 50.000d")) {
//                    priceMax = 50000;
//                } else if (mTextViewPrice.getText().equals("50.000d - 150.000d")) {
//                    priceMin = 50000;
//                    priceMax = 150000;
//                } else if (mTextViewPrice.getText().equals("150.000d - 250.000d")) {
//                    priceMin = 150000;
//                    priceMax = 250000;
//                } else if (mTextViewPrice.getText().equals("250.000d - 350.000d")) {
//                    priceMin = 250000;
//                    priceMax = 350000;
//                } else if (mTextViewPrice.getText().equals("350.000d - 450.000d")) {
//                    priceMin = 350000;
//                    priceMax = 450000;
//                } else {
//                    priceMin = 450000;
//                }
//            }
//            if (InternetConnection.getInstance().isOnline(FilterActivity.this)) {
//                //disable button
//                // mButtonUpdate.setEnabled(false);
//                showProgress();
//                //save
//                presenter.filterMaid(mLat, mLng, ageMin, ageMax, gender, maxDistance, priceMin, priceMax, workId);
//            } else {
//                ShowSnackbar.showSnack(FilterActivity.this, getResources().getString(R.string.no_internet));
//            }
//        }
    }

    @Override
    public void onClickDialog(DialogResulltItem dialogResulltItem) {
        switch (dialogResulltItem.getDialogId()) {
            case dialogPrice: {
                if (dialogResulltItem.getObject() != null) {
                    //reset biến
                    priceMax = null;
                    priceMin = null;
                    Item item = (Item) dialogResulltItem.getObject();
                    mTextViewPrice.setText(item.getTitle());
                    findPriceMinAndMax(item.getTitle());
                    isChoosePrice = true;
                }
                mMessageDialogManger.onDimiss();
                break;
            }
            case dialogGender: {
                if (dialogResulltItem.getObject() != null) {
                    Item item = (Item) dialogResulltItem.getObject();
                    mTextViewGender.setText(item.getTitle());
                    gender = Integer.parseInt(item.getId());
                }
                mMessageDialogManger.onDimiss();
                break;
            }
            case dialogTypeJob: {
                if (dialogResulltItem.getObject() != null) {
                    Item item = (Item) dialogResulltItem.getObject();
                    workId = item.getId();
                    mWorkName = item.getTitle();
                    mTextViewTypeJob.setText(item.getTitle());
                }
                mMessageDialogManger.onDimiss();
                break;
            }
            case dialogDistance: {
                if (dialogResulltItem.getObject() != null) {
                    Item item = (Item) dialogResulltItem.getObject();
                    maxDistance = Integer.parseInt(item.getId());
                    //show data
                    mTextViewDistance.setText(String.format("%s km", item.getId()));
                }
                mMessageDialogManger.onDimiss();
                break;
            }
        }
    }

    private void showGooglePlaces() {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent myIntent;
            myIntent = builder.build(FilterActivity.this);
            startActivityForResult(myIntent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                mAddress = String.format("%s", place.getAddress());
                mTextViewLocation.setText(mAddress);
                mLat = place.getLatLng().latitude;
                mLng = place.getLatLng().longitude;
            }
            //un-block linear address
            mLinearLocation.setEnabled(true);
        }

    }

    private void findPriceMinAndMax(String price) {
        if (price.equals("< 50.000d")) {
            priceMax = 50000;
        } else if (price.equals("50.000d - 150.000d")) {
            priceMin = 50000;
            priceMax = 150000;
        } else if (price.equals("150.000d - 250.000d")) {
            priceMin = 150000;
            priceMax = 250000;
        } else if (price.equals("250.000d - 350.000d")) {
            priceMin = 250000;
            priceMax = 350000;
        } else if (price.equals("350.000d - 450.000d")) {
            priceMin = 350000;
            priceMax = 450000;
        } else {
            priceMin = 450000;
        }
    }

    private void showDialogChooseItem(int dialogId, String header, List<Item> mItemList) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGE_DIALOG_TYPE_CHOOSE_ITEM)
                .setDialogId(dialogId)
                .setTitleDialog(header)
                .setItemList(mItemList)
                .setOnClickDialogListener(this)
                .build(this));
        mMessageDialogManger.onShow();
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
                    mTextViewOld.setText(String.format("%d %s", fromOld, getResources().getString(R.string.old)));
                } else {
                    mTextViewOld.setText(String.format("%s %d %s %d", getResources().getString(R.string.from), fromOld, getResources().getString(R.string.to), toOld));
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
        //save data type job
        mListItemTypeOfWork.add(new Item(null, getResources().getString(R.string.maid_near_by_all_work)));
        for (TypeJob typeJob : compareValueInModel(typeJobResponse.getData())) {
            mListItemTypeOfWork.add(new Item(typeJob.getId(), typeJob.getName()));
        }
    }

    //reverse array
    private List<TypeJob> compareValueInModel(List<TypeJob> list) {
        Collections.sort(list, new Comparator<TypeJob>() {
            public int compare(TypeJob obj1, TypeJob obj2) {
                return Integer.valueOf((int) obj1.getWeight()).compareTo((int) obj2.getWeight()); // To compare integer values
            }
        });
        return list;
    }

    @Override
    public void displayError(String error) {
        hideProgress();
        mButtonUpdate.setEnabled(true);
        if (error.equals("DATA_NOT_EXIST")) {
            Intent resultIntent = new Intent();
            List<Maid> maidList = new ArrayList<>();
            resultIntent.putExtra(Constants.MAID_LIST, (Serializable) maidList);
            setResult(RESULT_OK, resultIntent);
        }
        finish();
    }

    @Override
    public void filterMaid(MaidNearByResponse maidNearByResponse) {
        mButtonUpdate.setEnabled(true);
        hideProgress();
        if (maidNearByResponse.getData().size() >= 0) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(Constants.MAID_LIST, (Serializable) maidNearByResponse.getData());
            setResult(RESULT_OK, resultIntent);
        }
        finish();

    }

    @Override
    public void connectServerFail() {
        hideProgress();
        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), this);
    }


}
