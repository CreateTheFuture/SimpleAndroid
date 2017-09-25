package com.simple.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.FileProvider;

import java.io.File;


/**
 * package：    com.simple.library.utils
 * author：     XuShuai
 * date：       2017/9/20  9:57
 * version:     v1.0
 * describe：   app操作类
 */
public class AppUtils {
    private AppUtils() {
        /*cannot be instantiated */
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName() throws PackageManager.NameNotFoundException {
        PackageManager packageManager = Utils.getContext().getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(
                Utils.getContext().getPackageName(), 0);
        int labelRes = packageInfo.applicationInfo.labelRes;
        return Utils.getContext().getResources().getString(labelRes);
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName() throws PackageManager.NameNotFoundException {
        PackageManager packageManager = Utils.getContext().getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(
                Utils.getContext().getPackageName(), 0);
        return packageInfo.versionName;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode() throws PackageManager.NameNotFoundException {
        PackageManager packageManager = Utils.getContext().getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(
                Utils.getContext().getPackageName(), 0);
        return packageInfo.versionCode;
    }


    /**
     * 安装apk,用于更新时下载Apk并安装
     *
     * @param context 上下文
     * @param path    apk文件路径
     */
    public static void installApk(Context context, String path, String authority) {
        File file = new File(path);
        Intent intent = new Intent();
        // 执行动作
        Uri data;
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "com.xxx.fileProvider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(context, authority, file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        // 执行的数据类型
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳转到系统应用设置页面
     *
     * @param activity    activity
     * @param requestCode 请求码
     */
    public static void go2Setting(Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, requestCode);
    }
}
