package com.liuqing.app.launcher.modules.launch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.liuqing.app.launcher.R;
import com.liuqing.app.launcher.customview.viewpager.HorizontalViewPager;
import com.liuqing.app.launcher.modules.alipay.AlipayFragment;
import com.liuqing.app.launcher.modules.applist.AppListFragment;
import com.liuqing.app.launcher.modules.home.HomeFragment;
import com.liuqing.app.launcher.modules.home.HomePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断进入home还是fte
        // 先假设直接进入home
        goHome();
    }

    private void goFte() {

    }

    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////

    private HorizontalViewPager mViewPager;
    private AlipayFragment mAlipayFragment;
    private HomeFragment mHomeFragment;
    private AppListFragment mAppListFragment;
    private List<Fragment> pages = new ArrayList<>();

    private void goHome() {
        //WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        //Drawable wallpaperDrawable = wallpaperManager.getFastDrawable();
        //mViewPager.setBackground(wallpaperDrawable);
        setContentView(R.layout.activity_home);
        mAlipayFragment = new AlipayFragment();
        mHomeFragment = new HomeFragment();
        mAppListFragment = new AppListFragment();
        pages.add(mAlipayFragment);
        pages.add(mHomeFragment);
        pages.add(mAppListFragment);

        mViewPager = (HorizontalViewPager) findViewById(R.id.home_viewpager);
        mViewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), pages));
        mViewPager.setCurrentItem(HomePagerAdapter.HOME_PAGE_INDEX);
        mViewPager.setOffscreenPageLimit(pages.size());
    }

    public void setSwipeEnabled(boolean b) {
        if (mViewPager != null) {
            mViewPager.setSwipeEnabled(b);
        }
    }
}
