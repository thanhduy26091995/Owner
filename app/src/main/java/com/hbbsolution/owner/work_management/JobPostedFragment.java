package com.hbbsolution.owner.work_management;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hbbsolution.owner.R;

import butterknife.ButterKnife;

/**
 * Created by tantr on 5/9/2017.
 */

public class JobPostedFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_job_posted, container, false);

        return rootView;
    }

}
