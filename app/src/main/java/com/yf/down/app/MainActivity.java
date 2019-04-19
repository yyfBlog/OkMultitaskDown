package com.yf.down.app;

import android.app.DownloadManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yf.lib_okdown.AndroidDownloadManager;
import com.yf.lib_okdown.DownloadInfo;

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

        AndroidDownloadManager.getInstance().init(this);
//        long downloadId1 = AndroidDownloadManager.getInstance().start(url1, "kBZWxZpZBK");
//        long downloadId2 = AndroidDownloadManager.getInstance().start(url2, "kx2CX0sCOc");
//        long downloadId3 = AndroidDownloadManager.getInstance().start(url3, "kx2EtL4SNq");

        boolean isExits1 = AndroidDownloadManager.getInstance().fileIsExitsSdcard(44);
        Log.d(TAG, "onCreate: " + isExits1);
        boolean isExits2 = AndroidDownloadManager.getInstance().fileIsExitsSdcard(45);
        Log.d(TAG, "onCreate: " + isExits2);
        boolean isExits3 = AndroidDownloadManager.getInstance().fileIsExitsSdcard(46);
        Log.d(TAG, "onCreate: " + isExits3);

        AndroidDownloadManager.getInstance().setIDownloadCallBack(new AndroidDownloadManager.IDownloadCallBack() {
            @Override
            public void callBack(DownloadInfo downloadInfo) {
                switch (downloadInfo.getStatus()) {
                    case DownloadManager.STATUS_SUCCESSFUL://下载成功
                        Log.d(TAG, "callBack: 下载成功" + downloadInfo.getDownloadId());
                        break;
                    case DownloadManager.STATUS_FAILED://下载失败
                        Log.d(TAG, "callBack: 下载失败" + downloadInfo.getDownloadId());
                    case DownloadManager.STATUS_RUNNING://下载中
                        int progress = (int) (((float) downloadInfo.getCurrent_size()) / ((float) downloadInfo.getTotal_size()) * 100);
                        Log.d(TAG, "callBack:下载中 " + progress + "     任务Id" + downloadInfo.getDownloadId());
                        break;
                    case DownloadManager.STATUS_PAUSED://下载停止
                        Log.d(TAG, "downloadStatus: 下载停止" + downloadInfo.getDownloadId());
                        break;
                    case DownloadManager.STATUS_PENDING://准备下载
                        Log.d(TAG, "downloadStatus: 准备下载" + downloadInfo.getDownloadId());
                        break;
                }
            }
        });
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
