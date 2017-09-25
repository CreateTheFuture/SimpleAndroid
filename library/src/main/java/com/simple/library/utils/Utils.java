package com.simple.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.simple.library.BuildConfig;

/**
 * package：    com.simple.library.utils
 * author：     XuShuai
 * date：       2017/9/20  10:01
 * version:     v1.0
 * describe：   工具类管理类
 */
public class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context, Boolean debug) {
        Utils.context = context.getApplicationContext();
        LogUtils.isDebug(debug);
        CrashHandler.register();
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
        LogUtils.isDebug(BuildConfig.DEBUG);
        CrashHandler.register();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}
