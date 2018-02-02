package com.liuqing.app.launcher.modules.quicksettings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuqing.app.launcher.R;
import com.liuqing.app.launcher.modules.quicksettings.item.NetworkFragment;
import com.liuqing.app.launcher.modules.quicksettings.item.PowerFragment;
import com.liuqing.app.launcher.modules.quicksettings.item.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

import cn.youngkaaa.yviewpager.YFragmentStatePagerAdapter;
import cn.youngkaaa.yviewpager.YViewPager;

/**
 * Created by liuqing
 * 17-10-10.
 * Email: 1239604859@qq.com
 */

public class QuickSettingsFragment extends Fragment {
    private YViewPager mYViewPager;
    private List<Fragment> pages = new ArrayList<>();
    private PowerFragment mPowerFragment = new PowerFragment();
    private NetworkFragment mNetworkFragment = new NetworkFragment();
    private WeatherFragment mWeatherFragment = new WeatherFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        pages.add(mPowerFragment);
        pages.add(mNetworkFragment);
        pages.add(mWeatherFragment);
        return inflater.inflate(R.layout.fragment_quicksettings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mYViewPager = (YViewPager) view.findViewById(R.id.quicksettings_viewpager);
        mYViewPager.setAdapter(new QuickAdapter(getChildFragmentManager()));
        mYViewPager.setOffscreenPageLimit(pages.size());
    }

    private class QuickAdapter extends YFragmentStatePagerAdapter {

        QuickAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return pages.get(i);
        }

        @Override
        public int getCount() {
            return pages.size();
        }
    }
}
