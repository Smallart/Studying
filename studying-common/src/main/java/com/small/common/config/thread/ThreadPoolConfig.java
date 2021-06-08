package com.small.common.config.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * 配置自定义线程池
 * @author ruoyi
 */
@Configuration
public class ThreadPoolConfig {
    /**
     * 核心线程数
     */
    private int corePoolSize = 50;
    /**
     * 最大线程数
     */
    private int maxPoolSize = 200;
    /**
     * 任务队列的最大容量
     */
    private int queueCapacity = 1000;
    /**
     * 线程池维护线程所允许的空闲时间
     * 单位为秒
     */
    private int keepAliceSeconds = 300;

    /**
     * 自定义一个线程池
     * @return
     */
    @Bean(name = "studyingThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliceSeconds);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
     * 执行周期或是定时性任务
     * @return
     */
    @Bean(name = "studyingScheduledExecutorService")
    public ScheduledExecutorService scheduledExecutorService(){
        return new ScheduledThreadPoolExecutor(corePoolSize, Executors.defaultThreadFactory());
    }
}
