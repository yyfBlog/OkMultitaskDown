package com.yf.down.app;

import android.app.Application;

import com.yf.lib_okdown.db.DBHelper;

/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2019/4/11 13:36
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.init(this);
    }
}
