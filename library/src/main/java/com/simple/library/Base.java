package com.simple.library;

import android.content.Context;

import com.simple.library.retrofit.RetrofitManager;
import com.simple.library.utils.Utils;

/**
 * package：    com.simple.library
 * author：     XuShuai
 * date：       2017/9/20  10:40
 * version:     v1.0
 * describe：   library初始化类
 */
public class Base {
    public static String BASE_URL;

    public static void init(Context context, String baseUrl) {
        RetrofitManager.setBaseUrl(baseUrl);
        Utils.init(context.getApplicationContext());
        BASE_URL = baseUrl;
    }

    public static void init(Context context, String baseUrl, boolean debug) {
        RetrofitManager.setBaseUrl(baseUrl);
        Utils.init(context.getApplicationContext(), debug);
        BASE_URL = baseUrl;
    }
}
