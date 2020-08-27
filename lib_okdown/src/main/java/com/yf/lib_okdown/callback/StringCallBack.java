package com.yf.lib_okdown.callback;


import java.io.IOException;

import okhttp3.Response;

/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2020/8/25 15:45
 */
public abstract class StringCallBack implements BaseCallBack {

    @Override
    public void analysisResponse(Response response) {
        try {
            if (response.isSuccessful()) {
                onSuccess(response.body().string());
            } else {
                onFail(response.body().string(), null);
            }
        } catch (IOException e) {
            onFail("网络异常", e);
        }
    }
}
