package com.zmm.okhttp3demo.utils;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/1/22
 * Time:下午5:58
 */

public class RequestParam {

    private String key;

    private Object value;

    public RequestParam(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
