package com.liuqing.app.launcher.rxbus;

/**
 * Created by liuqing
 * 18-2-2.
 * Email: 1239604859@qq.com
 */

public class NotifiServiceBindBus {
    private boolean isServiceReady;

    public NotifiServiceBindBus(boolean isServiceReady) {
        this.isServiceReady = isServiceReady;
    }

    public boolean isServiceReady() {
        return isServiceReady;
    }

    public void setServiceReady(boolean serviceReady) {
        isServiceReady = serviceReady;
    }

    @Override
    public String toString() {
        return "NotifiServiceBindBus{" +
                "isServiceReady=" + isServiceReady +
                '}';
    }
}
