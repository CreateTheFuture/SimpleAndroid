package com.simple.library.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.simple.library.utils.ToastUtils;

/**
 * package：    com.simple.library.ui
 * author：     XuShuai
 * date：       2017/9/20  10:08
 * version:     v1.0
 * describe：   BaseFragment
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(getContentViewId(), container, false);
        initView(view);
        return view;
    }

    protected abstract int getContentViewId();

    protected abstract void initView(View view);

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
