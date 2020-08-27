package com.yf.lib_okdown.request;

import android.net.Uri;

import com.yf.lib_okdown.RequestBuilder;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Request;

/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2020/8/26 15:21
 */
public class GetRequest extends RequestBuilder {

    @Override
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
}
