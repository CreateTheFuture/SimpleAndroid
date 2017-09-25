package com.simple.library.retrofit;

import com.simple.library.utils.StringUtils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * package：    com.simple.library.retrofit
 * author：     XuShuai
 * date：       2017/9/20  10:36
 * version:     v1.0
 * describe：   Retrofit请求管理
 */
public class RetrofitManager {
    private static RetrofitManager mManager;
    private static Retrofit mRetrofit;
    private static String mBaseUrl = "";

    /**
     * 私有构造方法
     */
    private RetrofitManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(OkHttpFactory.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 单利模式创建
     *
     * @return manager
     */
    public static RetrofitManager getInstance() {
        if (mManager == null) {
            synchronized (RetrofitManager.class) {
                if (mManager == null) {
                    if (StringUtils.isEmpty(mBaseUrl)) {
                        throw new UnsupportedOperationException("u can't instantiate me because base url is null");
                    } else {
                        mManager = new RetrofitManager();
                    }
                }
            }
        }
        return mManager;
    }

    public static void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }


    public <T> T createService(Class<T> server) {
        return mRetrofit.create(server);
    }
}
