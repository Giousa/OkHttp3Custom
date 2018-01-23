package com.zmm.okhttp3demo.utils;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/1/22
 * Time:下午6:02
 */

public abstract class BaseCallback<T> {

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            return null;
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public Type mType;

    public BaseCallback(){
        mType = getSuperclassTypeParameter(this.getClass());
    }

    public abstract void onSuccess(T t);

    public abstract void onError(int code);

    public abstract void onFailure(Call call, IOException e);

}
