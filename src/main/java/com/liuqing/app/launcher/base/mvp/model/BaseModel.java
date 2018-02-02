package com.liuqing.app.launcher.base.mvp.model;

import android.content.Context;

import com.liuqing.app.launcher.http.RetrofitWizard;
import com.tz.mvp.framework.base.model.MvpModel;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by liuqing
 * 17-10-21.
 * Email: 1239604859@qq.com
 */

public class BaseModel implements MvpModel {
    protected enum Type {
        STRING, JSON
    }

    protected Lock mLock = new ReentrantLock();
    protected final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private Context mContext;

    public BaseModel(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public String getServerUrl() {
        return "http://api.budejie.com/api/";
    }

    public <T> T buildService(Class<T> serviceClass, Type type) {
        if (type.equals(Type.JSON)) {
            return RetrofitWizard.jsonRetrofit(mContext, getServerUrl())
                                 .create(serviceClass);
        } else if (type.equals(Type.STRING)) {
            return RetrofitWizard.stringRetrofit(mContext, getServerUrl())
                                 .create(serviceClass);
        } else {
            return null;
        }
    }

    public interface RxHttpListener<S, E> {
        void onSuccess(S s);

        void onError(E e);
    }
}
