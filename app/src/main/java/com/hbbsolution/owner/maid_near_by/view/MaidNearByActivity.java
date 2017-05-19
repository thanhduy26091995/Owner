package com.hbbsolution.owner.maid_near_by.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.maid_near_by.model.InfoWindowRefresh;
import com.hbbsolution.owner.maid_near_by.model.MapWrapperLayout;
import com.hbbsolution.owner.maid_near_by.model.MyMarker;
import com.hbbsolution.owner.maid_near_by.model.OnInfoWindowElemTouchListener;
import com.hbbsolution.owner.maid_near_by.presenter.MaidNearByPresenter;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.model.MaidInfo;
import com.hbbsolution.owner.model.MaidNearByResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buivu on 18/05/2017.
 */

public class MaidNearByActivity extends AppCompatActivity implements MaidNearByView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.text_title)
    TextView mTextTitle;

    private TextView mTextName, mTextPrice;
    private ImageView mImageAvatar;

    private GoogleMap googleMap;
    private List<MaidInfo> maidInfoList = new ArrayList<>();
    private HashMap<Marker, MyMarker> myMarkerHashMap = new HashMap<>();
    private HashMap<String, Boolean> markerLoadImage = new HashMap<>();
    private ArrayList<MyMarker> myMarkers = new ArrayList<>();

    private MaidNearByPresenter presenter;
    private MapWrapperLayout mapWrapperLayout;
    private ViewGroup infoWindow;
    private RelativeLayout relaChooseMaid;
    private OnInfoWindowElemTouchListener infoRelaListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_near_by);
        ButterKnife.bind(this);
        //init
        mapWrapperLayout = (MapWrapperLayout) findViewById(R.id.map_linear_layout);
        final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_map);
        googleMap = mapFragment.getMap();
        mapWrapperLayout.init(googleMap);
        this.infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_marker_layout, null);
        mTextName = (TextView) infoWindow.findViewById(R.id.text_name);
        mTextPrice = (TextView) infoWindow.findViewById(R.id.text_price);
        mImageAvatar = (ImageView) infoWindow.findViewById(R.id.image_avatar);
        presenter = new MaidNearByPresenter(this);
        //init toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        mTextTitle.setText("Người giúp việc quanh đây");
        //get data
        loadData();
        relaChooseMaid = (RelativeLayout) infoWindow.findViewById(R.id.rela_choose_maid);
        relaChooseMaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        infoRelaListener = new OnInfoWindowElemTouchListener(infoWindow) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                MyMarker myMarker = myMarkerHashMap.get(marker);
                Intent intent = new Intent(MaidNearByActivity.this, MaidProfileActivity.class);
                startActivity(intent);
            }
        };
        this.relaChooseMaid.setOnTouchListener(infoRelaListener);

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                MyMarker myMarker = myMarkerHashMap.get(marker);
                //kiểm tra, nếu ảnh chưa load kịp thì refresh lại InfoWindow
                boolean isLoadImage = markerLoadImage.get(marker.getId());
                if (isLoadImage) {
                    Picasso.with(MaidNearByActivity.this)
                            .load(myMarker.getImageUrl().toString())
                            .placeholder(R.drawable.avatar)
                            .error(R.drawable.avatar)
                            .into(mImageAvatar);
                } else {
                    isLoadImage = true;
                    markerLoadImage.put(marker.getId(), isLoadImage);
                    Picasso.with(MaidNearByActivity.this)
                            .load(myMarker.getImageUrl().toString())
                            .placeholder(R.drawable.avatar)
                            .error(R.drawable.avatar)
                            .into(mImageAvatar, new InfoWindowRefresh(marker));
                }
                mTextName.setText(myMarker.getName());
                mTextPrice.setText(myMarker.getPrice());
                // Setting up the infoWindow with current's marker info
                infoRelaListener.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });
    }

    private void loadData() {
        presenter.getMaidNearBy("0eb910010d0252eb04296d7dc32e657b402290755a85367e8b7a806c7e8bd14b0902e541763a67ef41f2dfb3b9b4919869b609e34dbf6bace4525fa6731d1046", 10.767200, 106.687738);
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
            String imageUrl = "https://scontent.fsgn5-1.fna.fbcdn.net/v/t31.0-8/s960x960/16487674_1305034889554168_9144538363083515071_o.jpg?oh=a132e1e85f8b95c54673ae603d3f5b31&oe=59799F04";
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
    public void displayMaidNearBy(MaidNearByResponse maidNearByResponse) {
        maidInfoList = maidNearByResponse.getData();
        updateMap(googleMap);
    }

    @Override
    public void displayError(String error) {

    }
}
