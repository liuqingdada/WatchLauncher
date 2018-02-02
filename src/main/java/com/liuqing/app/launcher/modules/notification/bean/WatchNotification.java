package com.liuqing.app.launcher.modules.notification.bean;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.liuqing.app.launcher.modules.applist.utils.ApplicationUtils;
import com.liuqing.app.launcher.modules.notification.BitmapDrawableUtils;

/**
 * Created by liuqing
 * 17-10-13.
 * Email: 1239604859@qq.com
 */

public class WatchNotification {
    private static final String TAG = "WatchNotification";
    private Long id;
    private int notificationId;
    private int type;
    private String title;
    private String content;
    private String iconAdress;
    private long time;
    /**
     * joint:
     * notificationId_pkg_tag
     */
    private String notificationKey;

    /**
     * also is ComponentPackageName
     */
    private String pkgName;

    private String appName;
    private boolean isRead; // 默认false(未读)
    private boolean canRemove = true; // 默认true(可以删除)

    private Intent mIntent;
    private Drawable iconDrawable;
    private Bitmap iconBitmap;
    private String bitmap64;

    private WatchNotification() {}

    private void initFieldBySbn(Context context, StatusBarNotification sbn) {
        Intent intent = sbn.getNotification().contentIntent.getIntent();
        Bundle bundle = sbn.getNotification().extras;

        int notificationId = sbn.getId();
        String key = sbn.getKey();
        String packageName = sbn.getPackageName();

        String appName = ApplicationUtils.getAppName(context, packageName);
        long postTime = sbn.getPostTime();
        boolean clearable = sbn.isClearable();
        String title = bundle.getString(Notification.EXTRA_TITLE);
        String text = bundle.getString(Notification.EXTRA_TEXT);
        int iconId = bundle.getInt(Notification.EXTRA_SMALL_ICON);

        Log.d(TAG,
              "initFieldBySbn: " + notificationId + "\n" + packageName + "\n" + key + "\n"
                      + postTime + "\n" + clearable + "\n" + intent + "\n" + title + "\n" + text
                      + "\n" + iconId);

        Bitmap iconBitmap;
        Drawable iconDrawable = ContextCompat.getDrawable(context, iconId);
        Log.d(TAG, "initFieldBySbn: " + iconDrawable);
        try {
            iconBitmap = BitmapDrawableUtils.drawableToBitmap(iconDrawable);
        } catch (Exception e) {
            Log.e(TAG, "initFieldBySbn: ", e);
            iconBitmap = null;
        }

        this.setNotificationId(notificationId);
        this.setNotificationKey(key);
        this.setPkgName(packageName);

        this.setAppName(appName);
        this.setTime(postTime);
        this.setCanRemove(clearable);
        this.setTitle(title);
        this.setContent(text);
        this.setIntent(intent);
        this.setIconDrawable(iconDrawable);
        this.setIconBitmap(iconBitmap);
        if (iconBitmap != null) {
            this.setBitmap64(BitmapDrawableUtils.bitmap2String(iconBitmap));
        }
    }

    public static class Builder {
        private Context mContext;
        private WatchNotification mWatchNotification = new WatchNotification();

        public Builder(Context context) {
            mContext = context;
        }

        public WatchNotification build() {
            return mWatchNotification;
        }

        public Builder setSbn(StatusBarNotification sbn) {
            mWatchNotification.initFieldBySbn(mContext, sbn);
            return this;
        }

        public Builder setId(Long id) {
            mWatchNotification.setId(id);
            return this;
        }

        public Builder setNotificationId(int notificationId) {
            mWatchNotification.setNotificationId(notificationId);
            return this;
        }

        public Builder setType(int type) {
            mWatchNotification.setType(type);
            return this;
        }

        public Builder setTitle(String title) {
            mWatchNotification.setTitle(title);
            return this;
        }

        public Builder setContent(String content) {
            mWatchNotification.setContent(content);
            return this;
        }

        public Builder setIconAdress(String iconAdress) {
            mWatchNotification.setIconAdress(iconAdress);
            return this;
        }

        public Builder setTime(long time) {
            mWatchNotification.setTime(time);
            return this;
        }

        public Builder setNotificationKey(String notificationKey) {
            mWatchNotification.setNotificationKey(notificationKey);
            return this;
        }

        public Builder setPkgName(String pkgName) {
            mWatchNotification.setPkgName(pkgName);
            return this;
        }

        public Builder setAppName(String appName) {
            mWatchNotification.setAppName(appName);
            return this;
        }

        public Builder setRead(boolean read) {
            mWatchNotification.setRead(read);
            return this;
        }

        public Builder setCanRemove(boolean canRemove) {
            mWatchNotification.setCanRemove(canRemove);
            return this;
        }

        public Builder setIntent(Intent intent) {
            mWatchNotification.setIntent(intent);
            return this;
        }

        public Builder setIconDrawable(Drawable iconDrawable) {
            mWatchNotification.setIconDrawable(iconDrawable);
            return this;
        }

        public Builder setIconBitmap(Bitmap iconBitmap) {
            mWatchNotification.setIconBitmap(iconBitmap);
            return this;
        }

        public Builder setBitmap64(String bitmap64) {
            mWatchNotification.setBitmap64(bitmap64);
            return this;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIconAdress() {
        return iconAdress;
    }

    public void setIconAdress(String iconAdress) {
        this.iconAdress = iconAdress;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getNotificationKey() {
        return notificationKey;
    }

    public void setNotificationKey(String notificationKey) {
        this.notificationKey = notificationKey;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isCanRemove() {
        return canRemove;
    }

    public void setCanRemove(boolean canRemove) {
        this.canRemove = canRemove;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public void setIntent(Intent intent) {
        mIntent = intent;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public Bitmap getIconBitmap() {
        return iconBitmap;
    }

    public void setIconBitmap(Bitmap iconBitmap) {
        this.iconBitmap = iconBitmap;
    }

    public String getBitmap64() {
        return bitmap64;
    }

    public void setBitmap64(String bitmap64) {
        this.bitmap64 = bitmap64;
    }

    @Override
    public String toString() {
        return "WatchNotification{" +
                "id=" + id +
                ", notificationId=" + notificationId +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", iconAdress='" + iconAdress + '\'' +
                ", time=" + time +
                ", notificationKey='" + notificationKey + '\'' +
                ", pkgName='" + pkgName + '\'' +
                ", appName='" + appName + '\'' +
                ", isRead=" + isRead +
                ", canRemove=" + canRemove +
                ", mIntent=" + mIntent +
                ", iconDrawable=" + iconDrawable +
                ", iconBitmap=" + iconBitmap +
                ", bitmap64='" + bitmap64 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            if (obj instanceof WatchNotification) {
                WatchNotification watchNotification = (WatchNotification) obj;

                return this.getNotificationKey()
                           .equals(watchNotification.getNotificationKey()) &&

                        this.getPkgName()
                            .equals(watchNotification.getPkgName()) &&

                        this.getNotificationId() == watchNotification.getNotificationId() &&

                        this.getTime() == watchNotification.getTime();
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.w(TAG, "equals: ", e);
            return false;
        }
    }
}
