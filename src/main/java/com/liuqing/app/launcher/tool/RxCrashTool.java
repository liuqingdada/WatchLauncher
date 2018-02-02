package com.liuqing.app.launcher.tool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vondear on 2016/12/21.
 *
 */

public class RxCrashTool implements UncaughtExceptionHandler {

    private volatile static RxCrashTool mInstance;

    private UncaughtExceptionHandler mHandler;
    private boolean mInitialized;
    private String mCrashDirPath;
    private String mVersionName;
    private int mVersionCode;

    private Context mContext;

    private RxCrashTool(Context context) {
        this.mContext = context;
    }

    /**
     * 获取单例
     * <p>在Application中初始化{@code RxCrashTool.getInstance().init(this);}</p>
     *
     * @return 单例
     */
    public static RxCrashTool getInstance(Context context) {
        if (mInstance == null) {
            synchronized (RxCrashTool.class) {
                if (mInstance == null) {
                    mInstance = new RxCrashTool(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public boolean init() {
        if (mInitialized) return true;

        try {
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            String name = mContext.getResources().getString(labelRes);
            mCrashDirPath = RxFileTool.getRootPath() + File.separator + name + File.separator + "crash" + File.separator;
        } catch (Exception e) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                mCrashDirPath = mContext.getExternalCacheDir().getPath() + File.separator + "crash" + File.separator;
            } else {
                mCrashDirPath = mContext.getCacheDir().getPath() + File.separator + "crash" + File.separator;
            }
        }

        try {
            PackageInfo pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            mVersionName = pi.versionName;
            mVersionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        return mInitialized = true;
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable throwable) {
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        final String fullPath = mCrashDirPath + now + ".txt";
        if (!RxFileTool.createOrExistsFile(fullPath)) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(new FileWriter(fullPath, false));
                    pw.write(getCrashHead());
                    throwable.printStackTrace(pw);
                    Throwable cause = throwable.getCause();
                    while (cause != null) {
                        cause.printStackTrace(pw);
                        cause = cause.getCause();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    RxFileTool.closeIO(pw);
                }
            }
        }).start();
        if (mHandler != null) {
            mHandler.uncaughtException(thread, throwable);
        }
    }

    /**
     * 获取崩溃头
     *
     * @return 崩溃头
     */
    private String getCrashHead() {
        return "\n************* Crash Log Head ****************" +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商
                "\nDevice Model       : " + Build.MODEL +// 设备型号
                "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版本
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK版本
                "\nApp VersionName    : " + mVersionName +
                "\nApp VersionCode    : " + mVersionCode +
                "\n************* Crash Log Head ****************\n\n";
    }
}