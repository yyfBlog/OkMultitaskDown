package com.yf.lib_okdown;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2020/8/25 8:07
 */
public class OkManager {
    private static final String TAG = "OkManager";
    private static OkManager instance;
    private OkHttpClient.Builder builder;
    private OkHttpClient client;

    public static OkManager getInstance() {
        if (instance == null) {
            synchronized (OkManager.class) {
                if (instance == null) {
                    instance = new OkManager();
                }
            }
        }
        return instance;
    }

    private OkManager() {
        builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, "log: " + message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        client = builder.build();
    }

    /**
     * post 请求
     */
    public void postJson(String url,Map<String, String> headers, String paramsJson, final StringCallBack callBack) {
        RequestBuilder postJsonRequest=new RequestBuilder();
        Call call = client.newCall(postJsonRequest.postJsonRequest(url,headers,paramsJson));
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (callBack != null) {
                    callBack.onFail("网络异常", e);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body().toString());
                } else {
                    callBack.onFail("网络异常", null);
                }
            }
        });
    }

    /**
     * get 请求
     */
    public void get(String url, Map<String, String> headers, Map<String, String> params, final StringCallBack callBack) {
        RequestBuilder request = new RequestBuilder();
        Call call = client.newCall(request.getRequest(url, headers, params));
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                if (callBack != null) {
                    callBack.onFail("网络异常", e);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());
                if (response.isSuccessful()) {
                    if (callBack != null) {
                        callBack.onSuccess(response.body().toString());
                    }
                } else {
                    if (callBack != null) {
                        callBack.onFail(response.body().toString(), null);
                    }
                }
            }
        });
    }
}
