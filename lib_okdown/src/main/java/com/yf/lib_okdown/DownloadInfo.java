package com.yf.lib_okdown;

/**
 * @Description: 描述
 * @author; yyf
 * @CreateDate:2019/4/11 11:29
 */
public class DownloadInfo {
    private String url;  //网址
    private String folder; //保存文件夹
    private String file_path; //保存文件地址
    private String file_name; //保存的文件名
    private long current_size; //下载的大小
    private long total_size;  //总长度
    private int status; //下载状态
    private long downloadId; //下载的任务Id

    public long getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(long downloadId) {
        this.downloadId = downloadId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public long getCurrent_size() {
        return current_size;
    }

    public void setCurrent_size(long current_size) {
        this.current_size = current_size;
    }

    public long getTotal_size() {
        return total_size;
    }

    public void setTotal_size(long total_size) {
        this.total_size = total_size;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
