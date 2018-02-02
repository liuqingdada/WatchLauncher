package com.liuqing.app.launcher.modules.applist.bean;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by suhen
 * 17-5-12.
 * Email: 1239604859@qq.com
 */

public class ApplicationInformation {
    private String appLabel;    //应用程序标签
    private Drawable appIcon;  //应用程序图像
    private Intent intent;     //启动应用程序的Intent ，一般是Action为Main和Category为Lancher的Activity
    private String pkgName;    //应用程序所对应的包名
    private String version;    //版本号

    public ApplicationInformation() {}

    public ApplicationInformation(String appLabel, Drawable appIcon, Intent intent, String pkgName,
                                  String vertion) {
        this.appLabel = appLabel;
        this.appIcon = appIcon;
        this.intent = intent;
        this.pkgName = pkgName;
        this.version = vertion;
    }

    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appName) {
        this.appLabel = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ApplicationInformation{" +
                "appLabel='" + appLabel + '\'' +
                ", appIcon=" + appIcon +
                ", intent=" + intent +
                ", pkgName='" + pkgName + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
