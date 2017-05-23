package com.hbbsolution.owner.maid_near_by.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
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
    private HashMap<Marker, MaidInfo> myMarkerHashMap = new HashMap<>();
    private HashMap<String, Boolean> markerLoadImage = new HashMap<>();
    private ArrayList<MyMarker> myMarkers = new ArrayList<>();
    private ArrayList<MaidInfo> maidList = new ArrayList<>();

    private MaidNearByPresenter presenter;
    private MapWrapperLayout mapWrapperLayout;
    private ViewGroup infoWindow;
    private LinearLayout linearMaidProfile;
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
        linearMaidProfile = (LinearLayout) infoWindow.findViewById(R.id.linear_maid_profile);
        linearMaidProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        infoRelaListener = new OnInfoWindowElemTouchListener(infoWindow) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                MaidInfo maidInfo = myMarkerHashMap.get(marker);
                Intent intent = new Intent(MaidNearByActivity.this, MaidProfileActivity.class);
                intent.putExtra("maid", maidInfo);
                startActivity(intent);
            }
        };
        this.linearMaidProfile.setOnTouchListener(infoRelaListener);

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                //  MyMarker myMarker = myMarkerHashMap.get(marker);
                MaidInfo maidInfo = myMarkerHashMap.get(marker);
                //kiểm tra, nếu ảnh chưa load kịp thì refresh lại InfoWindow
                boolean isLoadImage = markerLoadImage.get(marker.getId());
                if (isLoadImage) {
                    Picasso.with(MaidNearByActivity.this)
                            .load(maidInfo.getInfo().getImage().toString())
                            .placeholder(R.drawable.avatar)
                            .error(R.drawable.avatar)
                            .into(mImageAvatar);
                } else {
                    isLoadImage = true;
                    markerLoadImage.put(marker.getId(), isLoadImage);
                    Picasso.with(MaidNearByActivity.this)
                            .load(maidInfo.getInfo().getImage().toString())
                            .placeholder(R.drawable.avatar)
                            .error(R.drawable.avatar)
                            .into(mImageAvatar, new InfoWindowRefresh(marker));
                }
                mTextName.setText(maidInfo.getInfo().getUsername());
               // mTextPrice.setText(maidInfo.getWorkInfo().getPrice());
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
            double lat = maidInfo.getInfo().getAddress().getCoordinates().getLat();
            double lng = maidInfo.getInfo().getAddress().getCoordinates().getLng();
            MarkerOptions markerOptions = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_maid)).position(new LatLng(lat, lng));
            Marker currentMarker = googleMap.addMarker(markerOptions);
            myMarkerHashMap.put(currentMarker, maidInfo);
            markerLoadImage.put(currentMarker.getId(), false);
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
            startActivityForResult(intent, 100);
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
