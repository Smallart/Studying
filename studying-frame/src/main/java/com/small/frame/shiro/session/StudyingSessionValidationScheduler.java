package com.small.frame.shiro.session;

import com.small.common.utils.ThreadsUtils;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Shiro提供会话验证调度器，用于定期的验证会话是否已经过期，若干过期则停止会话。
 * 在web环境中，如果用户不主动退出是不知道会话是否过期的，所以需要定期的检测会话是否过期
 * @author ruoyi
 */
@Component
public class StudyingSessionValidationScheduler implements SessionValidationScheduler {

    private static final Logger log = LoggerFactory.getLogger(StudyingSessionValidationScheduler.class);

    @Autowired
    @Qualifier("studyingScheduledExecutorService")
    private ScheduledExecutorService executorService;

    @Autowired
    @Lazy
    ValidatingSessionManager sessionManager;

    private volatile boolean enabled = false;

    @Value("${shiro.session.validationInterval}")
    private long validationInterval;

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void enableSessionValidation() {
        enabled = true;
        if (log.isDebugEnabled()){
            log.debug("Scheduling session validation job using Spring Scheduler with session validation interval of {} ms",validationInterval);
        }
        try{
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    if (enabled){
                        sessionManager.validateSessions();
                    }
                }
            }, 1000, validationInterval * 60 * 1000, TimeUnit.MILLISECONDS);

            this.enabled = true;
            if (log.isDebugEnabled()){
                log.debug("Session validation job successfully scheduled with spring Scheduler.");
            }
        }catch (Exception e){
            if (log.isDebugEnabled()){
                log.error("Error starting the Spring Scheduler session validation job. session validation may not occur",e);
            }
        }
    }

    @Override
    public void disableSessionValidation() {
        if (log.isDebugEnabled()){
            log.debug("Stopping Spring Scheduler session validation job");
        }
        if (this.enabled){
            ThreadsUtils.shutdownAndAwaitTermination(executorService);
        }
        this.enabled = false;
    }
}
