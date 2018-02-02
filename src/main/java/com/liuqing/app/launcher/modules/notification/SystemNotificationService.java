package com.liuqing.app.launcher.modules.notification;

import android.app.INotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.liuqing.app.launcher.INotificationInterface;

public class SystemNotificationService extends NotificationListenerService {
    private static final String TAG = SystemNotificationService.class.getSimpleName();
    private INotificationManager mINotificationManager;
    private WatchNotificationInterface mWatchNotificationBinder;
    private RemoteCallbackList<INotificationInterface> mINotificationBinders;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        final int N = getINotificationBinders().beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                getINotificationBinders().getBroadcastItem(i)
                                         .onNotificationPosted(sbn);
            } catch (Exception e) {
                Log.e(TAG, "onNotificationPosted: ", e);
            }
        }
        Log.d(TAG, "onNotificationPosted: " + sbn.toString());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        final int N = getINotificationBinders().beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                getINotificationBinders().getBroadcastItem(i)
                                         .onNotificationRemoved(sbn);
            } catch (Exception e) {
                Log.e(TAG, "onNotificationRemoved: ", e);
            }
        }
        Log.v(TAG, "onNotificationRemoved: " + sbn.toString());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerNotification();

        Log.i(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNotification();
        getINotificationBinders().kill();
        startServiceAsUser(new Intent(this, SystemNotificationService.class),
                           UserHandle.CURRENT_OR_SELF);
        Log.i(TAG, "onDestroy: ");
    }

    private RemoteCallbackList<INotificationInterface> getINotificationBinders() {
        if (mINotificationBinders == null) {
            mINotificationBinders = new RemoteCallbackList<>();
        }
        return mINotificationBinders;
    }

    public INotificationManager getINotificationManager() {
        if (mINotificationManager == null) {
            mINotificationManager = INotificationManager.Stub.asInterface(
                    ServiceManager.getService(Context.NOTIFICATION_SERVICE));
        }
        return mINotificationManager;
    }

    private void registerNotification() {
        try {
            this.registerAsSystemService(this,
                                         new ComponentName(this.getPackageName(),
                                                           getClass().getCanonicalName()),
                                         UserHandle.USER_CURRENT_OR_SELF);
        } catch (Exception e) {
            Log.e(TAG, "onViewCreated: " + Log.getStackTraceString(e));
        }
    }

    private void unregisterNotification() {
        try {
            this.unregisterAsSystemService();
        } catch (Exception e) {
            Log.e(TAG, "onViewCreated: " + Log.getStackTraceString(e));
        }
    }

    public class WatchNotificationInterface extends INotificationInterface.Stub {
        @Override
        public void onNotificationPosted(StatusBarNotification sbn) throws RemoteException {}

        @Override
        public void onNotificationRemoved(StatusBarNotification sbn) throws RemoteException {}

        @Override
        public void setINotificationListener(INotificationInterface inl) throws RemoteException {
            getINotificationBinders().register(inl);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (mWatchNotificationBinder == null) {
            mWatchNotificationBinder = new WatchNotificationInterface();
        }
        return mWatchNotificationBinder;
    }
}
