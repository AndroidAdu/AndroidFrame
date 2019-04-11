package com.dxy.apk.download;

/**
 * Created by duxueyang on 2019/4/10.
 */
public interface DownLoadAPKListener {

    //不存在sdcard
    void noSDCard();

    //正在下载中
    void updateDownProgress(int progress,String tempFileSize,String targetFileSize);

    //下载完成
    void downloadFinish(String targetUrl);
}
