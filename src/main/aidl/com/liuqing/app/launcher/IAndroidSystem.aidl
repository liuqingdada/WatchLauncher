// IPowerInterface.aidl
package com.liuqing.app.launcher;

// Declare any non-default types here with import statements

interface IAndroidSystem {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int getPowerPercent(); // 电量

    void reboot(); // 重启

    void shutdown(); // 关机

    void restoreFactory(boolean isEraseSdCard); // 系统重置

    boolean getMobileDataState(); // 数据开关

    void setMobileDataState(boolean enable);

    // ------- 设置系统时间 --------
    void setSystemTime(long timestamp);

    void setTimeZone(String timeZone);

    void setAutoDateTime(boolean auto);

    void setAutoTimeZone(boolean auto);

    void setHourFormat(boolean isTime_24);

    // --------------------
}
