package com.simple.library.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * package：    com.simple.library.utils
 * author：     XuShuai
 * date：       2017/9/20  10:00
 * version:     v1.0
 * describe：   网络状态工具类
 */
public class NetworkUtils {
    private NetworkUtils() {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    //没有网络连接
    public static final int NETWORK_NONE = 0;
    //wifi连接
    public static final int NETWORK_WIFI = 1;
    //手机网络数据连接类型
    public static final int NETWORK_2G = 2;
    public static final int NETWORK_3G = 3;
    public static final int NETWORK_4G = 4;
    public static final int NETWORK_UNKNOWN = 5;

    /**
     * 判断网络是否连接
     *
     * @return 连接状态
     */
    public static boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager) Utils.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != manager) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi
     *
     * @return true wifi
     */
    public static boolean isWifi() {
        ConnectivityManager manager = (ConnectivityManager) Utils.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager != null && manager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断网络类型
     *
     * @return 网络类型
     */
    public static int getNetworkType() {
        ConnectivityManager manager = (ConnectivityManager) Utils.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == manager) {
            return NETWORK_NONE;
        }
        //获取是否有可用的网络
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return NETWORK_NONE;
        }

        //判断是否是wifi
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (state != null) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORK_WIFI;
                }
            }
        }

        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null != mobileInfo) {
            NetworkInfo.State state = mobileInfo.getState();
            String strSubTypeName = mobileInfo.getSubtypeName();
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetworkInfo.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                        case TelephonyManager.NETWORK_TYPE_GSM:
                            return NETWORK_2G;
                        //如果是3g类型
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                        case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                            return NETWORK_3G;
                        //如果是4g类型
                        case TelephonyManager.NETWORK_TYPE_IWLAN:
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return NETWORK_4G;
                        default:
                            //中国移动 联通 电信 三种3G制式
                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA")
                                    || strSubTypeName.equalsIgnoreCase("WCDMA")
                                    || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                return NETWORK_3G;
                            } else {
                                return NETWORK_UNKNOWN;
                            }
                    }
                }
            }
        }
        return NETWORK_NONE;
    }

    /**
     * 获取网络运营商名称
     * <p>中国移动、如中国联通、中国电信</p>
     *
     * @return 运营商名称
     */
    public static String getNetworkOperatorName() {
        TelephonyManager tm = (TelephonyManager) Utils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : null;
    }

    /**
     * 打开网络设置界面
     *
     * @param activity activity
     */
    public static void openNetworkSetting(Activity activity) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT > 10) {
            //跳转到设置界面
            intent.setAction(Settings.ACTION_SETTINGS);
        } else {
            //现在会跳转到移动网络设置界面
            ComponentName componentName = new ComponentName("com.android.settting",
                    "com.android.setting.WirelessSettings");
            intent.setComponent(componentName);
            intent.setAction("android.intent.action.View");
        }
        activity.startActivity(intent);
    }
}
