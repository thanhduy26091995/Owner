package com.hbbsolution.owner.maid_near_by.model;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.model.Maid;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by buivu on 18/05/2017.
 */

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {
    private Activity mActivity;
    private HashMap<Marker, Maid> mMarkerHashMap = new HashMap<>();
    private HashMap<String, Boolean> mMarkerLoadImage = new HashMap<>();
    private OnInfoWindowElemTouchListener chooseMaidListener;
    private GoogleMap googleMap;
    private SessionManagerUser sessionManagerUser;

    public MarkerInfoWindowAdapter(Activity mActivity, HashMap<Marker, Maid> mMarkerHashMap, HashMap<String, Boolean> mMarkerLoadImage) {
        this.mActivity = mActivity;
        this.mMarkerHashMap = mMarkerHashMap;
        this.mMarkerLoadImage = mMarkerLoadImage;
    }

    public MarkerInfoWindowAdapter(Activity mActivity, HashMap<Marker, Maid> mMarkerHashMap, HashMap<String, Boolean> mMarkerLoadImage, GoogleMap googleMap) {
        this.mActivity = mActivity;
        this.mMarkerHashMap = mMarkerHashMap;
        this.mMarkerLoadImage = mMarkerLoadImage;
        this.googleMap = googleMap;
        googleMap.setOnInfoWindowClickListener(this);
        sessionManagerUser = new SessionManagerUser(mActivity);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        final View view = mActivity.getLayoutInflater().inflate(R.layout.custom_marker_layout, null);

        ImageView imgAvatar = (ImageView) view.findViewById(R.id.image_avatar);
        TextView txtName = (TextView) view.findViewById(R.id.text_name);
        TextView txtPrice = (TextView) view.findViewById(R.id.text_price);
        // RelativeLayout relaChooseMaid = (RelativeLayout) view.findViewById(R.id.linear_maid_profile);
        //load data
        Maid maidInfo = mMarkerHashMap.get(marker);
        //kiểm tra, nếu ảnh chưa load kịp thì refresh lại InfoWindow
        boolean isLoadImage = mMarkerLoadImage.get(marker.getId());
        if (isLoadImage) {
            if (!TextUtils.isEmpty(maidInfo.getInfo().getImage())) {
                Picasso.with(mActivity)
                        .load(maidInfo.getInfo().getImage())
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .into(imgAvatar);
            }

        } else {
            isLoadImage = true;
            mMarkerLoadImage.put(marker.getId(), isLoadImage);
            if (!TextUtils.isEmpty(maidInfo.getInfo().getImage())) {
                Picasso.with(mActivity)
                        .load(maidInfo.getInfo().getImage())
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .into(imgAvatar, new InfoWindowRefresh(marker));
            }
        }
        txtName.setText(maidInfo.getInfo().getUsername());
        txtPrice.setText(String.valueOf(maidInfo.getWorkInfo().getPrice()));
        // Setting up the infoWindow with current's marker info
        return view;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (sessionManagerUser.isLoggedIn()) {
            Maid maidInfo = mMarkerHashMap.get(marker);
            Intent intent = new Intent(mActivity, MaidProfileActivity.class);
            intent.putExtra("maid", maidInfo);
            mActivity.startActivity(intent);
        }
    }
}
