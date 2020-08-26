package com.yf.lib_okdown.request;

import android.net.Uri;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.yf.lib_okdown.DataMediaType;
import com.yf.lib_okdown.OkManager;
import com.yf.lib_okdown.RequestBuilder;
import com.yf.lib_okdown.StringCallBack;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2020/8/26 14:38
 */
public class PostJsonRequest extends RequestBuilder {

    public PostJsonRequest() {

    }

    @Override
    public Request getRequest(String url, Map<String, String> headers, Map<String, String> params) {
        RequestBody body = RequestBody.Companion.create(JSON.toJSONString(params), DataMediaType.JSON);
        final Request.Builder requestBuilder = new Request.Builder();
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers != null) {
            for (String key : headers.keySet()) {
                headerBuilder.add(key, headers.get(key));
            }
        }
        return requestBuilder.url(url).post(body).headers(headerBuilder.build()).build();
    }

    @Override
    public void enqueue(final StringCallBack stringCallBack) {
        Request request = getRequest(getUrl(), getHeaders(), getParams());
        Call call = OkManager.getInstance().getClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                returnFailure(e, stringCallBack);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                returnResponse(response, stringCallBack);
            }
        });
    }

    private void returnResponse(Response response, StringCallBack callBack) {
        if (callBack != null) {
            if (response.isSuccessful()) {
                callBack.onSuccess(response.body().toString());
            } else {
                callBack.onFail(response.body().toString(), null);
            }
        }
    }

    private void returnFailure(Exception e, StringCallBack callBack) {
        if (callBack != null) {
            callBack.onFail("网络异常", e);
        }
    }
}
