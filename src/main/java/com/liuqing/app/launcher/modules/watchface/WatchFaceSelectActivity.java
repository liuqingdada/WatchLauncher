package com.liuqing.app.launcher.modules.watchface;

import android.app.WallpaperInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.azoft.carousellayoutmanager.DefaultChildSelectionListener;
import com.liuqing.app.launcher.R;

import java.util.List;

public class WatchFaceSelectActivity extends AppCompatActivity {
    private static final String TAG = WatchFaceSelectActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private WatchFaceManager mWatchFaceManager;
    private List<WallpaperInfo> mWallpaperList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchface_select);

        initData();
        initView();
    }

    private void initData() {
        mWatchFaceManager = WatchFaceManager.getInstance(this);
        mWallpaperList = mWatchFaceManager.getWallpaperList();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.viewpager_selector);
        CarouselLayoutManager clm = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL,
                                                              false);
        clm.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        clm.setMaxVisibleItems(1);

        mRecyclerView.setLayoutManager(clm);
        mRecyclerView.setHasFixedSize(true);
        final WatchFaceSelectAdapter recyclerAdapter = new WatchFaceSelectAdapter(this,
                                                                                  mWallpaperList);
        mRecyclerView.setAdapter(recyclerAdapter);
        watchfaceLocate();
        mRecyclerView.addOnScrollListener(new CenterScrollListener());

        DefaultChildSelectionListener.initCenterItemListener(
                new DefaultChildSelectionListener.OnCenterItemClickListener() {
                    @Override
                    public void onCenterItemClicked(
                            @NonNull final RecyclerView recyclerView,
                            @NonNull final CarouselLayoutManager carouselLayoutManager,
                            @NonNull final View v) {
                        final int position = recyclerView.getChildLayoutPosition(v);
                        WallpaperInfo wallpaperInfo = mWallpaperList.get(position);
                        Log.d(TAG, "onCenterItemClicked: " + wallpaperInfo);
                        mWatchFaceManager.selectWallpaper(wallpaperInfo);
                        finish();
                    }
                }, mRecyclerView, clm);
        clm.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {
            @Override
            public void onCenterItemChanged(int adapterPosition) {
                if (CarouselLayoutManager.INVALID_POSITION != adapterPosition) {
/*
                    final int value = recyclerAdapter.mItems.get(adapterPosition);
                    adapter.mPosition[adapterPosition] = (value % 10) + (value / 10 + 1) * 10;
                    adapter.notifyItemChanged(adapterPosition);
*/
                }
            }
        });
    }

    private void watchfaceLocate() {
        try {
            WallpaperInfo currentWallpaper = mWatchFaceManager.getCurrentWallpaper();
            for (int i = 0; i < mWallpaperList.size(); i++) {
                if (mWallpaperList.get(i)
                                  .getComponent()
                                  .equals(currentWallpaper.getComponent())) {
                    mRecyclerView.scrollToPosition(i);
                }
            }
        }catch (Exception e){
            Log.e(TAG, "watchfaceLocate: ", e);
        }
    }
}
