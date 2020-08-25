package com.yf.lib_okdown;

import android.net.Uri;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Request;

/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2020/8/25 16:19
 */
public class RequestBuilder {

    public Request getRequest(String url, Map<String, String> params) {
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
        return requestBuilder.url(builder.build().toString()).get().build();
    }
}
