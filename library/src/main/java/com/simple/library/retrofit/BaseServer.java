package com.simple.library.retrofit;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * package：    com.simple.library.retrofit
 * author：     XuShuai
 * date：       2017/9/20  10:44
 * version:     v1.0
 * describe：    基础retrofit请求接口，定义了上传下载的接口
 * <p>
 * retrofit2 上传文件有2种方式
 * 1.使用@Multipart注解方法，并用@Part注解方法参数，类型是List< okhttp3.MultipartBody.Part>
 * Retrofit会判断@Part的参数类型，如果参数类型为okhttp3.MultipartBody.Part,
 * 则Retrofit会把RequestBody封装成MultipartBody，再把Part添加到MultipartBody。
 * <p>
 * 2.不使用@Multipart注解方法，直接使用@Body注解方法参数，类型是okhttp3.MultipartBody
 * Retrofit会判断@Body的参数类型，如果参数类型为okhttp3.RequestBody,
 * 则Retrofit不做包装处理，直接丢给okhttp3处理。而MultipartBody是继承RequestBody，因此Retrofit不会自动包装这个对象
 */
public interface BaseServer {

    /**
     * Download observable.
     *
     * @param url the url
     * @return the observable
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    /**
     * Upload observable.
     *
     * @param url  the url
     * @param body the body
     * @return the observable
     */
    @POST
    Observable<ResponseBody> upload(@Url String url, @Body MultipartBody body);

    @Multipart
    @POST
    Observable<ResponseBody> upload2(@Url String url, @PartMap Map<String,RequestBody> maps);
}
