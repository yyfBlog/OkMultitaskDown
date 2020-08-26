package com.yf.lib_okdown;

import android.net.Uri;
import android.util.Log;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2020/8/25 16:19
 */
public class RequestBuilder {

    public Request getRequest(String url, Map<String, String> headers, Map<String, String> params) {
        Request.Builder requestBuilder = new Request.Builder();
        if (url == null || params == null || params.isEmpty()) {
            return requestBuilder.url(url).get().build();
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers != null) {
            for (String key : headers.keySet()) {
                headerBuilder.add(key, headers.get(key));
            }
        }
        return requestBuilder.url(builder.build().toString()).get().headers(headerBuilder.build()).build();
    }


    public Request postJsonRequest(String url, Map<String, String> headers, String jsonParams) {
        RequestBody body = RequestBody.Companion.create(jsonParams, DataMediaType.JSON);
        final Request.Builder requestBuilder = new Request.Builder();
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers != null) {
            for (String key : headers.keySet()) {
                headerBuilder.add(key, headers.get(key));
            }
        }
        return requestBuilder.url(url).post(body).headers(headerBuilder.build()).build();
    }
}
