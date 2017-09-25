package com.simple.library.rxbus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * package：    com.simple.library.rxbus
 * author：     XuShuai
 * date：       2017/9/20  10:22
 * version:     v1.0
 * describe：   同eventBus一样的事件总线,使用RxJava实现
 */
public class RxBus {
    private static volatile RxBus instance;
    private Map<Object, CompositeDisposable> mSubscriptionMap;
    private final Subject<Object> mSubject;

    private RxBus() {
        mSubject = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public void post(Object object) {
        if (mSubject.hasObservers()) {
            mSubject.onNext(object);
        }
    }

    /**
     * 过滤只有tclass类型的信息
     *
     * @param tClass 类型
     * @param <T>    泛型
     * @return Flowable
     */
    private <T> Flowable<T> toObservable(Class<T> tClass) {
        return mSubject.toFlowable(BackpressureStrategy.BUFFER).ofType(tClass);
    }

    /**
     * 订阅事件
     *
     * @param object   订阅事件对象，用于当做保存disposable的key
     * @param tClass   消息对象
     * @param consumer 消费者
     */
    public <T> void subscribe(Object object, Class<T> tClass, Consumer<T> consumer) {
        Disposable disposable = toObservable(tClass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
        addSubscription(object, disposable);
    }

    /**
     * 保存订阅后的disposable
     *
     * @param o          订阅事件的对象
     * @param disposable
     */
    private void addSubscription(Object o, Disposable disposable) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(disposable);
        } else {
            //一次性容器,可以持有多个并提供 添加和移除。
            CompositeDisposable disposables = new CompositeDisposable();
            disposables.add(disposable);
            mSubscriptionMap.put(key, disposables);
        }
    }

    /**
     * 取消订阅
     *
     * @param o
     */
    public void unSubscribe(Object o) {
        if (mSubscriptionMap == null) {
            return;
        }
        String key = o.getClass().getName();
        if (!mSubscriptionMap.containsKey(key)) {
            return;
        }
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).dispose();
        }
        mSubscriptionMap.remove(key);
    }
}
