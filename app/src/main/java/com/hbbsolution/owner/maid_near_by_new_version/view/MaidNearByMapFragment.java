package com.hbbsolution.owner.maid_near_by_new_version.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.maid_near_by.model.MarkerInfoWindowAdapter;
import com.hbbsolution.owner.model.Maid;

import java.util.HashMap;
import java.util.List;

import static com.hbbsolution.owner.maid_near_by_new_version.view.MaidNearByListFragment.maidNearByListFragment;

/**
 * Created by buivu on 08/09/2017.
 */

public class MaidNearByMapFragment extends Fragment implements OnMapReadyCallback {

    private View rootView;
    private GoogleMap googleMap;
    private MapView mMapView;
    private HashMap<Marker, Maid> myMarkerHashMap = new HashMap<>();
    private HashMap<String, Boolean> markerLoadImage = new HashMap<>();

    public static MaidNearByMapFragment maidNearByMapFragment = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maid_near_by_map, container, false);
        maidNearByMapFragment = this;
        return rootView;
    }

    public static MaidNearByMapFragment getInstance() {
        return maidNearByMapFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {
            mMapView = (MapView) rootView.findViewById(R.id.map);
            if (mMapView != null) {
                mMapView.onCreate(null);
                mMapView.onResume();
                mMapView.getMapAsync(this);
            }
        } catch (Exception e) {

        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
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

        //updateMap(googleMap, ListJobFragment.getInstance().getCurrentTaskData());
    }

    public void updateDataMap(List<Maid> maids) {
        updateMap(googleMap, maids);
    }

    private void updateMap(GoogleMap googleMap, List<Maid> maidInfoList) {
        googleMap.clear();
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
            googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(getActivity(), myMarkerHashMap, markerLoadImage, googleMap));
        }
    }

}
