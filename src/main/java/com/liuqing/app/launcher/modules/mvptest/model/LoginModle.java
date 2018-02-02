package com.liuqing.app.launcher.modules.mvptest.model;

import android.content.Context;

import com.liuqing.app.launcher.base.mvp.model.BaseModel;
import com.liuqing.app.launcher.base.mvp.model.ObserverAdapter;
import com.liuqing.app.launcher.modules.mvptest.bean.AttentionBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuqing
 * 17-10-21.
 * Email: 1239604859@qq.com
 */

public class LoginModle extends BaseModel {
    private IAttentionService mIAttentionService;

    public LoginModle(Context context) {
        super(context);
    }

    @Override
    public String getServerUrl() {
        return super.getServerUrl();
    }

    public void login(final RxHttpListener<AttentionBean, Throwable> rxHttpListener) {
        getService().getAttentionList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ObserverAdapter<AttentionBean>(mCompositeDisposable) {
                        @Override
                        public void onNext(@NonNull AttentionBean attentionBean) {
                            rxHttpListener.onSuccess(attentionBean);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            rxHttpListener.onError(e);
                            super.onError(e);
                        }
                    });
    }

    private IAttentionService getService() {
        mLock.lock();
        try {
            if (mIAttentionService == null) {
                mIAttentionService = buildService(IAttentionService.class, Type.JSON);
            }
            return mIAttentionService;
        } finally {
            mLock.unlock();
        }
    }

    public void clearObserver() {
        mCompositeDisposable.clear();
    }
}
