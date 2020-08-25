package com.yf.lib_okdown;

/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2020/8/25 15:45
 */
public interface StringCallBack {
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
}
