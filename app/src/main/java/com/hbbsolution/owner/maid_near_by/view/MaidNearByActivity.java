package com.hbbsolution.owner.maid_near_by.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.InternetConnection;
import com.hbbsolution.owner.maid_near_by.model.MarkerInfoWindowAdapter;
import com.hbbsolution.owner.maid_near_by.presenter.MaidNearByPresenter;
import com.hbbsolution.owner.maid_near_by.view.filter.view.FilterActivity;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.utils.ShowSnackbar;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 18/05/2017.
 */

public class MaidNearByActivity extends AppCompatActivity implements MaidNearByView, OnMapReadyCallback, LocationListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.search_view)
    SearchView searchView;


    private GoogleMap googleMap;
    private List<Maid> maidInfoList = new ArrayList<>();
    private HashMap<Marker, Maid> myMarkerHashMap = new HashMap<>();
    private HashMap<String, Boolean> markerLoadImage = new HashMap<>();
    private LocationManager locationManager;
    private MaidNearByPresenter presenter;
    public static final int REQUEST_ID_ACCESS_COARSE_FINE_LOCATION = 101;
    private LatLng latLng;
    private static final String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute
    private Location location; // location
    private Double latitude; // latitude
    private Double longitude; // longitude
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_near_by);
        ButterKnife.bind(this);
        //init
        setUpMapIfNeeded();
        presenter = new MaidNearByPresenter(this);
        //init toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        mTextTitle.setText(getResources().getString(R.string.maid_near_by));
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (InternetConnection.getInstance().isOnline(MaidNearByActivity.this)) {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                showSettingLocationAlert();
            } else {
                //get data
                loadData();
            }
        } else {
            ShowSnackbar.showSnack(MaidNearByActivity.this, getResources().getString(R.string.no_internet));
        }
        //  searchView.setIconified(false);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        //implement searchview
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //hide keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                showProgress();
                //call apk to search
                presenter.getLocationAddress(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void showProgress() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.loading));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void hideProgress() {
        if (mProgressDialog.isShowing() && mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
    }


    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
                            progressDialog.hide();
                        }
                    }, 6000);
                }

            }
        }
    };


    @Override
    protected void onResume() {
        registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mGpsSwitchStateReceiver);
        super.onDestroy();
    }

    private void showSettingLocationAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set title
        builder.setTitle(getResources().getString(R.string.GPSTitle));
        //set message
        builder.setMessage(getResources().getString(R.string.GPSContent));
        //on press
        builder.setPositiveButton(getResources().getString(R.string.setting), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent settingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(settingIntent);
            }
        });
        //on cancel
        builder.setNegativeButton(getResources().getString(R.string.huy), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void setUpMapIfNeeded() {
        if (googleMap == null) {
            SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
            mapFrag.getMapAsync(this);
            if (googleMap != null) {
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        marker.showInfoWindow();
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        return true;
                    }
                });
            }
        }

    }

    private void loadData() {
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
            boolean canGetLocation = false;

            LocationManager locationManager;
            locationManager = (LocationManager) this
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        //return TODO;
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    }

                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.d("LATLNG", "" + location.getLatitude() + "/" + location.getLongitude());
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                Log.d("LATLNG", "" + location.getLatitude() + "/" + location.getLongitude());
                            }
                        }
                    }
                }
                if (location != null) {
                    Log.d("LATLNG", "" + location.getLatitude() + "/" + location.getLongitude());
                    showProgress();
                    presenter.getMaidNearBy(location.getLatitude(), location.getLongitude());
                } else {
                    Toast.makeText(MaidNearByActivity.this, "Location not found!", Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        // return location;
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //TODO:
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                //(just doing it here for now, note that with this code, no explanation is shown)
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ID_ACCESS_COARSE_FINE_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ID_ACCESS_COARSE_FINE_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private void updateMap(GoogleMap googleMap) {
        int countLengthMaid = 0;
        for (Maid maidInfo : maidInfoList) {
            if (countLengthMaid == 0) {
                double lat = maidInfo.getInfo().getAddress().getCoordinates().getLat();
                Double lng = maidInfo.getInfo().getAddress().getCoordinates().getLng();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
            }
            countLengthMaid++;
            double lat = maidInfo.getInfo().getAddress().getCoordinates().getLat();
            double lng = maidInfo.getInfo().getAddress().getCoordinates().getLng();
            MarkerOptions markerOptions = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_maid)).position(new LatLng(lat, lng));
            Marker currentMarker = googleMap.addMarker(markerOptions);
            myMarkerHashMap.put(currentMarker, maidInfo);
            markerLoadImage.put(currentMarker.getId(), false);
            googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(MaidNearByActivity.this, myMarkerHashMap, markerLoadImage, googleMap));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter_maid, menu);
        menu.findItem(R.id.action_filter).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_sliders)
                        .color(R.color.home_background_history)
                        .colorRes(R.color.home_background_history)
                        .sizeDp(24)
                        .actionBarSize()
        );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_filter) {
            // if (location != null) {
            Intent intent = new Intent(MaidNearByActivity.this, FilterActivity.class);
            intent.putExtra(Constants.LAT, latitude);
            intent.putExtra(Constants.LNG, longitude);
            startActivityForResult(intent, Constants.FILTER_MAID_INTENT);
            // }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.FILTER_MAID_INTENT && resultCode == RESULT_OK) {
            googleMap.clear();
            maidInfoList.clear();
            myMarkerHashMap.clear();
            markerLoadImage.clear();
            maidInfoList = (List<Maid>) data.getSerializableExtra(String.valueOf(Constants.MAID_LIST));
            if (maidInfoList.size() == 0) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.activity), getResources().getString(R.string.no_result_found), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            updateMap(googleMap);
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void displayMaidNearBy(MaidNearByResponse maidNearByResponse) {
        hideProgress();
        hideKeyboard();
        maidInfoList = maidNearByResponse.getData();
        updateMap(googleMap);
    }

    @Override
    public void displayError(String error) {
        hideKeyboard();
        hideProgress();
        Log.d("ERROR", error);
    }

    @Override
    public void displaySearchResult(MaidNearByResponse maidNearByResponse) {
        hideKeyboard();
        hideProgress();
        googleMap.clear();
        maidInfoList.clear();
        myMarkerHashMap.clear();
        markerLoadImage.clear();
        maidInfoList = maidNearByResponse.getData();
        if (maidInfoList.size() == 0) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity), "Không tìm thấy kết quả", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        updateMap(googleMap);
    }

    @Override
    public void getLocationAddress(GeoCodeMapResponse geoCodeMapResponse) {
        hideProgress();
        hideKeyboard();
        Double lat = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
        Double lng = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();

        if (lat != 0 && lng != 0) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
            presenter.searchMaid(lat, lng);
        }
    }

    @Override
    public void displayNotFoundLocation(String error) {
        hideProgress();
        ShowAlertDialog.showAlert(error, MaidNearByActivity.this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        googleMap.setMyLocationEnabled(true);
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
}
