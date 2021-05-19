package com.small.frame.shiro.session;

import com.small.common.utils.SpringUtils;
import com.small.system.domain.SysUserOnline;
import com.small.system.query.SysUserOnlineQuery;
import com.small.system.service.ISysUserOnlineService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 自定义SessionManager
 * @author Liang
 */
public class StudyingSessionManager extends DefaultWebSessionManager {
    private static final Logger log = LoggerFactory.getLogger(StudyingSessionManager.class);

    @Override
    public void validateSessions() {
        if (log.isInfoEnabled()){
            log.info("invalidating all active sessions ……");
        }

        int timeout = (int) this.getGlobalSessionTimeout();
        if (timeout<0){
            // 永不过期
            return;
        }
        validateDBSessions(timeout);
        validateCacheSessions();
    }

    /**
     * 处理下数据库中过期session
     * @param timeout
     */
    private void validateDBSessions(int timeout){
        if (log.isInfoEnabled()){
            log.info("check DB session begin...");
        }
        int invalidCount = 0;
        // 把DB的删除一波
        Date expireTime = DateUtils.addMilliseconds(new Date(), 0 - timeout);
        ISysUserOnlineService userOnlineService = SpringUtils.getBean(ISysUserOnlineService.class);
        SysUserOnlineQuery sysUserOnlineQuery = new SysUserOnlineQuery();
        sysUserOnlineQuery.setSearchDate(expireTime);
        List<SysUserOnline> expireUserOnline = userOnlineService.findExpireUserOnline(sysUserOnlineQuery);
        List<String> needOfflineIdList = new ArrayList<String>();
        for (SysUserOnline sysUserOnline : expireUserOnline) {
            DefaultSessionKey key = null;
            Session session = null;
            try{
                key = new DefaultSessionKey(sysUserOnline.getSessionId());
                session = retrieveSession(key);
                if (session!=null){
                    throw new InvalidSessionException();
                }else {
                    // 对于那些各种以外遗留的session进行清除
                    needOfflineIdList.add(sysUserOnline.getSessionId());
                }
            }catch (InvalidSessionException e){
                if (log.isDebugEnabled()){
                    boolean expired = (e instanceof ExpiredSessionException);
                    log.debug("Invalidated session with id {} {}", sysUserOnline.getSessionId(),expired?"expired":"stopped");
                }
                invalidCount++;
                needOfflineIdList.add(sysUserOnline.getSessionId());
                // 会调用父类的方法将缓存中的session进行删除
                validate(session,key);
            }
            try{
                if (needOfflineIdList.size()>0){
                    userOnlineService.batchDeleteOnline(needOfflineIdList);
                }
            }catch (Exception e){
                if (log.isErrorEnabled()){
                    log.error("batch delete db session error.",e);
                }
            }
            if (log.isInfoEnabled()){
                String msg = "Finished DB session validation";
                if (invalidCount>0){
                    msg+="["+invalidCount+"] session were stopped";
                }else{
                    msg+=" No session were stopped";
                }
                log.info(msg);
            }
        }
    }

    /**
     * 处理下Cache中session过期session或是stop的session
     */
    private void validateCacheSessions(){
        if (log.isInfoEnabled()){
            log.info("Validating Cache active sessions");
        }
        super.validateSessions();
    }

}
