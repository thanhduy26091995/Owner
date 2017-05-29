package com.hbbsolution.owner.history.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hbbsolution.owner.R;

/**
 * Created by Administrator on 16/05/2017.
 */

public class HistoryViewPagerFragment extends FragmentStatePagerAdapter {
    private Context context;
    private int page_num;

    public HistoryViewPagerFragment(FragmentManager fm, Context context, int page_num) {
        super(fm);
        this.context = context;
        this.page_num = page_num;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HistoryJobFragment.newInstance();
            case 1:
                return HistoryHelperFragment.newInstance();

            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.completedjob);
            case 1:
                return context.getResources().getString(R.string.completedhelpler);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return page_num;
    }
}
