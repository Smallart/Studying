package com.small.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 和线程相关的工具类
 * @author ruoyi
 */
public class ThreadsUtils {
    private static final Logger log = LoggerFactory.getLogger(ThreadsUtils.class);

    /**
     * 停止线程
     * 先使用shutdown,停止接收新任务并尝试完成所有已存在任务
     * 如果超时，则调用shutdown,取消在workQueue中的Pending的任务，并且中断所有阻塞函数
     * 如果仍然超时，则强制退出
     * 另外对shutdown时线程本身被调用中断做出了处理
     * @param pool
     */
    public static void shutdownAndAwaitTermination(ExecutorService pool){
        if (pool!=null&&!pool.isShutdown()){
            pool.shutdown();
            try {
                if (!pool.awaitTermination(120, TimeUnit.SECONDS)){
                    pool.shutdown();
                    if (!pool.awaitTermination(120,TimeUnit.SECONDS)){
                        log.info("Pool did not terminate");
                    }
                }
            } catch (InterruptedException e) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    /**
     * 打印线程异常信息
     * @param r
     * @param t
     */
    public static void printException(Runnable r,Throwable t){
        if (t == null && r instanceof Future<?>){
            try{
                Future<?> future = (Future<?>) r;
                if (future.isDone()){
                    future.get();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                t = e.getCause();
            }
        }
        if (t!=null){
            log.error(t.getMessage(),t);
        }
    }
}
