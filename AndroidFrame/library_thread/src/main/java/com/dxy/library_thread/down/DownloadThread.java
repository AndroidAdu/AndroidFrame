package com.dxy.library_thread.down;


import android.os.Handler;

/**
 * Created by duxueyang on 2019/4/24.
 * 下载线程
 */
public class DownloadThread extends Thread {


    public DownloadThread(DownloadTask task, Handler handler, ThreadGroup threadGroup) {
        super(threadGroup, task.getDownloadMessage().getDownloadUrl());
        //初始化下载内容
    }


    @Override
    public void run() {
        //这个里面写下载方法

    }
}
