package com.dxy.library_thread.down;

import java.io.File;

/**
 * Created by duxueyang on 2019/4/24.
 * <p>
 * 专注于下载任务
 */
public class DownloadTask {

    /**
     * 下载状态
     */
    // 等待调度
    public static final int RES_STATUS_IDLE = 1;
    // 下载中
    public static final int RES_STATUS_DOWNLOADING = 2;
    // 下载暂停
    public static final int RES_STATUS_PAUSE = 3;
    // 下载完毕
    public static final int RES_STATUS_FINISHED = 4;
    // 下载时发现错误
    public static final int RES_STATUS_ERROR = 5;
    /**
     * 错误代码
     */
    // 无错误
    public static final int ERR_OK = 6;
    // 无效链接错误
    public static final int ERR_DEAD_URL = 7;
    // 网络错误
    public static final int ERR_NET_IO = 8;


    /**
     * 下载相关
     */
    // 下载状态
    public int status;
    //下载进度
    public long progress;
    // 错误代码
    public int errCode;


    /**
     * 下载信息
     */
    private DownloadMessage downloadMessage;

    public DownloadMessage getDownloadMessage() {
        return downloadMessage;
    }

    public DownloadTask(DownloadMessage message){
        this.downloadMessage=message;
    }


    /**
     * 是否为同一任务
     */
    public boolean equal(DownloadTask task){
        return downloadMessage.getDownloadUrl().equals(task.getDownloadMessage().getDownloadUrl());
    }


    /**
     * 是否处于空闲状态
     */
    public synchronized boolean isIdle() {
        return status == RES_STATUS_IDLE;
    }

    /**
     * 是否处于下载状态
     */
    public synchronized boolean isDownloading() {
        return status == RES_STATUS_DOWNLOADING;
    }

    /**
     * 是否可以断点续传
     */
    public synchronized boolean canContinueDownload() {
        return (status == RES_STATUS_PAUSE || status == RES_STATUS_ERROR) &&
                progress > 0 && progress < downloadMessage.getTotalLength();
    }

    /**
     * 是否处于下载暂停状态
     */
    public synchronized boolean isPause() {
        return status == RES_STATUS_PAUSE;
    }


    /**
     * 是否处于下载完毕状态
     */
    public synchronized boolean isFinish() {
        return status == RES_STATUS_FINISHED;
    }

    /**
     * 是否处于错误状态
     */
    public synchronized boolean isError() {
        return status == RES_STATUS_ERROR;
    }


    /**
     * 暂停下载
     */
    public synchronized void pause() {
        if (isDownloading()) status = RES_STATUS_PAUSE;
    }

    /**
     * 恢复下载
     */
    public synchronized void resume() {
        if (isPause()) status = RES_STATUS_DOWNLOADING;
    }

    /**
     * 初始化状态
     */
    public synchronized void idle() {
        status = RES_STATUS_IDLE;
        progress = -1;
        errCode = ERR_OK;
    }

    /**
     * 下载完成
     */
    public synchronized void finish() {
        if (isDownloading()) {
            status = RES_STATUS_FINISHED;
            errCode = ERR_OK;
        }
    }

    /**
     * 下载错误
     */
    public synchronized void fail(int code) {
        status = RES_STATUS_ERROR;
        errCode = code;
    }

    /**
     * 开始下载
     */
    public synchronized void download() {
        switch (status) {
            case RES_STATUS_PAUSE:
            case RES_STATUS_ERROR:
                if (progress < 0 || progress > downloadMessage.getTotalLength())
                    progress = 0;
                status = RES_STATUS_DOWNLOADING;
                break;
            case RES_STATUS_IDLE:
                progress = 0;
                status = RES_STATUS_DOWNLOADING;
                break;
        }
    }


    /**
     * 删除下载文件
     */
    public final boolean delDownloadFile() {
        try {
            File file = new File(isFinish() ? downloadMessage.getSaveLocalUrl() : downloadMessage.getSaveLocalTempUrl());
            if (file.exists())
                file.delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
