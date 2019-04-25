package com.dxy.library_thread.down;

/**
 *
 * Created by duxueyang on 2019/4/24.
 * 下载信息实体类
 *
 */
public class DownloadMessage {


    //网络下载地址
    private String downloadUrl;
    //保存本地的地址
    private String saveLocalUrl;
    //保存本地的临时地址
    private String saveLocalTempUrl;
    //描述
    private String desc;
    //文件总大小
    private long totalLength;





    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getSaveLocalUrl() {
        return saveLocalUrl;
    }

    public void setSaveLocalUrl(String saveLocalUrl) {
        this.saveLocalUrl = saveLocalUrl;
    }

    public String getSaveLocalTempUrl() {
        return saveLocalTempUrl;
    }

    public void setSaveLocalTempUrl(String saveLocalTempUrl) {
        this.saveLocalTempUrl = saveLocalTempUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }
}
