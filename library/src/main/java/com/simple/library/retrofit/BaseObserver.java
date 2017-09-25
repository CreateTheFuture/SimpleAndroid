package com.simple.library.retrofit;

import android.accounts.NetworkErrorException;


import com.simple.library.retrofit.entity.ResultEntity;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * package：    com.simple.library.retrofit
 * author：     XuShuai
 * date：       2017/9/20  10:46
 * version:     v1.0
 * describe：   初步解析数据
 */
public abstract class BaseObserver<T> implements Observer<ResultEntity<T>> {
    public BaseObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(ResultEntity<T> resultEntity) {
        onRequestEnd();
        if (resultEntity.isSuccess()) {
            onSuccess(resultEntity);
        } else {
            onCodeError(resultEntity);
        }
    }

    @Override
    public void onError(Throwable e) {
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

    protected abstract void onSuccess(ResultEntity<T> resultEntity);

    protected abstract void onCodeError(ResultEntity<T> resultEntity);

    protected abstract void onFailure(Throwable e, boolean isNetworkError);

    protected void onRequestEnd() {

    }
}
