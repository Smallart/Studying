package com.small.frame.shiro.session;

import com.small.common.enums.OnlineStatus;
import com.small.frame.manager.AsyncManager;
import com.small.frame.manager.facotry.AsyncFactory;
import com.small.frame.shiro.service.SysShiroService;
import com.small.frame.shiro.session.domain.OnlineSession;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

/**
 * 自定义SessionDao
 * @author ruoyi
 */
public class StudyingSessionDao extends EnterpriseCacheSessionDAO {
    @Autowired
    private SysShiroService sysShiroService;

    /**
     * 定义的session同步到数据库的周期
     */
    @Value("${shiro.session.dbSyncPeriod}")
    private int dbSyncPeriod;

    private static final String LAST_SYNC_DB_TIMESTAMP = StudyingSessionDao.class.getName()+"LAST_SYNC_DB_TIMESTAMP";

    @Override
    protected void doUpdate(Session session) {
        if (session instanceof OnlineSession){
            OnlineSession onlineSession = (OnlineSession) session;
            onlineSession.sessionChanged();
        }
    }

    /**
     * 更新会话，如果更新会话最后访问时间/设置超时时间/移除属性等调用。
     * 为啥不同直接在父类的doUpdate调用，而是要在Filter中调用
     */
    public void syncToDb(OnlineSession onlineSession){
        Date lastSyncTime =(Date)onlineSession.getAttribute(LAST_SYNC_DB_TIMESTAMP);
        if (lastSyncTime!=null){
            boolean needSync = true;

            long dateTime  = onlineSession.getLastAccessTime().getTime() - lastSyncTime.getTime();
            if (dateTime< dbSyncPeriod*60*1000){
                needSync = false;
            }
            // 如何session的attribute改变了
            if (onlineSession.getSessionChange()){
                needSync = true;
            }
            if (!needSync){
                return;
            }
        }
        onlineSession.setAttribute(LAST_SYNC_DB_TIMESTAMP,onlineSession.getLastAccessTime());
        if (onlineSession.getSessionChange()){
            onlineSession.resetSessionStatus();
        }
        AsyncManager.me().execute(AsyncFactory.syncSessionToDb(onlineSession));
    }

    @Override
    protected void doDelete(Session session) {
        if (session==null){
            return;
        }
        OnlineSession onlineSession = (OnlineSession)session;
        onlineSession.setStatus(OnlineStatus.OFF_LINE.getInfo());
        sysShiroService.deleteSession(onlineSession.getId());
    }
}
