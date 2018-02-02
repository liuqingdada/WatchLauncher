// INotificationListenerServiceInterface.aidl
package com.liuqing.app.launcher;
import android.service.notification.StatusBarNotification;

// Declare any non-default types here with import statements

interface INotificationInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNotificationPosted(in StatusBarNotification sbn);

    void onNotificationRemoved(in StatusBarNotification sbn);

    void setINotificationListener(in INotificationInterface inl);
}
