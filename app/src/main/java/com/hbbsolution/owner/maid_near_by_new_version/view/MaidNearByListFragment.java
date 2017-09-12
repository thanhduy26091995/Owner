package com.hbbsolution.owner.maid_near_by_new_version.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.InternetConnection;
import com.hbbsolution.owner.maid_near_by_new_version.adapter.MaidNearByNewAdapter;
import com.hbbsolution.owner.maid_near_by_new_version.presenter.MaidNearByNewPresenter;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.utils.ShowSettingLocation;
import com.hbbsolution.owner.utils.ShowSnackbar;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static com.hbbsolution.owner.maid_near_by.view.MaidNearByActivity.REQUEST_ID_ACCESS_COARSE_FINE_LOCATION;

/**
 * Created by buivu on 08/09/2017.
 */

public class MaidNearByListFragment extends Fragment implements LocationListener, MaidNearByNewView {

    private View rootView;
    private LocationManager mLocationManager;
    public static MaidNearByListFragment maidNearByListFragment = null;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute
    private Location location; // location
    private Double mLat, mLng;
    private MaidNearByNewPresenter mMaidNearByNewPresenter;
    private ProgressDialog mProgressDialog;
    private List<Maid> mMaidList;
    private RecyclerView mRecycler;
    private MaidNearByNewAdapter mMaidNearByNewAdapter;
    private SessionManagerUser sessionManagerUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maid_near_by_list, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.recyclerView_maid);
        sessionManagerUser = new SessionManagerUser(getContext());
        maidNearByListFragment = this;
        initComponents();
        return rootView;
    }

    @Override
    public void onResume() {
        getActivity().registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        super.onResume();
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mGpsSwitchStateReceiver);
        super.onDestroy();
    }

    public static MaidNearByListFragment getInstance() {
        return maidNearByListFragment;
    }

    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (statusOfGPS) {
                    final ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setCancelable(false);
                    progressDialog.setTitle(getResources().getString(R.string.notification));
                    progressDialog.setMessage(getResources().getString(R.string.loading));
                    progressDialog.show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            loadData();
                            progressDialog.dismiss();
                        }
                    }, 5000);
                }

            }
        }
    };

    private void initComponents() {
        //init
        mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        mMaidNearByNewPresenter = new MaidNearByNewPresenter(this);
        mMaidList = new ArrayList<>();
        mMaidNearByNewAdapter = new MaidNearByNewAdapter(mMaidList, getActivity());
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mMaidNearByNewAdapter);
        //end init
        if (InternetConnection.getInstance().isOnline(getActivity())) {
            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                ShowSettingLocation.showSettingLocationAlert(getActivity());
            } else {
                //get data
                loadData();
            }
        } else {
            ShowSnackbar.showSnack(getActivity(), getResources().getString(R.string.no_internet));
        }
        //event click adapter
        mMaidNearByNewAdapter.setItemClick(new MaidNearByNewAdapter.OnItemClick() {
            @Override
            public void onItemClickDetail(Maid maid) {
                if (sessionManagerUser.isLoggedIn()) {
                    Intent intent = new Intent(getActivity(), MaidProfileActivity.class);
                    intent.putExtra("maid", maid);
                    startActivity(intent);
                } else {
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.activity), getResources().getString(R.string.loginFirst), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    public void loadData() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                getLocation();
            }
        } else {
            getLocation();
        }

    }

    private void getLocation() {
        try {
            boolean isGPSEnabled = false;
            // flag for network status
            boolean isNetworkEnabled = false;

            // getting GPS status
            isGPSEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                ShowSnackbar.showSnack(getActivity(), getResources().getString(R.string.locationisnotfound));
            } else {
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        //return TODO;
                        mLocationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    }

                    if (mLocationManager != null) {
                        location = mLocationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            mLat = location.getLatitude();
                            mLng = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        mLocationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (mLocationManager != null) {
                            location = mLocationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                mLat = location.getLatitude();
                                mLng = location.getLongitude();
                            }
                        }
                    }
                }
                if (location != null) {
                    showProgress();
                    mMaidNearByNewPresenter.getMaidNearBy(mLat, mLng);
                } else {
                    ShowSnackbar.showSnack(getActivity(), getResources().getString(R.string.locationisnotfound));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showProgress() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getResources().getString(R.string.loading));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private boolean checkLocationPermission() {
        int location = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (location != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ID_ACCESS_COARSE_FINE_LOCATION);
            return false;
        }
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public List<Maid> getCurrentMaidList() {
        return mMaidList;
    }

    public void updateListMaid(List<Maid> maids) {
        mMaidList.clear();
        mMaidList.addAll(maids);
        mMaidNearByNewAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayMaidNearBy(MaidNearByResponse maidNearByResponse) {
        hideProgress();
        if (maidNearByResponse.getStatus()) {
            mMaidList.clear();
            mMaidList.addAll(maidNearByResponse.getData());
            mMaidNearByNewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void displayError(String error) {
        hideProgress();
        if (error.equals("DATA_NOT_EXIST")) {
            mMaidList.clear();
            mMaidNearByNewAdapter.notifyDataSetChanged();
        } else {
            ShowAlertDialog.showAlert(getResources().getString(R.string.loi_thu_lai), getActivity());
        }
    }

    @Override
    public void connectServerFail() {
        hideProgress();
        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), getActivity());
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case REQUEST_ID_ACCESS_COARSE_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadData();
                }
                break;
        }

    }
}
