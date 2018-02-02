package com.liuqing.app.launcher.base.mvp.presenter;

import android.content.Context;

import com.tz.mvp.framework.base.presenter.impl.MvpBasePresenter;
import com.tz.mvp.framework.base.view.MvpView;

/**
 * Created by liuqing
 * 17-10-21.
 * Email: 1239604859@qq.com
 */

public class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {
    public BasePresenter(Context context) {
        super(context);
    }
}
