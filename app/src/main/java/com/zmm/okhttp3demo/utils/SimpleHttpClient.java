package com.zmm.okhttp3demo.utils;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/1/22
 * Time:下午5:44
 */

public class SimpleHttpClient {

    private Builder mBuilder;


    public SimpleHttpClient(Builder builder) {
        mBuilder = builder;
    }

    /**
     * 创建Request
     * @return
     */
    public Request buildRequest(){

        Request.Builder builder = new Request.Builder();

        if(mBuilder.method == "GET"){
            builder.url(buildGetRequestParam());
        }else if(mBuilder.method == "POST"){

            try {
                builder.post(buildRequestBody());
                builder.url(mBuilder.url);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        return builder.build();
    }

    /**
     * 整理请求参数
     * @return
     */
    private String buildGetRequestParam() {
        if (mBuilder.mParams == null) {
            return this.mBuilder.url;
        }

        Uri.Builder builder = Uri.parse(mBuilder.url).buildUpon();

        for (RequestParam  p: mBuilder.mParams) {

            builder.appendQueryParameter(p.getKey(),p.getValue()==null?"":p.getValue().toString());
        }

        String url = builder.build().toString();


        return url;
    }


    /**
     * 创建RequestBody
     * @return
     */
    public RequestBody buildRequestBody() throws JSONException {

        if(mBuilder.isJsonParam){

            JSONObject jsonObject = new JSONObject();

            for (RequestParam p:mBuilder.mParams) {
                jsonObject.put(p.getKey(),p.getValue());
            }

            String json = jsonObject.toString();
            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);

        }

        FormBody.Builder builder = new FormBody.Builder();

        for (RequestParam p : mBuilder.mParams) {
            builder.add(p.getKey(),p.getValue() == null?"":p.getValue().toString());
        }


        return builder.build();
    }


    /**
     * 执行方法enqueue
     * @param callback
     */
    public void enqueue(BaseCallback callback){
        OKHttpManager.getInstance().request(this,callback);
    }




    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{


        private String url;
        private String method;
        private boolean isJsonParam;
        private List<RequestParam> mParams;

        public Builder() {
            method = "GET";
        }

        public SimpleHttpClient build() {
            return new SimpleHttpClient(this);
        }


        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder get(){

            method = "GET";
            return this;
        }


        public Builder post(){

            method = "POST";
            return this;
        }

        public Builder json(){

            isJsonParam = true;
            return this;
        }

        public Builder addParam(String key,String value){

            if(mParams == null){
                mParams = new ArrayList<>();

            }

            mParams.add(new RequestParam(key,value));

            return this;
        }

    }




}
