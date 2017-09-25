package com.simple.library.retrofit;

import com.simple.library.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * package：    com.simple.library.retrofit
 * author：     XuShuai
 * date：       2017/9/20  10:34
 * version:     v1.0
 * describe：   okHttpClient创建工厂
 */
public class OkHttpFactory {
    public static final int HTTP_CONNECT_OUT_TIME = 10;
    public static final int HTTP_READ_OUT_TIME = 10;
    public static final int HTTP_WRITE_OUT_TIME = 10;

    private static OkHttpClient mOkHttpClient;
    private static OkHttpClient mDownloadClient;

    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


    /**
     * Gets ok http client.
     *
     * @return the ok http client
     */
    public static OkHttpClient getOkHttpClient() {
        if (null == mOkHttpClient) {
            OkHttpClient.Builder build = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(HTTP_CONNECT_OUT_TIME, TimeUnit.SECONDS)
                    .readTimeout(HTTP_READ_OUT_TIME, TimeUnit.SECONDS)
                    .writeTimeout(HTTP_WRITE_OUT_TIME, TimeUnit.SECONDS);
            if (BuildConfig.DEBUG) {
                build.addInterceptor(getHttpLoggingInterceptor());
            }
            mOkHttpClient = build.build();
            return mOkHttpClient;
        } else {
            return mOkHttpClient;
        }
    }
}
