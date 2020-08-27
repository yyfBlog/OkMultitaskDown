package com.yf.lib_okdown.request;

import com.alibaba.fastjson.JSON;
import com.yf.lib_okdown.DataMediaType;
import com.yf.lib_okdown.RequestBuilder;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2020/8/26 16:56
 */
public class FileRequest extends RequestBuilder {
    @Override
    public Request getRequest(String url, Map<String, String> headers, Map<String, String> params) {
        RequestBody body = RequestBody.Companion.create(JSON.toJSONString(params), DataMediaType.MEDIA_TYPE_STREAM);
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
