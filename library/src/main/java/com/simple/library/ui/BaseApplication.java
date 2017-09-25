package com.simple.library.ui;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;


/**
 * package：    com.library.base.ui
 * author：     XuShuai
 * date：       2017/9/19  18:05
 * version:     v1.0
 * describe：   base Application,自动
 */
public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
    }


    /**
     * 重写getResources,使APP文字大小不随用户系统字体大小设置而改变
     *
     * @return resources
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        return resources;
    }
}
