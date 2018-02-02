package com.liuqing.app.launcher.http;

import android.content.Context;

import com.liuqing.app.launcher.http.cookie.CookieManger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by liuqing
 * 2017/2/2.
 * Email: 1239604859@qq.com
 * <p>
 * 普 通 的 网 络 请 求
 */

public class RetrofitWizard {
    private static Lock sLock = new ReentrantLock();
    private static Converter.Factory sGsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory sRxJavaCallAdapterFactory = RxJava2CallAdapterFactory
            .create();
    private static ScalarsConverterFactory sScalarsConverterFactory = ScalarsConverterFactory
            .create();
    private static int TIME_OUT = 15;

    private RetrofitWizard() {}

    public static Retrofit jsonRetrofit(Context context, String baseUrl) {
        if (sLock == null) {
            sLock = new ReentrantLock();
        }

        sLock.lock();

        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieManger(context))
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .build();

            return new Retrofit.Builder().baseUrl(baseUrl)
                                         .client(okHttpClient)
                                         .addCallAdapterFactory(sRxJavaCallAdapterFactory)
                                         .addConverterFactory(sGsonConverterFactory)
                                         .build();
        } finally {
            sLock.unlock();
        }
    }

    public static Retrofit stringRetrofit(Context context, String baseUrl) {
        if (sLock == null) {
            sLock = new ReentrantLock();
        }

        sLock.lock();

        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieManger(context))
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .build();

            return new Retrofit.Builder().baseUrl(baseUrl)
                                         .client(okHttpClient)
                                         .addCallAdapterFactory(sRxJavaCallAdapterFactory)
                                         .addConverterFactory(sScalarsConverterFactory)
                                         .build();
        } finally {
            sLock.unlock();
        }
    }
}
