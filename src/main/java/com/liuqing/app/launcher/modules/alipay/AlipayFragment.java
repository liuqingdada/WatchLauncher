package com.liuqing.app.launcher.modules.alipay;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.liuqing.app.launcher.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liuqing
 * 17-10-9.
 * Email: 1239604859@qq.com
 */

public class AlipayFragment extends Fragment {
    private static final String TAG = "AlipayFragment";
    private static final int HOST_ID = View.NO_ID;
    private final static ComponentName sAlipayApp = new ComponentName(
            "com.eg.android.AlipayGphone", "com.alipay.quickcard.BarcodeWidgetProvider");

    private AppWidgetManager mAppWidgetManager;
    private AppWidgetHost mHost;
    private int mAppWidgetId;
    private AppWidgetProviderInfo mAppWidgetProviderInfo;
    private AppWidgetHostView mAppWidgetHostView;
    private LinearLayout.LayoutParams mLp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppWidgetManager = AppWidgetManager.getInstance(getActivity());
        mHost = new AppWidgetHost(getActivity(), HOST_ID);
        mHost.deleteHost();
        mAppWidgetId = mHost.allocateAppWidgetId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alipay, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mHost.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        bindWidget();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mHost.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHost.deleteAppWidgetId(mAppWidgetId);
    }

    private void bindWidget() {
        workThread.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "bindWidget: app widget id = " + mAppWidgetId);

                mAppWidgetManager.bindAppWidgetIdIfAllowed(mAppWidgetId, sAlipayApp);
                mAppWidgetProviderInfo = mAppWidgetManager.getAppWidgetInfo(mAppWidgetId);

                mAppWidgetHostView = mHost.createView(getActivity(), mAppWidgetId,
                                                                       mAppWidgetProviderInfo);
                mLp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                uiHandler.sendEmptyMessage(MSG_ADD_VIEW);
            }
        });
    }

    private void sendUpdateIntent() {
        if (mAppWidgetProviderInfo == null) {
            Log.e(TAG, "sendUpdateIntent: mAppWidgetProviderInfo is null");
            return;
        }
        int[] appWidgetIds = {mAppWidgetId};
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        intent.setComponent(mAppWidgetProviderInfo.provider);
        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        getActivity().sendBroadcastAsUser(intent, UserHandle.CURRENT_OR_SELF);
        Log.d(TAG, "sendUpdateIntent: " + intent);
    }

    private static final int MSG_ADD_VIEW = 100;

    private Handler uiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_ADD_VIEW:
                    // The View generated for this fragment.
                    LinearLayout rootView = (LinearLayout) getView();
                    if (rootView != null) {
                        rootView.removeAllViews();
                    } else {
                        Log.d(TAG, "bindWidget: skip bindWidget for rootView is null");
                        return;
                    }

                    rootView.addView(mAppWidgetHostView, mLp);
                    sendUpdateIntent();
                    break;
            }
        }
    };

    private ExecutorService workThread = Executors.newSingleThreadExecutor();
}
