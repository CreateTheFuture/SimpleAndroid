package com.simple.library.retrofit.entity;

/**
 * package：    com.simple.library.retrofit.entity
 * author：     XuShuai
 * date：       2017/9/20  10:50
 * version:     v1.0
 * describe：   网络请求返回结果实体对象
 */
public class ResultEntity<T> {


    private int code;
    private String result;
    private T data;

    public boolean isSuccess() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "code=" + code +
                ", result='" + result + '\'' +
                ", data=" + data +
                '}';
    }
}
