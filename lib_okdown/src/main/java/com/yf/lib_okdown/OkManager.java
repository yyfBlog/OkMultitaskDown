package com.yf.lib_okdown;

import android.util.Log;

import com.yf.lib_okdown.request.GetRequest;
import com.yf.lib_okdown.request.PostJsonRequest;

import java.util.Map;

import okhttp3.OkHttpClient;
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

    public OkHttpClient getClient() {
        return client;
    }

    /**
     * post 请求
     */
    public RequestBuilder postJson() {
        PostJsonRequest postJsonRequest = new PostJsonRequest();
        return postJsonRequest;
    }

    /**
     * get 请求
     */
    public GetRequest get() {
        GetRequest getRequest = new GetRequest();
        return getRequest;
    }


}
