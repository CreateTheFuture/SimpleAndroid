package com.simple.library.retrofit.download;

import android.support.annotation.NonNull;

import java.io.IOException;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * package：    com.simple.library.retrofit.download
 * author：     XuShuai
 * date：       2017/9/20  11:20
 * version:     v1.0
 * describe：   文件下载进度监听
 */
public class ProgressResponseBody extends ResponseBody {

    private DownloadCallback callback;
    private ResponseBody responseBody;
    private BufferedSource bufferedSource;


    public ProgressResponseBody(ResponseBody responseBody, DownloadCallback callback) {
        this.callback = callback;
        this.responseBody = responseBody;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (null == bufferedSource) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += (bytesRead != -1 ? bytesRead : 0);
                if (null != callback) {
                    callback.progress(totalBytesRead, contentLength());
                }
                return bytesRead;
            }
        };
    }
}
