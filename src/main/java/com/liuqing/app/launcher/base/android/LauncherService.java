package com.liuqing.app.launcher.base.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.os.storage.ExternalStorageFormatter;

import java.util.Objects;

/**
 * Created by liuqing
 * 18-1-29.
 * Email: 1239604859@qq.com
 */

public class LauncherService {
    private static final String TAG = "LauncherService";
    private static final String LAUNCHER_SERVICE = "launcher_service";
    private IAndroidSystem mIAndroidSystem;
    private Context mContext;

    public LauncherService(Context context) {
        mContext = context;
        onCreate();
    }

    private IAndroidSystem getIAndroidSystem() {
        if (mIAndroidSystem == null) {
            mIAndroidSystem = new IAndroidSystem(mContext);
        }
        return mIAndroidSystem;
    }

    public void onCreate() {
        if (ServiceManager.checkService(LAUNCHER_SERVICE) == null) {
            ServiceManager.addService(LAUNCHER_SERVICE, getIAndroidSystem());
        }
    }

    public IAndroidSystem getServiceBinder() {
        return (IAndroidSystem) ServiceManager.getService(LAUNCHER_SERVICE);
    }

    public static class IAndroidSystem extends com.liuqing.app.launcher.IAndroidSystem.Stub {
        private Context mContext;
        private BatteryReceiver mBatteryReceiver;

        IAndroidSystem(Context context) {
            mContext = context;
            mBatteryReceiver = new BatteryReceiver();
            IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            mContext.registerReceiver(mBatteryReceiver, filter);
        }

        @Override
        public int getPowerPercent() throws RemoteException {
            return BatteryReceiver.WATCH_CURRENT_POWER_PERCENT;
        }

        @Override
        public void reboot() throws RemoteException {
            // show dialog
            //
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_REBOOT);
            intent.putExtra("nowait", 1);
            intent.putExtra("interval", 1);
            intent.putExtra("window", 0);
            mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT_OR_SELF);
        }

        @Override
        public void shutdown() throws RemoteException {
            // show dialog
            //
            Intent i = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
            i.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
            i.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }

        @Override
        public void restoreFactory(boolean isEraseSdCard) throws RemoteException {
            // show dialog
            //
            if (isEraseSdCard) {
                Intent intent = new Intent(ExternalStorageFormatter.FORMAT_AND_FACTORY_RESET);
                intent.putExtra(Intent.EXTRA_REASON, "MasterClearConfirm");
                intent.setComponent(ExternalStorageFormatter.COMPONENT_NAME);
                mContext.startService(intent);
            } else {
                Intent intent = new Intent(Intent.ACTION_MASTER_CLEAR);
                intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                intent.putExtra(Intent.EXTRA_REASON, "MasterClearConfirm");
                mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT_OR_SELF);
                // Intent handling is asynchronous -- assume it will happen soon.
            }
        }

        @Override
        public boolean getMobileDataState() throws RemoteException {
            TelephonyManager telephonyService = (TelephonyManager) mContext.getSystemService(
                    Context.TELEPHONY_SERVICE);
            return telephonyService.getDataEnabled();
        }

        @Override
        public void setMobileDataState(boolean enable) throws RemoteException {
            TelephonyManager telephonyService = (TelephonyManager) mContext.getSystemService(
                    Context.TELEPHONY_SERVICE);
            telephonyService.setDataEnabled(enable);
        }

        @Override
        public void setSystemTime(long timestamp) throws RemoteException {
            SystemTimeUtils.setSysTime(mContext, timestamp);
        }

        @Override
        public void setTimeZone(String timeZone) throws RemoteException {
            SystemTimeUtils.setTimeZone(mContext, timeZone);
        }

        @Override
        public void setAutoDateTime(boolean auto) throws RemoteException {
            SystemTimeUtils.setAutoDateTime(mContext, auto ? 1 : 0);
        }

        @Override
        public void setAutoTimeZone(boolean auto) throws RemoteException {
            SystemTimeUtils.setAutoTimeZone(mContext, auto ? 1 : 0);
        }

        @Override
        public void setHourFormat(boolean isTime_24) throws RemoteException {
            SystemTimeUtils.setHourFormat(mContext, isTime_24 ? "24" : "12");
        }

        private static class BatteryReceiver extends BroadcastReceiver {
            private static int WATCH_CURRENT_POWER_PERCENT;

            @Override
            public void onReceive(Context context, Intent intent) {
                if (Objects.equals(intent.getAction(), Intent.ACTION_BATTERY_CHANGED)) {
                    Bundle extras = intent.getExtras();
                    assert extras != null;
                    int current = extras.getInt("level"); // 获得当前电量
                    int total = extras.getInt("scale"); // 获得总电量
                    WATCH_CURRENT_POWER_PERCENT = current * 100 / total;

                    Log.d(TAG, "watch current power percent: " + WATCH_CURRENT_POWER_PERCENT);
                }
            }
        }
    }
}
