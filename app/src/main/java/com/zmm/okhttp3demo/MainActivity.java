package com.zmm.okhttp3demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zmm.okhttp3demo.model.User;
import com.zmm.okhttp3demo.utils.BaseCallback;
import com.zmm.okhttp3demo.utils.SimpleHttpClient;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String url = "http://giousa.com/info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_get, R.id.btn_post, R.id.btn_simple_get, R.id.btn_simple_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getRequest();
                    }
                }).start();
                break;
            case R.id.btn_post:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        postRequest();
                    }
                }).start();
                break;
            case R.id.btn_simple_get:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getSimpleRequest();
                    }
                }).start();
                break;
            case R.id.btn_simple_post:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        postSimpleRequest();
                    }
                }).start();
                break;
        }
    }


    /**
     * GET请求
     */
    private void getRequest() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("---get :: failed---");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("get :: success = " + response.body().string());
            }
        });


    }


    /**
     * POST请求
     */
    private void postRequest() {
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder().add("id", "giousa").build();

        Request request = new Request.Builder().url(url).post(formBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("post :: failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("post :: success " + response.body().string());
            }
        });

    }



    /**
     * 自定义GET请求
     */
    private void getSimpleRequest() {
        SimpleHttpClient
                .newBuilder()
                .url(url)
                .build()
                .enqueue(new BaseCallback<User>() {
                    @Override
                    public void onSuccess(User user) {
                        Toast.makeText(MainActivity.this,"GET:SUCCESS"+user.toString(),Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(int code) {
                        Toast.makeText(MainActivity.this,"GET:ERROR",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(MainActivity.this,"GET:FAILURE",Toast.LENGTH_SHORT).show();

                    }
                });


    }





    /**
     * 自定义POST请求
     */
    private void postSimpleRequest() {
        SimpleHttpClient
                .newBuilder()
                .addParam("id","121")
                .addParam("username","giousa")
                .addParam("password","123456")
                .post()
                .url(url)
                .build()
                .enqueue(new BaseCallback<User>(){

                    @Override
                    public void onSuccess(User user) {
                        Toast.makeText(MainActivity.this,"POST:SUCCESS"+user.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(int code) {
                        Toast.makeText(MainActivity.this,"POST:ERROR",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(MainActivity.this,"POST:FAILURE",Toast.LENGTH_SHORT).show();

                    }
                });
    }




}
