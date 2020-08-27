package com.yf.lib_okdown;

import com.yf.lib_okdown.callback.BaseCallBack;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2020/8/25 16:19
 */
public abstract class RequestBuilder extends BaseBuilder {
    private String url;
    private Map<String, String> headers;
    private Map<String, String> params;

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public BaseBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public BaseBuilder header(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public BaseBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public abstract Request getRequest(String url, Map<String, String> headers, Map<String, String> params);

    @Override
    public void enqueue(final BaseCallBack callBack) {
        Request request = getRequest(getUrl(), getHeaders(), getParams());
        Call call = OkManager.getInstance().getClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                returnFailure(e, callBack);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                returnResponse(response, callBack);
            }
        });
    }


    private void returnResponse(Response response, BaseCallBack callBack) {
        try {
            if (callBack != null) {
                if (response.isSuccessful()) {
                    callBack.analysisResponse(response);
                } else {
                    callBack.onFail(response.body().string(), null);
                }
            }
        } catch (IOException e) {
            returnFailure(e, callBack);
        }
    }

    private void returnFailure(Exception e, BaseCallBack callBack) {
        if (callBack != null) {
            callBack.onFail("网络异常", e);
        }
    }
}
