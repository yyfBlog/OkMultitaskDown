package com.yf.down.app;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yf.lib_okdown.OkManager;
import com.yf.lib_okdown.callback.FileCallBack;
import com.yf.lib_okdown.callback.StringCallBack;


import java.util.HashMap;
import java.util.Map;

/**
 * @author yangyifan
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //21  22
        //掘金下载地址
        String URL_JUEJIN = "https://imtt.dd.qq.com/16891/4611E43165D203CB6A52E65759FE7641.apk?fsname=com.daimajia.gold_5.6.2_196.apk&csr=1bbd";
        String url1 = "http://10.2.161.197:8001/fm/fileDownload/rectByShortCode/kBZWxZpZBK";
        String url2 = "http://www.grgebuy.com:8001/fm/fileDownload/rectByShortCode/kx2CX0sCOc";
        String url3 = "http://www.grgebuy.com:8001/fm/fileDownload/rectByShortCode/kx2EtL4SNq";

//        long downloadId1 = AndroidDownloadManager.getInstance().start(url1, "kBZWxZpZBK");
//        long downloadId2 = AndroidDownloadManager.getInstance().start(url2, "kx2CX0sCOc");
//        long downloadId3 = AndroidDownloadManager.getInstance().start(url3, "kx2EtL4SNq");
        String url="http://dev2.grgfast.com.cn:8001/eqcloud/polling/getTs";
        Map<String,String> params=new HashMap<>();
        params.put("key","bbb");
        Map<String,String> header=new HashMap<>();
        header.put("token","ccc");

        Log.d(TAG, "onCreate: "+Environment.getExternalStorageDirectory().getPath());
        OkManager.getInstance().get().url(url2).enqueue(new FileCallBack(Environment.getExternalStorageDirectory().getPath()) {
            @Override
            public void onProgress(long totalLength, long alreadyDownLength) {
                Log.d(TAG, "onProgress: totalLength:"+totalLength+"   alreadyDownLength:"+alreadyDownLength);
            }

            @Override
            public void onSuccess(String msg) {
                Log.d(TAG, "onSuccess: "+msg);
            }

            @Override
            public void onFail(String msg, Exception e) {
                Log.d(TAG, "onFail: "+msg);
            }

        });

//        OkManager.getInstance().postJson().url(url).header(header).params(params).enqueue(new StringCallBack() {
//            @Override
//            public void onSuccess(String msg) {
//                Log.d(TAG, "onSuccess: "+msg);
//            }
//
//            @Override
//            public void onFail(String msg, Exception e) {
//                Log.d(TAG, "onFail: "+msg);
//            }
//        });

//       OkManager.getInstance().get().url(url2).header(header).params(params).enqueue(new StringCallBack() {
//           @Override
//           public void onSuccess(String msg) {
//               Log.d(TAG, "onSuccess: "+msg);
//           }
//
//           @Override
//           public void onFail(String msg, Exception e) {
//               Log.d(TAG, "onFail: "+msg);
//           }
//       });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
