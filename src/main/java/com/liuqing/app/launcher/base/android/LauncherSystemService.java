package com.liuqing.app.launcher.base.android;

import android.content.Context;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.android.server.SystemService;
import com.liuqing.app.launcher.ILauncherInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by liuqing
 * 18-1-27.
 * Email: 1239604859@qq.com
 */

public class LauncherSystemService extends SystemService {
    private static final String TAG = "LauncherSystemService";
    private static final String SERVICE_NAME = "app_launcher_system_service";
    private LSSBinder mLSSBinder;

    public LauncherSystemService(Context context) {
        super(context);
        onStart();
    }

    @Override
    public void onStart() {
        if (ServiceManager.checkService(SERVICE_NAME) == null) {
            this.publishBinderService(SERVICE_NAME, getLSSBinder());
        }
    }

    public LSSBinder getBinderService() {
        try {
            return (LSSBinder) this.getBinderService(SERVICE_NAME);
        } catch (Exception e) {
            Log.e(TAG, "getService: ", e);
            return null;
        }
    }

    private LSSBinder getLSSBinder() {
        if (mLSSBinder == null) {
            mLSSBinder = new LSSBinder(getContext());
        }
        return mLSSBinder;
    }

    public static class LSSBinder extends ILauncherInterface.Stub {
        private Context mContext;

        LSSBinder(Context context) {
            mContext = context;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                               double aDouble,
                               String aString) throws RemoteException {
        }

        @Override
        public String makeText(long time) throws RemoteException {
            Log.w(TAG, "packageName: " + mContext.getPackageName());
            Log.w(TAG, "myUid: " + Process.myUid());
            Log.w(TAG, "myPid: " + Process.myPid());
            Log.w(TAG, "myUserHandle: " + Process.myUserHandle());
            //<td><code>"yyyy-MM-dd'T'HH:mm:ss.SSSXXX"</code>
            //<td><code>2001-07-04T12:08:56.235-07:00</code>
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                                               Locale.getDefault());
            String format = dateFormat.format(new Date(time));
            return "根据您的时间格式化: " + format;
        }
    }
}
