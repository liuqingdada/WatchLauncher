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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SystemNotificationService extends NotificationListenerService {
    private static final String TAG = SystemNotificationService.class.getSimpleName();
    private INotificationManager mINotificationManager;
    private WatchNotificationInterface mWatchNotificationBinder;
    private List<INotificationInterface> mINotificationBinders;
    private ExecutorService serviceCallback = Executors.newFixedThreadPool(2);

    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        serviceCallback.execute(new Runnable() {
            @Override
            public void run() {
                for (INotificationInterface notificationInterface : getINotificationBinders()) {
                    try {
                        notificationInterface.onNotificationPosted(sbn);
                    } catch (RemoteException e) {
                        Log.e(TAG, "run onNotificationPosted: ", e);
                    } catch (Exception e) {
                        getINotificationBinders().remove(notificationInterface);
                        Log.w(TAG, "run onNotificationPosted: remove one");
                    }
                }
            }
        });
        Log.d(TAG, "onNotificationPosted: " + sbn.toString());
    }

    @Override
    public void onNotificationRemoved(final StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        serviceCallback.execute(new Runnable() {
            @Override
            public void run() {
                for (INotificationInterface notificationInterface : getINotificationBinders()) {
                    try {
                        notificationInterface.onNotificationRemoved(sbn);
                    } catch (RemoteException e) {
                        Log.e(TAG, "run onNotificationRemoved: ", e);
                    } catch (Exception e) {
                        getINotificationBinders().remove(notificationInterface);
                        Log.w(TAG, "run onNotificationRemoved: remove one");
                    }
                }
            }
        });
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
        getINotificationBinders().clear();
        startServiceAsUser(new Intent(this, SystemNotificationService.class),
                           UserHandle.CURRENT_OR_SELF);
        Log.i(TAG, "onDestroy: ");
    }

    private List<INotificationInterface> getINotificationBinders() {
        if (mINotificationBinders == null) {
            mINotificationBinders = new CopyOnWriteArrayList<>();
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
            getINotificationBinders().add(inl);
            Log.d(TAG, "INotificationListeners size is: " + getINotificationBinders().size());
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
