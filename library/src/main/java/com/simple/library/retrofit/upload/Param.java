package com.simple.library.retrofit.upload;

/**
 * package：    com.simple.library.retrofit.upload
 * author：     XuShuai
 * date：       2017/9/20  14:37
 * version:     v1.0
 * describe：   上传参数
 */
public class Param {
    public Param() {
    }

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    String key;
    String value;

    @Override
    public String toString() {
        return "Param{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
