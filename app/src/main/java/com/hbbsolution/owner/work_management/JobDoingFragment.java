package com.hbbsolution.owner.work_management;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.work_management.view.DetailJobDoingActivity;

/**
 * Created by tantr on 5/9/2017.
 */

public class JobDoingFragment extends Fragment{
    private View rootView;
    private LinearLayout lo_item_doing;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_job_doing, container, false);

            lo_item_doing = (LinearLayout) rootView.findViewById(R.id.lo_item_doing);

            lo_item_doing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), DetailJobDoingActivity.class);
                    startActivity(intent);
                }
            });

        }else {
            ViewGroup parent = (ViewGroup) container.getParent();
            parent.removeView(rootView);
        }
        return rootView;
    }
}
