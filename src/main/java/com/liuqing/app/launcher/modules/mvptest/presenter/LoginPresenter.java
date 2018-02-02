package com.liuqing.app.launcher.modules.mvptest.presenter;

import android.content.Context;
import android.util.Log;

import com.liuqing.app.launcher.base.mvp.model.BaseModel;
import com.liuqing.app.launcher.base.mvp.view.LoginView;
import com.liuqing.app.launcher.modules.mvptest.bean.AttentionBean;
import com.liuqing.app.launcher.modules.mvptest.model.LoginModle;
import com.tz.mvp.framework.base.presenter.impl.MvpBasePresenter;

/**
 * Created by liuqing
 * 17-10-21.
 * Email: 1239604859@qq.com
 */

public class LoginPresenter extends MvpBasePresenter<LoginView> {
    private static final String TAG = "LoginPresenter";
    private LoginModle mLoginModle;

    public LoginPresenter(Context context) {
        super(context);
        mLoginModle = new LoginModle(context);
    }

    public void login() {
        // show loading
        mLoginModle.login(new BaseModel.RxHttpListener<AttentionBean, Throwable>() {
            @Override
            public void onSuccess(AttentionBean attentionBean) {
                Log.d(TAG, "onSuccess: " + attentionBean);
                // show content
                getView().onLoginSuccess(attentionBean);
            }

            @Override
            public void onError(Throwable throwable) {
                // show error
                getView().onLoginFailure(throwable);
            }
        });
    }

    @Override
    public void dettachView() {
        super.dettachView();
        mLoginModle.clearObserver();
    }
}
