package com.liuqing.app.launcher.base.mvp.view;

import com.liuqing.app.launcher.base.mvp.presenter.BasePresenter;
import com.tz.mvp.framework.support.view.MvpActivity;

/**
 * Created by liuqing
 * 17-10-21.
 * Email: 1239604859@qq.com
 */

public class TestActivity extends MvpActivity<TestView, BasePresenter<TestView>>
        implements TestView {
    @Override
    public void onSuccess() {

    }

    @Override
    public void onfailure() {

    }

    @Override
    public BasePresenter<TestView> createPresenter() {
        return null;
    }
}
