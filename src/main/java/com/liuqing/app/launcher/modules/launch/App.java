package com.liuqing.app.launcher.modules.launch;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.liuqing.app.launcher.INotificationInterface;
import com.liuqing.app.launcher.base.android.LauncherService;
import com.liuqing.app.launcher.base.android.LauncherSystemService;
import com.liuqing.app.launcher.modules.notification.SystemNotificationService;

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

        performNotificationService();
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

    /////////////// notification api /////////////////

    private SystemNotificationService.WatchNotificationInterface watchNotificationBinder;

    public SystemNotificationService.WatchNotificationInterface getWatchNotificationBinder() {
        return watchNotificationBinder;
    }

    private void performNotificationService() {
        try {
            Intent intent = new Intent(this, SystemNotificationService.class);
            startServiceAsUser(intent, UserHandle.CURRENT_OR_SELF);
            bindServiceAsUser(intent, mNotificationInterfaceConn, Context.BIND_AUTO_CREATE,
                              UserHandle.CURRENT_OR_SELF);
        } catch (Exception e) {
            Log.e(TAG, "performNotificationService: ", e);
        }
    }

    private ServiceConnection mNotificationInterfaceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: INotificationInterfaceConn");
            watchNotificationBinder = (SystemNotificationService.WatchNotificationInterface)
                    INotificationInterface.Stub.asInterface(service);
            try {
                watchNotificationBinder.setINotificationListener(mNotificationInterface);
            } catch (RemoteException e) {
                Log.e(TAG, "onServiceConnected: ", e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.w(TAG, "onServiceDisconnected: INotificationInterfaceConn");
            performNotificationService();
        }
    };

    private INotificationInterface mINI;

    public void setINI(INotificationInterface INI) {
        mINI = INI;
    }

    private INotificationInterface mNotificationInterface = new INotificationInterface.Stub() {
        @Override
        public void onNotificationPosted(StatusBarNotification sbn) throws RemoteException {
            if (mINI != null) {
                mINI.onNotificationPosted(sbn);
            }
        }

        @Override
        public void onNotificationRemoved(StatusBarNotification sbn) throws RemoteException {
            if (mINI != null) {
                mINI.onNotificationRemoved(sbn);
            }
        }

        @Override
        public void setINotificationListener(INotificationInterface inl) throws RemoteException {}
    };
}
