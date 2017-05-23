package com.hbbsolution.owner.maid_near_by.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.maid_near_by.model.MarkerInfoWindowAdapter;
import com.hbbsolution.owner.maid_near_by.presenter.MaidNearByPresenter;
import com.hbbsolution.owner.maid_near_by.view.filter.view.FilterActivity;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.model.MaidNearByResponse;
import com.hbbsolution.owner.utils.Constants;
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

public class MaidNearByActivity extends AppCompatActivity implements MaidNearByView, OnMapReadyCallback {

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

    private MaidNearByPresenter presenter;

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
        mTextTitle.setText("Người giúp việc quanh đây");
        //get data
        loadData();
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
                Log.d("RESULT", query);
                //hide keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
        presenter.getMaidNearBy(10.767200, 106.687738);
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
            Intent intent = new Intent(MaidNearByActivity.this, FilterActivity.class);
            intent.putExtra(Constants.LAT, 10.767200);
            intent.putExtra(Constants.LNG, 106.687738);
            startActivityForResult(intent, Constants.FILTER_MAID_INTENT);
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
            updateMap(googleMap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void displayMaidNearBy(MaidNearByResponse maidNearByResponse) {
        maidInfoList = maidNearByResponse.getData();
        updateMap(googleMap);
    }

    @Override
    public void displayError(String error) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

}
