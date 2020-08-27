package com.yf.lib_okdown;



import com.yf.lib_okdown.callback.BaseCallBack;

import java.util.Map;


/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2020/8/26 14:07
 */
public abstract class BaseBuilder {
    public abstract BaseBuilder url(String url);

    public abstract BaseBuilder header(Map<String,String> headers);

    public abstract BaseBuilder params(Map<String,String> params);

    public abstract void enqueue(BaseCallBack CallBack);

}
