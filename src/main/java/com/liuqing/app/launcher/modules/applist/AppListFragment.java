package com.liuqing.app.launcher.modules.applist;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuqing.app.launcher.R;
import com.liuqing.app.launcher.modules.applist.adapter.AppListAdapter;
import com.liuqing.app.launcher.modules.applist.bean.ApplicationInformation;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liuqing
 * 17-10-9.
 * Email: 1239604859@qq.com
 */

public class AppListFragment extends Fragment implements SwipeItemClickListener {
    private static final String TAG = "AppListFragment";
    private List<ApplicationInformation> mAppInfos;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_appslist, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workThread.execute(new Runnable() {
            @Override
            public void run() {
                initData(view);
            }
        });
    }

    private void initData(View view) {
        mAppInfos = loadApps();
        rootView = view;
        uiHandler.sendEmptyMessage(MSG_INIT_DATA);
    }

    private void initView() {
        if (rootView != null) {
            SwipeMenuRecyclerView recyclerView = (SwipeMenuRecyclerView) rootView.findViewById(
                    R.id.apps_list);
            recyclerView.setSwipeItemClickListener(this);
            recyclerView.addItemDecoration(new DefaultItemDecoration(Color.WHITE));
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new AppListAdapter(getActivity(), mAppInfos,
                                                       R.layout.item_applist));
        }
    }

    private List<ApplicationInformation> loadApps() {
        List<ApplicationInformation> appInfos = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = getActivity().getPackageManager()
                                              .queryIntentActivities(intent, 0);
        // for循环遍历ResolveInfo对象获取包名和类名
        for (int i = 0; i < apps.size(); i++) {
            try {
                ResolveInfo info = apps.get(i);
                String packageName = info.activityInfo.packageName;
                String cls = info.activityInfo.name;
                String name = info.activityInfo.loadLabel(getActivity().getPackageManager())
                                               .toString();
                Drawable icon1 = info.loadIcon(getActivity().getPackageManager());
                Drawable icon2 = info.activityInfo.loadIcon(getActivity().getPackageManager());
                String versionCode = "";
                try {
                    versionCode = getActivity().getPackageManager()
                                               .getPackageInfo(packageName, 0).versionCode + "";
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, name + "\n" + packageName + "\n" + cls + "\n");

                Intent in = new Intent();
                in.addCategory(Intent.CATEGORY_LAUNCHER);
                in.setComponent(new ComponentName(packageName, cls));
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                ApplicationInformation appInfo = new ApplicationInformation(name, icon2, in,
                                                                            packageName,
                                                                            versionCode);
                appInfos.add(appInfo);
            } catch (Exception e) {
                Log.e(TAG, "loadApps: ", e);
            }
        }
        return appInfos;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        try {
            Log.d(TAG, "onItemClick: " + mAppInfos.get(position));
            startActivity(mAppInfos.get(position)
                                   .getIntent());
        } catch (Exception e) {
            Log.e(TAG, "onItemClick: ", e);
        }
    }

    private static final int MSG_INIT_DATA = 101;

    private Handler uiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_INIT_DATA:
                    initView();
                    break;
            }
        }
    };

    private ExecutorService workThread = Executors.newSingleThreadExecutor();
}
