package com.yf.lib_okdown.callback;


import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2020/8/27 8:24
 */
public abstract class FileCallBack implements BaseCallBack {
    private static final String TAG = "FileCallBack";
    private long mTotalLength;
    private long mAlreadyDownLength;
    private String mPath;

    public FileCallBack(String mPath) {
        this.mPath = mPath;
    }

    @Override
    public void analysisResponse(Response response) {
        RandomAccessFile randomAccessFile = null;
        InputStream inputStream = null;
        try {
            ResponseBody responseBody = response.body();
            //得到输入流
            inputStream = responseBody.byteStream();
            //得到任意保存文件处理类实例
            randomAccessFile = new RandomAccessFile(mPath + "/" + getHeaderFileName(response), "rw");
            if (mTotalLength == 0) {
                //得到文件的总字节大小
                mTotalLength = responseBody.contentLength();
                //预设创建一个总字节的占位文件
                randomAccessFile.setLength(mTotalLength);
            }
            if (mAlreadyDownLength != 0) {
                randomAccessFile.seek(mAlreadyDownLength);
            }
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                randomAccessFile.write(bytes, 0, len);
                mAlreadyDownLength = mAlreadyDownLength + len;
                onProgress(mTotalLength,mAlreadyDownLength);
            }
            if (mTotalLength==mAlreadyDownLength){
                onSuccess("下载成功");
            }
        } catch (Exception e) {
            onFail("下载异常",e);
        } finally {
            try {
                //记录当前保存文件的位置
                mAlreadyDownLength = randomAccessFile.getFilePointer();
                randomAccessFile.close();
                inputStream.close();
                Log.e(TAG, "流关闭 下载的位置=" + mAlreadyDownLength);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析文件头
     * Content-Disposition:attachment;filename=FileName.txt
     * Content-Disposition: attachment; filename*="UTF-8''%E6%9B%BF%E6%8D%A2%E5%AE%9E%E9%AA%8C%E6%8A%A5%E5%91%8A.pdf"
     */
    private String getHeaderFileName(Response response) {
        String dispositionHeader = response.header("Content-Disposition");
        if (!TextUtils.isEmpty(dispositionHeader)) {
            dispositionHeader.replace("attachment;filename=", "");
            dispositionHeader.replace("filename*=utf-8", "");
            String[] strings = dispositionHeader.split(";");
            if (strings.length > 1) {
                dispositionHeader = strings[1].replace("filename=", "");
                dispositionHeader = dispositionHeader.replace("\"", "");
                return dispositionHeader;
            }
            return "";
        }
        return "";
    }

    /**
     * 下载进度
     */
    public abstract void onProgress(long totalLength,long alreadyDownLength);
}
