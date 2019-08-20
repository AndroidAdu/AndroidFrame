package com.dxy.library_thread.threadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by duxueyang on 2019/7/24.
 * 线程工厂
 */
public class ThreadFactory {

    /**
     * 核心线程池数量
     */
    private static final int CORE_POOL_SIZE = 8;
    /**
     * 最大线程池数量
     */
    private static final int MAXIMUM_POOL_SIZE = 32;
    /**
     * 保持活跃线程(核心线程除外)
     */
    private static final int KEEP_ALIVE = 2;

    /**
     * 链表线程队列
     */
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(64);

    /**
     * 线程池策略 （处理线程池充满后，后续线程的处理）
     * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
     * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
     * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
     * ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
     */
    private static final RejectedExecutionHandler sRejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();


    /**
     *  线程池
     */
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue, sRejectedExecutionHandler);

    /**
     * 获取线程池
     */
    public static ThreadPoolExecutor getMainThreadPool() {
        return threadPool;
    }


}
