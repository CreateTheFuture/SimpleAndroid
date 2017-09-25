package com.simple.library.retrofit;

import android.accounts.NetworkErrorException;


import com.simple.library.retrofit.entity.ResultEntity;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * package：    com.simple.library.retrofit
 * author：     XuShuai
 * date：       2017/9/20  10:50
 * version:     v1.0
 * describe：   解析数据
 */
public abstract class BaseObserverWithoutObject implements Observer<ResultEntity> {
    public BaseObserverWithoutObject() {
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(@NonNull ResultEntity resultEntity) {
        onRequestEnd();
        if (resultEntity.isSuccess()) {
            onSuccess(resultEntity);
        } else {
            onCodeError(resultEntity);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onRequestEnd();
        if (e instanceof ConnectException
                || e instanceof TimeoutException
                || e instanceof NetworkErrorException
                || e instanceof UnknownHostException) {
            onFailure(e, true);
        } else {
            onFailure(e, false);
        }
    }

    @Override
    public void onComplete() {

    }

    protected void onRequestStart() {
    }

    protected abstract void onSuccess(ResultEntity resultEntity);

    protected abstract void onCodeError(ResultEntity resultEntity);

    protected abstract void onFailure(Throwable e, boolean isNetworkError);

    protected void onRequestEnd() {

    }
}
