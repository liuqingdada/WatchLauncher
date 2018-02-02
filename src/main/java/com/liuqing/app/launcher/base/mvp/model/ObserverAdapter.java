package com.liuqing.app.launcher.base.mvp.model;

import android.util.Log;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by liuqing
 * 17-10-21.
 * Email: 1239604859@qq.com
 */

public abstract class ObserverAdapter<T> extends DisposableObserver<T> {
    private static final String TAG = ObserverAdapter.class.getSimpleName();

    private CompositeDisposable mCompositeDisposable;

    protected ObserverAdapter(CompositeDisposable compositeDisposable) {
        mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void onError(@NonNull Throwable e) {
        boolean remove = mCompositeDisposable.remove(this);
        Log.w(TAG, "onError: ", e);
        Log.w(TAG, "onError: remove subscriber " + remove);
        Log.w(TAG, "onError: observer size = " + mCompositeDisposable.size());
    }

    @Override
    public void onComplete() {
        boolean remove = mCompositeDisposable.remove(this);
        Log.i(TAG, "onComplete: remove subscriber " + remove);
        Log.i(TAG, "onComplete: observer size = " + mCompositeDisposable.size());
    }

    protected void clearThis() {
        boolean remove = mCompositeDisposable.remove(this);
        Log.d(TAG, "clearThis: remove subscriber " + remove);
        Log.d(TAG, "clearThis: observer size = " + mCompositeDisposable.size());
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }
}
