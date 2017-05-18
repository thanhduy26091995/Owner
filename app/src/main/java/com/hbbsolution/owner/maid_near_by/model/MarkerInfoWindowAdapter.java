package com.hbbsolution.owner.maid_near_by.model;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.hbbsolution.owner.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by buivu on 18/05/2017.
 */

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {
    private Activity mActivity;
    private HashMap<Marker, MyMarker> mMarkerHashMap = new HashMap<>();
    private HashMap<String, Boolean> mMarkerLoadImage = new HashMap<>();

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
    public View getInfoContents(Marker marker) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.custom_marker_layout, null);

        ImageView imgAvatar = (ImageView) view.findViewById(R.id.image_avatar);
        TextView txtName = (TextView) view.findViewById(R.id.text_name);
        TextView txtPrice = (TextView) view.findViewById(R.id.text_price);
        //load data
        MyMarker mMyMarker = mMarkerHashMap.get(marker);
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
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK", "AVATAR");
            }
        });

        return view;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
    }
}
