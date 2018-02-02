package com.liuqing.app.launcher.base.mvp.view;

import com.liuqing.app.launcher.modules.mvptest.bean.AttentionBean;
import com.tz.mvp.framework.base.view.MvpView;

/**
 * Created by liuqing
 * 17-10-21.
 * Email: 1239604859@qq.com
 */

public interface LoginView extends MvpView {
    void onLoginSuccess(AttentionBean result);

    void onLoginFailure(Throwable error);
}
