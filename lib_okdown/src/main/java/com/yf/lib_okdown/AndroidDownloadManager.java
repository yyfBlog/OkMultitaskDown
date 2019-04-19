package com.yf.lib_okdown;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AndroidDownloadManager {
    private static final String TAG = "DownloadManager";
    private static AndroidDownloadManager instance;
    private DownloadManager downloadManager;
    private Context mContext;
    private ScheduledExecutorService scheduledExecutorService;
    private DownloadRunnable mDownloadRunnable;
    private HashMap<Long, DownloadRunnable> taskMap;

    public static AndroidDownloadManager getInstance() {
        if (instance == null) {
            synchronized (AndroidDownloadManager.class) {
                if (instance == null) {
                    instance = new AndroidDownloadManager();
                }
            }
        }
        return instance;
    }

    public void init(Context mContext) {
        //1.得到下载对象
        this.mContext = mContext;
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        scheduledExecutorService = Executors.newScheduledThreadPool(3);
        taskMap = new HashMap<>();
    }


    /**
     * 开始下载文件
     *
     * @param url
     * @param fileName
     */
    public long start(String url, String fileName) {
        //2.创建下载请求对象，并且把下载的地址放进去
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //3.给下载的文件指定路径
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        //5更改服务器返回的minetype为android包类型
        request.setMimeType("application/vnd.android.package-archive");
        //6.设置在什么连接状态下执行下载操作
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        //7. 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        //8. 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        long downloadId = downloadManager.enqueue(request);

        Log.d(TAG, "start: " + downloadId);
        mDownloadRunnable = new DownloadRunnable(downloadId);
        scheduledExecutorService.execute(mDownloadRunnable);
        taskMap.put(downloadId, mDownloadRunnable);
        return downloadId;
    }

    /**
     * DownloadManager.STATUS_SUCCESSFUL: //下载成功
     * DownloadManager.STATUS_FAILED://下载失败
     * DownloadManager.STATUS_RUNNING://下载中
     * DownloadManager.STATUS_PAUSED://下载停止
     * DownloadManager.STATUS_PENDING://准备下载
     *
     * @param downId
     */
    private DownloadInfo getDownLoadInfo(long downId) {
        //已经下载文件大小
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downId);
        Cursor cursor = downloadManager.query(query);
        DownloadInfo downloadInfo = new DownloadInfo();
        try {
            if (cursor != null && cursor.moveToNext()) {
                //已经下载文件大小
                int alreadyCount = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                downloadInfo.setCurrent_size(alreadyCount);
                //下载文件的总大小
                int totalCount = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                downloadInfo.setTotal_size(totalCount);
                //下载状态
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                //url
                String uri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                downloadInfo.setFile_path(uri);
                //下载的任务id
                long downloadId = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
                downloadInfo.setDownloadId(downloadId);
                downloadInfo.setStatus(status);
                switch (status) {
                    case DownloadManager.STATUS_SUCCESSFUL://下载成功
                        Log.d(TAG, "downloadStatus: 下载成功");
                        DownloadRunnable targetRunnable = taskMap.get(downloadId);
                        if (targetRunnable != null) {
                            targetRunnable.setStop(true);
                        }
                        break;
                    case DownloadManager.STATUS_FAILED://下载失败
                        Log.d(TAG, "downloadStatus: 下载失败");
                        break;
                    case DownloadManager.STATUS_RUNNING://下载中
                        /**
                         * 计算下载下载率；
                         */
                        int totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        int currentSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        int progress = (int) (((float) currentSize) / ((float) totalSize) * 100);
                        Log.d(TAG, "downloadStatus: 下载中" + progress);
                        break;
                    case DownloadManager.STATUS_PAUSED://下载停止
                        Log.d(TAG, "downloadStatus: 下载停止");
                        break;
                    case DownloadManager.STATUS_PENDING://准备下载
                        Log.d(TAG, "downloadStatus: 准备下载");
                        break;
                }
            }
        } finally {
            cursor.close();
        }
        return downloadInfo;
    }


    /**
     * 移除下载任务
     */
    private void remove(long downloadId) {
        if (downloadManager != null) {
            downloadManager.remove(downloadId);
        }
    }

    /**
     * 监听下载信息
     */
    public class DownloadRunnable implements Runnable {
        private long downloadId;
        private boolean isStop = false;

        public DownloadRunnable(long downloadId) {
            this.downloadId = downloadId;
        }

        public void setStop(boolean stop) {
            isStop = stop;
        }

        @Override
        public void run() {
            while (!isStop) {
                try {
                    DownloadInfo info = getDownLoadInfo(downloadId);
                    if (mIDownloadCallBack != null) {
                        mIDownloadCallBack.callBack(info);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 查询任务是否存在
     */
    public boolean fileIsExitsSdcard(long downId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downId);
        Cursor cursor = downloadManager.query(query);
        try {
            if (cursor != null && cursor.moveToNext()) {
                //下载状态
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                if (status == DownloadManager.STATUS_SUCCESSFUL) {//下载成功
                    return true;
                }
            }
        } finally {
            cursor.close();
        }
        return false;
    }


    /**
     * 任务监听
     */
    public interface IDownloadCallBack {
        void callBack(DownloadInfo downloadInfo);
    }

    private IDownloadCallBack mIDownloadCallBack;

    public void setIDownloadCallBack(IDownloadCallBack callBack) {
        this.mIDownloadCallBack = callBack;
    }
}
