package com.example.king.pcweek001.net;

import android.os.Handler;

import com.example.king.pcweek001.contract.ReqContract;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {

    /**
     * 单例模式
     */
    private static OkHttpUtil instance;
    private final OkHttpClient okHttpClient;
    private final Handler handler;

    private OkHttpUtil() {
        handler = new Handler();

        //设置拦截器


        okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(5,TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpUtil getInstance(){
        if (instance == null){
            synchronized (OkHttpUtil.class){
                if (instance == null){
                    instance = new OkHttpUtil();
                }
            }
        }
        return instance;
    }

    /**
     * POST
     * @param params
     * @param api
     * @param
     */
    public void toPost(Map<String,String> params, String api, final ReqContract.OKHttpCallBack okHttpCallBack){
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, String> p : params.entrySet()) {
            formBody.add(p.getKey(),p.getValue());
        }

        //设置方式
        Request request = new Request.Builder()
                .post(formBody.build())
                .url(api)
                .build();

        //设置Call
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallBack.onFilure("网络错误");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                if (okHttpCallBack != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            okHttpCallBack.onSuccess(res);
                        }
                    });
                }
            }
        });

    }

    /**
     * 取消所有请求，好处：节省开销：内存开销，cpu的开销（线程开销）
     */
    public void cancelAllTask() {
        if (okHttpClient != null) {
            okHttpClient.dispatcher().cancelAll();
        }
    }



}
