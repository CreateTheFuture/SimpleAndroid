package com.simple.library.utils;

import android.widget.Toast;

/**
 * package：    com.simple.library.utils
 * author：     XuShuai
 * date：       2017/9/20  10:01
 * version:     v1.0
 * describe：   Toast工具类
 */
public class ToastUtils {


    private ToastUtils() {
        /** cannot be instantiated**/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 短时间显示Toast
     */
    public static void show(CharSequence message) {
        Toast.makeText(Utils.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     */
    public static void show(int resId) {
        Toast.makeText(Utils.getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(CharSequence message) {
        Toast.makeText(Utils.getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(int resId) {
        Toast.makeText(Utils.getContext(), resId, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     */
    public static void show(CharSequence message, int duration) {
        Toast.makeText(Utils.getContext(), message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     */
    public static void show(int resId, int duration) {
        Toast.makeText(Utils.getContext(), resId, duration).show();
    }
}
