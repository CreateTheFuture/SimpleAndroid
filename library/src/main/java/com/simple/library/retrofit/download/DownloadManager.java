package com.simple.library.retrofit.download;

import com.simple.library.Base;
import com.simple.library.BuildConfig;
import com.simple.library.R;
import com.simple.library.retrofit.BaseServer;
import com.simple.library.retrofit.OkHttpFactory;
import com.simple.library.utils.LogUtils;
import com.simple.library.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * package：    com.simple.library.retrofit.entity
 * author：     XuShuai
 * date：       2017/9/20  10:54
 * version:     v1.0
 * describe：   简单下载文件管理器
 */
public class DownloadManager {

    private static DownloadManager mManager;
    private static Retrofit mRetrofit;

    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
        if (mManager == null) {
            synchronized (DownloadManager.class) {
                if (mManager == null) {
                    mManager = new DownloadManager();
                }
            }
        }
        return mManager;
    }


    /**
     * Download 下载文件
     *
     * @param url        the url 下载地址
     * @param fileParent the file parent 文件保存目录
     * @param callback   the callback 下载回调接口
     */
    public void download(String url, final String fileParent, final DownloadCallback callback) {
        String fileName = getFileName(url);
        File parentFile = new File(fileParent);
        if (parentFile.isFile()) {
            parentFile = parentFile.getParentFile();
        }
        if (!parentFile.exists()) {
            if (!parentFile.mkdirs()) {
                LogUtils.e(Utils.getContext().getString(R.string.library_file_error));
                return;
            }
        }
        final File file = new File(parentFile.getAbsoluteFile() + File.separator + fileName);

        OkHttpClient.Builder okHttpClientBuild = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(OkHttpFactory.HTTP_CONNECT_OUT_TIME, TimeUnit.SECONDS)
                .readTimeout(OkHttpFactory.HTTP_READ_OUT_TIME, TimeUnit.SECONDS)
                .writeTimeout(OkHttpFactory.HTTP_WRITE_OUT_TIME, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            okHttpClientBuild.addInterceptor(OkHttpFactory.getHttpLoggingInterceptor());
        }
        okHttpClientBuild.addNetworkInterceptor(new ProgressInterceptor(callback));
        OkHttpClient okHttpClient = okHttpClientBuild.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        BaseServer server = retrofit.create(BaseServer.class);
        server.download(url)
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        return writeResponseBody2File(responseBody, file, callback);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onStart();
                    }

                    @Override
                    public void onNext(String filePath) {
                        callback.onCompleted(filePath);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 讲responseBody写入文件
     *
     * @param responseBody 下载返回body
     * @param file         下载文件
     * @param callback     下载回调
     */
    private String writeResponseBody2File(ResponseBody responseBody, File file, DownloadCallback callback) {
        try {
            InputStream is = responseBody.byteStream();
            FileOutputStream fos = new FileOutputStream(file);
            int byteCount;
            byte[] buffer = new byte[2048];
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            return file.getAbsolutePath();
        } catch (IOException e) {
            LogUtils.e(e);
            callback.onError(e);
        }
        return null;
    }

    /**
     * 从下载地址获取文件名
     *
     * @param url 下载地址
     * @return 文件名
     */
    private static String getFileName(String url) {
        int separatorIndex = url.lastIndexOf("/");
        return (separatorIndex < 0) ? url : url.substring(separatorIndex + 1, url.length());
    }
}
