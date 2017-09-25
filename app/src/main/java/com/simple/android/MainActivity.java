package com.simple.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.simple.library.retrofit.download.DownloadCallback;
import com.simple.library.retrofit.download.DownloadManager;
import com.simple.library.retrofit.upload.Param;
import com.simple.library.retrofit.upload.UploadManager;
import com.simple.library.ui.BaseActivity;
import com.simple.library.utils.FileDirectoryUtils;
import com.simple.library.utils.FileUtils;
import com.simple.library.utils.LogUtils;

import java.io.File;
import java.text.NumberFormat;

/**
 * package：    com.simple.android
 * author：     XuShuai
 * date：       2017/9/20  17:34
 * version:     v1.0
 * describe：
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });

        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });
    }

    private void upload() {
        String path1 = "/data/hw_init/product/media/Pre-loaded/Pictures/04-04.jpg";
        String path2 = "/storage/emulated/0/DCIM/Camera/IMG_20170715_141727.jpg";
        File file1 = new File(path1);
        File file2 = new File(path2);
        File[] files = new File[]{file1};
        Param param = new Param("file4", "3333333333333333");
        Param[] params = new Param[]{param};
        UploadManager.getInstance().upload2("http://192.168.0.104:8080/test/file/upload", files, params);
    }

    MaterialDialog dialog;

    private void download() {
        String path = "http://192.168.0.104:8080/test/file/spicy_v1.0_20170908204228_Online_release.apk";
        DownloadManager.getInstance().download(path, FileDirectoryUtils.getExternalFilesDir("file").getAbsolutePath()
                , new DownloadCallback() {
                    int i = 0;
                    long currentTime = 0L;

                    @Override
                    public void onStart() {
                        LogUtils.d("开始下载:" + Thread.currentThread().getName());
                        currentTime = System.currentTimeMillis();
                        dialog = new MaterialDialog.Builder(MainActivity.this)
                                .title("下载apk")
                                .content("开始下载")
                                .progress(false, 100, true)
                                .show();
                    }

                    @Override
                    public void progress(long progress, long total) {
                        dialog.setProgressNumberFormat(FileUtils.getDataSize(progress) + "/" + FileUtils.getDataSize
                                (total));
                        dialog.setMaxProgress((int) total);
                        dialog.setProgress((int) progress);
                        if (i < 10) {
                            LogUtils.d(progress * 1.00f / total * 100 + "%:" + Thread.currentThread().getName());
                            i++;
                        }
                    }

                    @Override
                    public void onCompleted(String filePath) {
                        LogUtils.d("下载完成:" + (System.currentTimeMillis() - currentTime) + "      " +
                                Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e);
                    }
                });
    }
}
