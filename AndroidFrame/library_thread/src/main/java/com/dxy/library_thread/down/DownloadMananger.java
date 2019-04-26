package com.dxy.library_thread.down;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by duxueyang on 2019/4/25.
 * <p>
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


    /**
     *  添加Task
     */
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

    /**
     * 删除任务
     */
    @Override
    public boolean cancelTask(DownloadTask task) {
        if (task == null)
            return false;
        Iterator<DownloadTask> iter = downloadVector.iterator();
        while (iter.hasNext()) {
            if (task.equal(iter.next())) {
                threadPool.pause(task);
                downloadVector.remove(task);
                task.delDownloadFile();
                return true;
            }
        }
        return false;
    }


    /**
     * 暂停所有下载
     */
    @Override
    public void pauseAllTask() {
        Iterator<DownloadTask> iter = downloadVector.iterator();
        while (iter.hasNext())
            threadPool.pause(iter.next());
        threadPool.waitAllThreadOver();

    }


    /**
     * 启动所有下载
     */
    @Override
    public void resumeAllTask() {
        //启动所有暂停,错误,初始化
        int conormStatus = DownloadTask.RES_STATUS_PAUSE | DownloadTask.RES_STATUS_ERROR
                | DownloadTask.RES_STATUS_IDLE;
        startAllTask(conormStatus);
    }

    /**
     * 开始所有下载
     */
    @Override
    public int startAllTask(int conformStatus) {
        int launchTaskCount = 0;
        Iterator<DownloadTask> iter = downloadVector.iterator();
        while (iter.hasNext()) {
            DownloadTask task = iter.next();
            //确认当前Task状态是否符合TaskStatus
            boolean conform = ((conformStatus & task.status) > 0);
            if (conform && threadPool.launchTask(task, downHandler)) {
                launchTaskCount++;
                try {
                    Thread.currentThread().sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return launchTaskCount;
    }


    /**
     * 清除所有已完成的任务
     */
    @Override
    public void clearAllFinishedTask() {
        for (int i = 0; i < downloadVector.size(); ) {
            DownloadTask task = downloadVector.get(i);
            if (task != null && task.isFinish()) {
                downloadVector.remove(i);
            } else {
                ++i;
            }
        }

    }

    /**
     * 打开空闲任务
     */
    @Override
    public void ScheduleIdleTask() {
        downHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int conformStatus = DownloadTask.RES_STATUS_IDLE;
                startAllTask(conformStatus);
            }
        }, 1000);
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
