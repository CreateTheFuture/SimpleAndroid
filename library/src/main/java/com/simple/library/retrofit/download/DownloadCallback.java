package com.simple.library.retrofit.download;


/**
 * package：    com.simple.library.retrofit
 * author：     XuShuai
 * date：       2017/9/20  11:01
 * version:     v1.0
 * describe：   下载回调接口
 */
public abstract class DownloadCallback{

    public abstract void onStart();

    public abstract void progress(long progress, long total);

    public abstract void onCompleted(String filePath);

    public abstract void onError(Throwable e);
}
