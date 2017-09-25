package com.simple.library.utils;

import android.os.Environment;

import java.io.File;

/**
 * package：    com.simple.library.utils
 * author：     XuShuai
 * date：       2017/9/20  9:59
 * version:     v1.0
 * describe：   获取应用需要的各种文件路径
 */
public class FileDirectoryUtils {

    private FileDirectoryUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 应用私有存储目录
     * 获取内置存储下的文件目录，可以用来保存不能公开给其他应用的一些敏感数据如用户个人信息
     * 详细路径为：/data/data/应用包名/files/
     * 此路径不需要申请权限
     * 是Android 手机内置存储，如果手机没有获得root权限的话文件浏览器是无法访问的。
     * 这种存储会随之app被删除而被删除。
     *
     * @return 路径文件
     */
    public static File getFilesDir() {
        return Utils.getContext().getFilesDir();
    }

    /**
     * 应用私有存储目录
     * 获取内置存储下的缓存目录，可以用来保存一些缓存文件如图片，当内置存储的空间不足时将系统自动被清除
     * 详细路径为：/data/data/应用包名/cache/
     * 此路径不需要申请权限
     * 是Android 手机内置存储，如果手机没有获得root权限的话文件浏览器是无法访问的。
     * 这种存储会随之app被删除而被删除。
     *
     * @return 路径文件
     */
    public static File getCacheDir() {
        return Utils.getContext().getCacheDir();
    }

    /**
     * 获取应用数据库文件
     * 详细路径： /data/data/应用包名/databases/xxx.db
     *
     * @param databaseName 数据库名称
     * @return 数据库文件
     */
    public static File getDatabasePath(String databaseName) {
        return Utils.getContext().getDatabasePath(databaseName);
    }

    /**
     * 判断外置sd卡能否使用
     *
     * @return true为可用，false为不可用
     */
    public static boolean getExternalStorageState() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡上的文件目录,可以保存一些长时间保存文件
     * 详细路径为：SDCard/Android/data/应用包名/files/type/
     * 存储在sdcard上所以尽量不要存在敏感数据比如用户信息等。
     * 这里的文件也会随着app 被删除而被删除。
     * 文件对应 设置->应用->应用详情里面的“清除数据”选项
     * 该目录需要申请权限：
     * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
     * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
     *
     * @param type 子文件夹
     *             type可以为自定义文件夹名，这时如果APP被uninstall，则文件会被删除。
     *             type也可以为一下值：
     *             {@link Environment#DIRECTORY_MUSIC},
     *             {@link Environment#DIRECTORY_PODCASTS},
     *             {@link Environment#DIRECTORY_RINGTONES},
     *             {@link Environment#DIRECTORY_ALARMS},
     *             {@link Environment#DIRECTORY_NOTIFICATIONS},
     *             {@link Environment#DIRECTORY_PICTURES}, or
     *             {@link Environment#DIRECTORY_MOVIES}.
     * @return 目标文件
     */
    public static File getExternalFilesDir(String type) {
        if (getExternalStorageState()) {
            return Utils.getContext().getExternalFilesDir(type);
        }
        return null;
    }

    /**
     * 获取SD卡上的缓存目录，可以用来保存一些缓存文件如图片
     * 详细路径为：SDCard/Android/data/应用包名/cache/
     * 存储在sdcard上所以尽量不要存在敏感数据比如用户信息等。
     * 这里的文件也会随着app 被删除而被删除。
     * 文件对应 设置->应用->应用详情里面的“清除缓存”选项。
     * 该目录需要申请权限：
     * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
     * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
     *
     * @return 目标文件
     */
    public static File getExternalCacheDir() {
        if (getExternalStorageState()) {
            return Utils.getContext().getExternalCacheDir();
        }
        return null;
    }

    /**
     * 获取SD卡根目录
     * 详细路径为：SDCard/
     * 需要存储一些公共文件,这些文件能够不随着App被删除而被删除，例如我们录制的视频或者下载的音乐等。
     * 这个目录可以被任何app访问，我们在使用的时候是需要申请权限的。
     * 该目录需要申请权限：
     * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
     * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
     *
     * @return 目标文件
     */
    public static File getExternalStorageDirectory() {
        if (getExternalStorageState()) {
            return Environment.getExternalStorageDirectory();
        }
        return null;
    }

    /**
     * 获取SD卡下一些公共目录，如Picture,Movies。Music...
     * 详细目录为：SDCard/type
     * 这些文件不会随APP uninstall而被删除，会被永久(除非手动删除)保存在SD上
     * 这个目录可以被任何app访问，我们在使用的时候是需要申请权限的。
     * 该目录需要申请权限：
     * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
     * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
     *
     * @param type The type of storage directory to return. Should be one of
     *             {@link Environment#DIRECTORY_MUSIC},
     *             {@link Environment#DIRECTORY_PODCASTS},
     *             {@link Environment#DIRECTORY_RINGTONES},
     *             {@link Environment#DIRECTORY_ALARMS},
     *             {@link Environment#DIRECTORY_NOTIFICATIONS},
     *             {@link Environment#DIRECTORY_PICTURES},
     *             {@link Environment#DIRECTORY_MOVIES},
     *             {@link Environment#DIRECTORY_DOWNLOADS},
     *             {@link Environment#DIRECTORY_DCIM},or
     *             {@link Environment#DIRECTORY_DOCUMENTS}.
     *             May not be null.
     * @return 目标文件 可能为空
     */
    public static File getExternalStoragePublicDirectory(String type) {
        if (getExternalStorageState()) {
            return Environment.getExternalStoragePublicDirectory(type);
        }
        return null;
    }
}
