package com.simple.android;

import com.simple.library.Base;
import com.simple.library.ui.BaseApplication;

/**
 * package：    com.simple.android
 * author：     XuShuai
 * date：       2017/9/20  17:35
 * version:     v1.0
 * describe：
 */
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Base.init(this, "http://www.baidu.com", BuildConfig.DEBUG);
    }
}
