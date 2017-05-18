package com.hbbsolution.owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.model.Helper;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 18/05/2017.
 */

public class HelperListAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "HelperListAdapter";
    private Context context;
    private List<String> listType;
    private HashMap<String, List<Helper>> listHelper;
    private TextView txt_name_helper, txt_salary_helper;
    private ImageView img_avatar_helper;
    private RatingBar ratingBar;

    public HelperListAdapter(Context context, List<String> listType, HashMap<String, List<Helper>> listHelper) {
        this.context = context;
        this.listType = listType;
        this.listHelper = listHelper;
    }

    @Override
    public int getGroupCount() {
        return listType.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHelper.get(listType.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listType.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHelper.get(listType.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(context);
            convertView = li.inflate(R.layout.group_helper, parent, false);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(context);
            convertView = li.inflate(R.layout.item_helper_list, parent, false);
        }

        txt_name_helper = (TextView) convertView.findViewById(R.id.txt_name_helper);
        txt_salary_helper = (TextView) convertView.findViewById(R.id.txt_salary_helper);
        img_avatar_helper = (ImageView) convertView.findViewById(R.id.img_avatar_helper);
        ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

        txt_name_helper.setText((((Helper) getChild(groupPosition, childPosition)).getName()));
        txt_salary_helper.setText((((Helper) getChild(groupPosition, childPosition)).getSalary()));
        ratingBar.setRating((((Helper) getChild(groupPosition, childPosition)).getRating()));
        Picasso.with(context).load((((Helper) getChild(groupPosition, childPosition)).getImg()))
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(img_avatar_helper);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
