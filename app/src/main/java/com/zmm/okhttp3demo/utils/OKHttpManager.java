package com.zmm.okhttp3demo.utils;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/1/23
 * Time:上午9:13
 */

public class OKHttpManager {

    private static  OKHttpManager mInstance;

    private OkHttpClient mOkHttpClient;

    private Handler mHandler;

    private OKHttpManager() {
        initOkHttp();

        mHandler = new Handler(Looper.getMainLooper());
    }


    public static synchronized OKHttpManager getInstance(){

        if(mInstance == null){
            mInstance = new OKHttpManager();
        }

        return mInstance;
    }

    private void initOkHttp() {
        mOkHttpClient = new OkHttpClient()
                .newBuilder()
                .readTimeout(30000, TimeUnit.SECONDS)
                .connectTimeout(30000,TimeUnit.SECONDS)
                .build();

    }

    public void request(SimpleHttpClient client, final BaseCallback callback) {

        if(callback == null){
            throw  new NullPointerException("callback is null");
        }


        mOkHttpClient.newCall(client.buildRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendOnFailureMessage(callback,call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){

                    String result = response.body().string();

                    if(callback.mType == null || callback.mType == String.class){
                        sendOnSuccessMessage(callback,result);
                    }else {

                        sendOnSuccessMessage(callback, JSON.parseObject(result,callback.mType));
                    }

                }else {
                    sendOnErrorMessage(callback,response.code());
                }
            }
        });
    }


    /**
     * 成功
     * @param callback
     * @param obj
     */
    private void sendOnSuccessMessage(final BaseCallback callback, final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(obj);
            }
        });
    }


    /**
     * 错误
     * @param callback
     * @param code
     */
    private void sendOnErrorMessage(final BaseCallback callback, final int code) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(code);
            }
        });
    }


    /**
     * 失败
     * @param callback
     * @param call
     * @param e
     */
    private void sendOnFailureMessage(final BaseCallback callback, final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(call,e);
            }
        });
    }
}
