package com.simple.library.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.simple.library.utils.ActivityManager;
import com.simple.library.utils.ToastUtils;

/**
 * package：    com.simple.library.ui
 * author：     XuShuai
 * date：       2017/9/20  10:02
 * version:     v1.0
 * describe：   基础BaseActivity,自动加入ActivityManager管理，并封装简易toast输出
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().pushActivity(this);
        setContentView(getContentViewId());
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().popActivity(this);
    }

    protected abstract int getContentViewId();

    protected abstract void initView();

    public void showToast(String msg) {
        ToastUtils.show(msg);
    }

    public void showToast(int resId) {
        ToastUtils.show(resId);
    }

    public void showToastLong(String msg) {
        ToastUtils.showLong(msg);
    }

    public void showToastLong(int resId) {
        ToastUtils.showLong(resId);
    }
}
