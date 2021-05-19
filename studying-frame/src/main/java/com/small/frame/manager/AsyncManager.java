package com.small.frame.manager;

import com.small.common.utils.SpringUtils;
import com.small.common.utils.ThreadsUtils;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务管理器
 * @author ruoyi
 */
public class AsyncManager {
    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 在非spring容器的环境中获得bean对象
     */
    private ScheduledExecutorService executorService = SpringUtils.getBean("studyingScheduledExecutorService");

    private AsyncManager(){}

    private static AsyncManager me = new AsyncManager();

    /**
     * 单例模式 返回单例对象
     * @return
     */
    public static AsyncManager me(){
        return me;
    }

    /**
     * 执行任务
     * @param task
     */
    public void execute(TimerTask task){
        executorService.schedule(task,OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止任务线程池
     */
    public void shutdown(){
        ThreadsUtils.shutdownAndAwaitTermination(executorService);
    }
}
