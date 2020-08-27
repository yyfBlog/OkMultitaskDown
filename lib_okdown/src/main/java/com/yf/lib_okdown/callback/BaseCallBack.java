package com.yf.lib_okdown.callback;

import okhttp3.Response;

/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2020/8/27 8:28
 */
public interface BaseCallBack {
    /**
     * 成功时回调
     * @param msg
     */
     void onSuccess(String msg);

    /**
     * 失败时回调
     * @param e
     */
     void onFail(String msg,Exception e);


    /**
     * 解析response
     */
    void analysisResponse(Response response);
}
