package com.dxy.library_thread.down;

/**
 * Created by duxueyang on 2019/4/25.
 */
public interface DownloadManagerImpl {

    //添加任务
    boolean addTask(DownloadTask task);

    //删除任务
    boolean cancelTask(DownloadTask task);

    //暂停所有任务
    void pauseAllTask();

    //恢复所有任务
    void resumeAllTask();

    //开启所有想要启动任务
    int startAllTask(int conformStatus);

    //清除所有下载完成任务
    void clearAllFinishedTask();

    //启动空闲任务
    void ScheduleIdleTask();


}
