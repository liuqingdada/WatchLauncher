package com.liuqing.app.launcher.modules.launch;

import android.app.Application;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;

import com.liuqing.app.launcher.base.android.LauncherService;
import com.liuqing.app.launcher.base.android.LauncherSystemService;

import mstarc_os_api.mstarc_os_api_msg;

/**
 * Created by liuqing
 * 17-10-11.
 * Email: 1239604859@qq.com
 */

public class App extends Application {
    private static final String TAG = "App";
    private LauncherSystemService mLauncherSystemService;
    private LauncherService mLauncherService;
    private mstarc_os_api_msg mApiMsg;
    private boolean isMstarcApiConnected;

    @Override
    public void onCreate() {
        super.onCreate();
        Intent startIntent = new Intent();
        startIntent.setAction("com.mstarc.wearablelauncher.started");
        sendBroadcastAsUser(startIntent, UserHandle.CURRENT_OR_SELF);

        mApiMsg = new mstarc_os_api_msg(this) {
            @Override
            public void onServiceConnected() {
                super.onServiceConnected();
                isMstarcApiConnected = true;
                Log.i(TAG, "mstarc_os_api_msg serviceConnected: ");
            }
        };

        // RxTool.init(this);
        // android system server test.
        mLauncherSystemService = new LauncherSystemService(this);

        // common service for this launcher app
        // it can control power ui, and ...
        mLauncherService = new LauncherService(this);
    }

    ////////////// service api /////////////////////////

    public mstarc_os_api_msg getApiMsg() {
        return isMstarcApiConnected ? mApiMsg : null;
    }

    public LauncherSystemService getLauncherSystemService() {
        return mLauncherSystemService;
    }

    public LauncherService getLauncherService() {
        return mLauncherService;
    }
}
