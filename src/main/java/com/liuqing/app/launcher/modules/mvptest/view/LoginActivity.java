package com.liuqing.app.launcher.modules.mvptest.view;

import android.os.Bundle;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.liuqing.app.launcher.R;
import com.liuqing.app.launcher.base.android.LauncherService;
import com.liuqing.app.launcher.base.android.LauncherSystemService;
import com.liuqing.app.launcher.base.mvp.view.LoginView;
import com.liuqing.app.launcher.modules.launch.App;
import com.liuqing.app.launcher.modules.mvptest.bean.AttentionBean;
import com.liuqing.app.launcher.modules.mvptest.presenter.LoginPresenter;
import com.tz.mvp.framework.support.view.MvpActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings({ "FieldCanBeLocal", "unused" })
public class LoginActivity extends MvpActivity<LoginView, LoginPresenter>
        implements LoginView, View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private PowerManager mPowerManager;
    private LauncherService.IAndroidSystem mIAndroidSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        App app = (App) getApplication();
        init();
        initView();

        // test system service
        LauncherSystemService.LSSBinder binderService = app.getLauncherSystemService()
                                                           .getBinderService();
        try {
            if (binderService != null) {
                Log.w(TAG, "onCreate: " + binderService.makeText(System.currentTimeMillis()));
            } else {
                Log.w(TAG, "onCreate: binderService is null");
            }
        } catch (RemoteException e) {
            Log.e(TAG, "onCreate: ", e);
        }

        try {
            String[] listServices = ServiceManager.listServices();
            for (String listService : listServices) {
                Log.e(TAG, "onCreate: " + listService);
            }

        } catch (RemoteException e) {
            Log.e(TAG, "onCreate: ", e);
        }
    }

    private void init() {
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        //        PowerManager.WakeLock wl = pm.newWakeLock(
        //                PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, TAG);
        //        wl.acquire();
        //        // ... do work...
        //        wl.release();
        App app = (App) getApplication();
        mIAndroidSystem = app.getLauncherService()
                             .getServiceBinder();

        try {
            Log.d(TAG, "PowerPercent: " + mIAndroidSystem.getPowerPercent());
        } catch (RemoteException e) {
            Log.e(TAG, "init: ", e);
        }
    }

    private void initView() {
        findViewById(R.id.system_time_is24).setOnClickListener(this);
        findViewById(R.id.system_timezone_auto).setOnClickListener(this);
        findViewById(R.id.system_time_auto).setOnClickListener(this);
        findViewById(R.id.system_timezone).setOnClickListener(this);
        findViewById(R.id.system_time).setOnClickListener(this);
        findViewById(R.id.mobile_data).setOnClickListener(this);
        //
        findViewById(R.id.factory_restore).setOnClickListener(this);
        findViewById(R.id.reboot).setOnClickListener(this);
        findViewById(R.id.shut_down).setOnClickListener(this);
        findViewById(R.id.bt_login).setOnClickListener(this);
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onLoginSuccess(AttentionBean result) {
        Log.d(TAG, "onLoginSuccess: " + result);
    }

    @Override
    public void onLoginFailure(Throwable error) {
        Log.d(TAG, "onLoginFailure: ", error);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        switch (v.getId()) {
            case R.id.bt_login:
                getPresenter().login();
                break;

            case R.id.shut_down:
                try {
                    mIAndroidSystem.shutdown();
                } catch (RemoteException e) {
                    Log.e(TAG, "onClick: ", e);
                }
                break;

            case R.id.reboot:
                try {
                    mIAndroidSystem.reboot();
                } catch (RemoteException e) {
                    Log.e(TAG, "onClick: ", e);
                }
                break;

            case R.id.factory_restore:
                try {
                    mIAndroidSystem.restoreFactory(true);
                } catch (RemoteException e) {
                    Log.e(TAG, "onClick: ", e);
                }
                break;

            case R.id.mobile_data:
                try {
                    boolean b = !mIAndroidSystem.getMobileDataState();
                    mIAndroidSystem.setMobileDataState(b);
                    Log.d(TAG, button.getText()
                                     .toString() + b);
                } catch (RemoteException e) {
                    Log.e(TAG, "onClick: ", e);
                }
                break;

            case R.id.system_time:
                long halfDayAgo = System.currentTimeMillis() - (1000 * 60 * 60 * 12);
                try {
                    mIAndroidSystem.setAutoDateTime(false);
                    mIAndroidSystem.setSystemTime(halfDayAgo);
                } catch (RemoteException e) {
                    Log.e(TAG, "onClick: ", e);
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                                                   Locale.getDefault());
                String format = dateFormat.format(new Date(halfDayAgo));
                Log.d(TAG, "oneDayAgo: " + format);

                break;

            case R.id.system_timezone:
                if (timezone == null) {
                    timezone = west;
                }

                try {
                    mIAndroidSystem.setAutoTimeZone(false);
                    mIAndroidSystem.setTimeZone(timezone);
                } catch (RemoteException e) {
                    Log.e(TAG, "onClick: ", e);
                }

                if (timezone.equals(west)) {
                    timezone = east;
                } else {
                    timezone = west;
                }
                break;

            case R.id.system_time_auto:
                try {
                    mIAndroidSystem.setAutoDateTime(isAutoTime);
                } catch (RemoteException e) {
                    Log.e(TAG, "onClick: ", e);
                }
                isAutoTime = !isAutoTime;
                break;

            case R.id.system_timezone_auto:
                try {
                    mIAndroidSystem.setAutoTimeZone(isAutoTimezone);
                } catch (RemoteException e) {
                    Log.e(TAG, "onClick: ", e);
                }
                isAutoTimezone = !isAutoTimezone;
                break;

            case R.id.system_time_is24:
                try {
                    mIAndroidSystem.setHourFormat(isAuto24);
                } catch (RemoteException e) {
                    Log.e(TAG, "onClick: ", e);
                }
                isAuto24 = !isAuto24;
                break;
        }
    }

    private String timezone;
    private String west = "GMT-8:00";
    private String east = "GMT+8:00";

    private boolean isAutoTime = true;
    private boolean isAutoTimezone = true;
    private boolean isAuto24 = true;
}
