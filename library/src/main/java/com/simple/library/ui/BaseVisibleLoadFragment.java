package com.simple.library.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * package：    com.simple.library.ui
 * author：     XuShuai
 * date：       2017/9/20  10:08
 * version:     v1.0
 * describe：   解决使用ViewPager和Fragment搭配使用时，Fragment预加载数据问题
 */
public abstract class BaseVisibleLoadFragment extends BaseFragment {
    private boolean isInitView = false;//控件是否已经初始化
    private boolean isFirstLoad = true;//是否第一次加载数据
    private boolean isVisible = false;//当前fragment是否可见

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isInitView = true;
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isVisible || !isFirstLoad || !isInitView) {
            return;
        }
        loadData();
        isFirstLoad = false;
    }

    public abstract void loadData();
}
