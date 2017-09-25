package com.simple.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * package：    com.simple.library.utils
 * author：     XuShuai
 * date：       2017/9/25  9:53
 * version:     v1.0
 * describe：   SharePreference工具类
 */
public class SharePreferenceUtils {

    private static final String NAME = "share_data";

    public static void putBoolean(String key, boolean value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean putBooleanWithResult(String key, boolean value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();
    }

    public static boolean putIntWithResult(String key, int value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defaultValue) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static void putFloat(String key, Float value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putFloat(key, value).apply();
    }

    public static boolean putFloatWithResult(String key, Float value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.edit().putFloat(key, value).commit();
    }

    public static float getFloat(String key, Float defaultValue) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    public static void putLong(String key, Long value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).apply();
    }

    public static boolean putLongWithResult(String key, Long value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.edit().putLong(key, value).commit();
    }

    public static Long getLong(String key, Long defaultValue) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    public static void putString(String key, String value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static boolean putStringWithResult(String key, String value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void putStringSet(String key, Set<String> value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putStringSet(key, value).apply();
    }

    public static boolean putStringSetWithResult(String key, Set<String> value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.edit().putStringSet(key, value).commit();
    }

    public static Set<String> getStringSet(String key, Set<String> defaultValue) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getStringSet(key, defaultValue);
    }

    public static void remove(String key) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).apply();
    }

    public static boolean removeWithResult(String key) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.edit().remove(key).commit();
    }

    public static void clear() {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    public static boolean clearWithResult() {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.edit().clear().commit();
    }

    /**
     * sharePreference 不支持存储double,要存储double，可以将double转为Json(可以不失去精度)存储
     *
     * @param key   key
     * @param value 存储目标数据
     */
    public static void putDouble(String key, double value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        JSONObject object = new JSONObject();
        try {
            object.put("double", value);
            sp.edit().putString(key, object.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static boolean putDoubleWithResult(String key, double value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        JSONObject object = new JSONObject();
        try {
            object.put("double", value);
            return sp.edit().putString(key, object.toString()).commit();
        } catch (JSONException e) {
            LogUtils.e(e);
            e.printStackTrace();
        }
        return false;
    }

    public static double getDouble(String key, double defaultValue) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String jsonString = sp.getString(key, "");
        double value;
        if (StringUtils.isEmpty(jsonString)) {
            value = defaultValue;
        } else {
            try {
                JSONObject object = new JSONObject(jsonString);
                value = object.getDouble("double");
            } catch (JSONException e) {
                LogUtils.e(e);
                value = defaultValue;
            }
        }
        return value;
    }

    public static <T> void putObject(String key, T object) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(object);
        putString(key, jsonString);
    }

    public static <T> boolean putObjectWithResult(String key, T object) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(object);
        return putStringWithResult(key, jsonString);
    }

    public static <T> T getObject(String key, Class<T> clazz) {
        String jsonString = getString(key, null);
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, clazz);
        }
    }
}


