package com.liuqing.app.launcher.modules.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.liuqing.app.launcher.modules.watchface.WatchFaceFragment;

import java.util.List;

/**
 * Created by liuqing
 * 17-10-9.
 * Email: 1239604859@qq.com
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "HomePagerAdapter";
    public static final int QUICKSETTINGS_PAGE_INDEX = 0;
    public static final int HOME_PAGE_INDEX = 1;
    public static final int NOTIFICATION_PAGE_INDEX = 2;

    private List<Fragment> pages;

    public HomePagerAdapter(FragmentManager fm, List<Fragment> pages) {
        super(fm);
        this.pages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return pages.get(position);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return new WatchFaceFragment();
        }
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}
