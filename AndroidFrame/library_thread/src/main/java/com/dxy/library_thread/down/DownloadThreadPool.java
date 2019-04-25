package com.dxy.library_thread.down;

import android.os.Handler;

/**
 * Created by duxueyang on 2019/4/24.
 * 自定义下载线程池
 *
 * 负责定义线程组， 设置线程组最大线程数
 *
 */
public class DownloadThreadPool {


    //默认最大线程数
    private static final int MAX_THREAD_COUNT = 5;
    //自定义线程数
    private int CUSTOM_THREAD_COUNT;


    //定义线程组
    public ThreadGroup threadGroup = new ThreadGroup("AndroidFrame");


    public DownloadThreadPool() {
        this(MAX_THREAD_COUNT);
    }

    public DownloadThreadPool(int maxThreadCount) {
        CUSTOM_THREAD_COUNT = maxThreadCount;
    }


    /**
     * 启动下载线程，并添加到线程组中
     */
    public boolean launchTask(DownloadTask task, Handler handler) {
        if (task != null && canLaunchNewTask())
            if (task.isIdle() || task.isPause() || task.isError()) {
                DownloadThread thread = new DownloadThread(task, handler, threadGroup);
                thread.start();
                return true;
            }

        return false;
    }

    /**
     * 暂停下载
     */
    public boolean pause(DownloadTask task) {
        if (task == null || !task.isDownloading()) return false;
        task.pause();
        return true;
    }


    /**
     * 等待所有活动线程结束
     */
    public void waitAllThreadOver() {
        Thread[] threads = new Thread[threadGroup.activeCount()];
        //复制线程组到制定的数组 threads
        threadGroup.enumerate(threads);
        for (Thread thread : threads) {
            //线程是否启动
            if (!thread.isAlive())
                try {
                    //优先等待thread线程
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }


    /**
     * 是否能够执行新的下载任务
     */
    private boolean canLaunchNewTask() {
        return threadGroup.activeCount() < CUSTOM_THREAD_COUNT;
    }


}
