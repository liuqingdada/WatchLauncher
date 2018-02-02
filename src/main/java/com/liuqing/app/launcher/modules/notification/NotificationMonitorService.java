package com.liuqing.app.launcher.modules.notification;

import android.app.INotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.liuqing.app.launcher.modules.notification.bean.WatchNotification;

/**
 * Created by liuqing
 * 17-10-11.
 * Email: 1239604859@qq.com
 */

public class NotificationMonitorService extends NotificationListenerService {
    private static final String TAG = NotificationMonitorService.class.getSimpleName();
    private INotificationManager mINotificationManager;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
        super.onNotificationPosted(sbn, rankingMap);
        Log.v(TAG, "onNotificationPosted: \n" + sbn + "\n" + rankingMap);
        WatchNotification watchNotification = new WatchNotification.Builder(this)
                .setSbn(sbn)
                .build();
        Log.i(TAG, "onNotificationPosted: " + watchNotification);

        if (mNotificationListener != null) {
            mNotificationListener.onNotificationPosted(watchNotification);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap) {
        super.onNotificationRemoved(sbn, rankingMap);
        Log.d(TAG, "onNotificationPosted: \n" + sbn + "\n" + rankingMap);
    }

    ////////////////////////////////////////////////
    ////////////////////////////////////////////////

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
        startService(new Intent(this, NotificationMonitorService.class));
        Log.i(TAG, "onDestroy: ");
    }

    public INotificationManager getINotificationManager() {
        if (mINotificationManager == null) {
            mINotificationManager = INotificationManager.Stub.asInterface(
                    ServiceManager.getService(Context.NOTIFICATION_SERVICE));
        }
        return mINotificationManager;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new NotificationBinder();
    }

    public class NotificationBinder extends Binder {
        public void setNotificationListener(NotificationListener l) {
            mNotificationListener = l;
        }
    }

    private NotificationListener mNotificationListener;

    public interface NotificationListener {
        void onNotificationPosted(WatchNotification watchNotification);

        void onNotificationRemoved();
    }

    private void registerNotification() {
        try {
            this.registerAsSystemService(this,
                                         new ComponentName(this.getPackageName(),
                                                           getClass().getCanonicalName()),
                                         UserHandle.USER_ALL);
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
}
