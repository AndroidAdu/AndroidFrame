package com.dxy.library_thread.down;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by duxueyang on 2019/4/25.
 * 下载管理类
 */
public class DownloadMananger implements DownloadManagerImpl {


    //下载任务集合
    private Vector<DownloadTask> downloadVector = new Vector<>();
    // 线程池
    private DownloadThreadPool threadPool = new DownloadThreadPool();

    private DownHandler downHandler;


    public static DownloadMananger getInstance() {

        return DownloadManangerInstance.INSTANCE;

    }


    public DownloadMananger() {
        downHandler = new DownHandler(DownloadMananger.this);
    }


    @Override
    public boolean addTask(DownloadTask task) {
        if (task == null)
            return false;
        Iterator<DownloadTask> iter = downloadVector.iterator();
        while (iter.hasNext()) {
            if (task.equal(iter.next()))
                return false;
        }
        if (task.isIdle())
            downloadVector.add(task);

        return true;
    }


    @Override
    public boolean cancelTask(DownloadTask task) {
        if (task != null) {
            threadPool.pause(task);
            downloadVector.remove(task);
            task.delDownloadFile();
            return true;
        }
        return false;
    }

    @Override
    public void pauseAllTask() {
        Iterator<DownloadTask> iter = downloadVector.iterator();
        while (iter.hasNext())
            threadPool.pause(iter.next());
        threadPool.waitAllThreadOver();

    }

    @Override
    public void resumeAllTask() {


    }

    /**
     * taskStatus
     */
    @Override
    public void startAllTask(int taskStatus) {
        int launchTaskCount = 0;
        Iterator<DownloadTask> iter = downloadVector.iterator();
        while (iter.hasNext()) {
            DownloadTask task = iter.next();
            if (task != null&&()&&threadPool.launchTask(task, downHandler)) {
                launchTaskCount++;
                try {
                    //当前线程
                    Thread.currentThread().sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startAllTask() {


    }

    @Override
    public void clearAllFinishedTask() {

    }

    //实例化DownloadMananger
    private static class DownloadManangerInstance {
        private static final DownloadMananger INSTANCE = new DownloadMananger();
    }

    private static class DownHandler extends Handler {

        private WeakReference<DownloadMananger> downloadWeakReference;

        public DownHandler(DownloadMananger downloadMananger) {
            downloadWeakReference = new WeakReference<>(downloadMananger);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (downloadWeakReference.get() != null) {

            }
        }


    }


}
