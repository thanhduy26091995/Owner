package com.hbbsolution.owner.maid_near_by.model;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by buivu on 18/05/2017.
 */

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {
    private Activity mActivity;
    private HashMap<Marker, MyMarker> mMarkerHashMap = new HashMap<>();
    private HashMap<String, Boolean> mMarkerLoadImage = new HashMap<>();
    private OnInfoWindowElemTouchListener chooseMaidListener;

    public MarkerInfoWindowAdapter(Activity mActivity, HashMap<Marker, MyMarker> mMarkerHashMap, HashMap<String, Boolean> mMarkerLoadImage) {
        this.mActivity = mActivity;
        this.mMarkerHashMap = mMarkerHashMap;
        this.mMarkerLoadImage = mMarkerLoadImage;
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
        final MyMarker mMyMarker = mMarkerHashMap.get(marker);
        boolean isLoadImage = mMarkerLoadImage.get(marker.getId());


        if (isLoadImage) {
            Picasso.with(mActivity)
                    .load(mMyMarker.getImageUrl().toString())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(imgAvatar);
        } else {
            isLoadImage = true;
            mMarkerLoadImage.put(marker.getId(), isLoadImage);
            Picasso.with(mActivity)
                    .load(mMyMarker.getImageUrl().toString())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(imgAvatar, new InfoWindowRefresh(marker));
        }
        //load infor
        txtName.setText(mMyMarker.getName());
        txtPrice.setText(mMyMarker.getPrice());

        return view;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        MyMarker myMarker = mMarkerHashMap.get(marker);
        Toast.makeText(mActivity, "InfoMaid window clicked",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mActivity, MaidProfileActivity.class);
        mActivity.startActivity(intent);
    }
}
