package com.liuqing.app.launcher.modules.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuqing.app.launcher.modules.launch.LauncherActivity;
import com.liuqing.app.launcher.R;
import com.liuqing.app.launcher.modules.watchface.WatchFaceFragment;
import com.liuqing.app.launcher.customview.viewpager.VerticalViewPager;
import com.liuqing.app.launcher.modules.notification.NotificationFragment;
import com.liuqing.app.launcher.modules.quicksettings.QuickSettingsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuqing
 * 17-10-9.
 * Email: 1239604859@qq.com
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private VerticalViewPager mViewPager;
    private QuickSettingsFragment mQuickSettingsFragment;
    private WatchFaceFragment mWatchFaceFragment;
    private NotificationFragment mNotificationFragment;
    private List<Fragment> pages = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mQuickSettingsFragment = new QuickSettingsFragment();
        mWatchFaceFragment = new WatchFaceFragment();
        mNotificationFragment = new NotificationFragment();
        pages.add(mQuickSettingsFragment);
        pages.add(mWatchFaceFragment);
        pages.add(mNotificationFragment);

        mViewPager = (VerticalViewPager) view.findViewById(R.id.home_vertical_viewpager);
        mViewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager(), pages));
        mViewPager.setOffscreenPageLimit(pages.size());
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                LauncherActivity launcherActivity = (LauncherActivity) getActivity();
                switch (position) {
                    case HomePagerAdapter.QUICKSETTINGS_PAGE_INDEX:
                        launcherActivity.setSwipeEnabled(false);
                        break;

                    case HomePagerAdapter.HOME_PAGE_INDEX:
                        launcherActivity.setSwipeEnabled(true);
                        break;

                    case HomePagerAdapter.NOTIFICATION_PAGE_INDEX:
                        launcherActivity.setSwipeEnabled(false);
                        break;
                }
            }
        });

        mViewPager.setCurrentItem(HomePagerAdapter.HOME_PAGE_INDEX, false);

        //////////////////////////////////
    }
}
