package com.simple.library.retrofit.upload;

import com.simple.library.Base;
import com.simple.library.BuildConfig;
import com.simple.library.retrofit.BaseServer;
import com.simple.library.retrofit.OkHttpFactory;
import com.simple.library.retrofit.download.DownloadManager;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * package：    com.simple.library.retrofit.upload
 * author：     XuShuai
 * date：       2017/9/20  14:34
 * version:     v1.0
 * describe：   上传文件
 */
public class UploadManager {
    private static UploadManager mManager;

    private UploadManager() {
    }

    public static UploadManager getInstance() {
        if (mManager == null) {
            synchronized (DownloadManager.class) {
                if (mManager == null) {
                    mManager = new UploadManager();
                }
            }
        }
        return mManager;
    }

    /**
     * 使用MultipartBody方式上传文件
     *
     * @param url    上传文件地址
     * @param files  上传文件
     * @param params 附带参数
     */
    public void upload(String url, File[] files, Param[] params) {
        MultipartBody requestBody = createRequestBody(files, params);
        OkHttpClient.Builder okHttpClientBuild = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(OkHttpFactory.HTTP_CONNECT_OUT_TIME, TimeUnit.SECONDS)
                .readTimeout(OkHttpFactory.HTTP_READ_OUT_TIME, TimeUnit.SECONDS)
                .writeTimeout(OkHttpFactory.HTTP_WRITE_OUT_TIME, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            okHttpClientBuild.addInterceptor(OkHttpFactory.getHttpLoggingInterceptor());
        }
        OkHttpClient okHttpClient = okHttpClientBuild.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        BaseServer server = retrofit.create(BaseServer.class);
        server.upload(url, requestBody)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {

                    }
                });


    }

    public void upload2(String url, File[] files, Param[] params) {
        Map<String, RequestBody> maps = new HashMap<>();
        if (null != params) {
            for (Param param : params) {
                RequestBody body = RequestBody.create(null, param.value);
                maps.put(param.key, body);
            }
        }
        if (null != files) {
            for (File file : files) {
                String fileName = file.getName();
                RequestBody body = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //retrofit 使用基本就是把key放在name里面，故在里面加入filename,就可以上传
                maps.put(fileName + "\"; filename=\"" + fileName, body);
            }
        }
        OkHttpClient.Builder okHttpClientBuild = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(OkHttpFactory.HTTP_CONNECT_OUT_TIME, TimeUnit.SECONDS)
                .readTimeout(OkHttpFactory.HTTP_READ_OUT_TIME, TimeUnit.SECONDS)
                .writeTimeout(OkHttpFactory.HTTP_WRITE_OUT_TIME, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            okHttpClientBuild.addInterceptor(OkHttpFactory.getHttpLoggingInterceptor());
        }
        OkHttpClient okHttpClient = okHttpClientBuild.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        BaseServer server = retrofit.create(BaseServer.class);
        server.upload2(url, maps)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {

                    }
                });

    }


    /**
     * 构建MultipartBody
     *
     * @param files  文件
     * @param params 参数
     * @return MultipartBody
     */
    private MultipartBody createRequestBody(File[] files, Param[] params) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //参数
        if (null != params) {
            for (Param param : params) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                        RequestBody.create(null, param.value));
            }
        }
        if (null != files) {
            String[] fileKeys = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                fileKeys[i] = files[i].getName();
            }
            RequestBody fileBody;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + fileKeys[i] + "\"; " +
                        "filename=\"" + fileName + "\""), fileBody);
            }
        }
        return builder.build();
    }


    /**
     * @param path 文件路径
     * @return 文件mime type
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
