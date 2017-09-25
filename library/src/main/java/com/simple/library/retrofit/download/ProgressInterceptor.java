package com.simple.library.retrofit.download;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * package：    com.simple.library.retrofit.download
 * author：     XuShuai
 * date：       2017/9/20  13:18
 * version:     v1.0
 * describe：   进度监听拦截器
 */
public class ProgressInterceptor implements Interceptor {
    private DownloadCallback callback;

    public ProgressInterceptor(DownloadCallback callback) {
        this.callback = callback;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), callback))
                .build();
    }
}
