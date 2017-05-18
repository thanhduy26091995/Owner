package com.hbbsolution.owner.maid_near_by.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import com.hbbsolution.owner.maid_near_by.model.MyMarker;
import com.hbbsolution.owner.maid_near_by.presenter.MaidNearByPresenter;
import com.hbbsolution.owner.model.MaidInfo;
import com.hbbsolution.owner.model.MaidNearByResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 18/05/2017.
 */

public class MaidNearByActivity extends AppCompatActivity implements OnMapReadyCallback, MaidNearByView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.text_title)
    TextView mTextTitle;

    private GoogleMap googleMap;
    private List<MaidInfo> maidInfoList = new ArrayList<>();
    private HashMap<Marker, MyMarker> myMarkerHashMap = new HashMap<>();
    private HashMap<String, Boolean> markerLoadImage = new HashMap<>();
    private ArrayList<MyMarker> myMarkers = new ArrayList<>();

    private MaidNearByPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_near_by);
        ButterKnife.bind(this);
        //init
        presenter = new MaidNearByPresenter(this);
        //init toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        mTextTitle.setText("Người giúp việc quanh đây");
        //set up map
        setUpMapIfNeeded();
        loadData();
    }

    private void loadData() {
        presenter.getMaidNearBy("0eb910010d0252eb04296d7dc32e657b402290755a85367e8b7a806c7e8bd14b0902e541763a67ef41f2dfb3b9b4919869b609e34dbf6bace4525fa6731d1046", 10.767200, 106.687738);
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

    private void updateMap(GoogleMap googleMap) {
        int countLengthMaid = 0;
        for (MaidInfo maidInfo : maidInfoList) {
            if (countLengthMaid == 0) {
                double lat = maidInfo.getInfo().getAddress().getCoordinates().getLat();
                Double lng = maidInfo.getInfo().getAddress().getCoordinates().getLng();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
            }
            countLengthMaid++;
            String name = maidInfo.getInfo().getUsername();
            String imageUrl = maidInfo.getInfo().getImage();
            int price = maidInfo.getWorkInfo().getPrice();
            double lat = maidInfo.getInfo().getAddress().getCoordinates().getLat();
            double lng = maidInfo.getInfo().getAddress().getCoordinates().getLng();
            String maidId = maidInfo.getId();
            myMarkers.add(new MyMarker(name, imageUrl, String.valueOf(price), lat, lng, maidId));
        }
        if (myMarkers.size() > 0) {
            for (MyMarker myMarker : myMarkers) {
                MarkerOptions markerOptions = new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_maid)).position(new LatLng(myMarker.getLat(), myMarker.getLng()));
                Marker currentMarker = googleMap.addMarker(markerOptions);
                myMarkerHashMap.put(currentMarker, myMarker);
                markerLoadImage.put(currentMarker.getId(), false);
                googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(this, myMarkerHashMap, markerLoadImage));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    public void displayMaidNearBy(MaidNearByResponse maidNearByResponse) {
        maidInfoList = maidNearByResponse.getData();
        updateMap(googleMap);
    }

    @Override
    public void displayError(String error) {

    }
}
