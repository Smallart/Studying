package com.small.frame.shiro.service;

import com.small.frame.shiro.session.domain.OnlineSession;
import com.small.system.domain.SysUserOnline;
import com.small.system.query.SysUserOnlineQuery;
import com.small.system.service.ISysUserOnlineService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 会话db操作
 * @author ruoyi
 */
@Component
public class SysShiroService {
    @Autowired
    private ISysUserOnlineService onlineService;

    /**
     * 通过SessionId获得session
     * @param sessionId
     * @return
     */
    public Session getSession(Serializable sessionId){
        SysUserOnline sysUserOnline = onlineService.findUserOnlineBySessionId(String.valueOf(sessionId));
        return sysUserOnline == null? null:createSession(sysUserOnline);
    }

    public Session createSession(SysUserOnline online){
        OnlineSession session = new OnlineSession();
        session.setId(online.getSessionId());
        session.setHost(online.getIp());
        session.setBrowser(online.getBrowser());
        session.setOs(online.getOs());
        session.setLoginName(online.getLoginName());
        session.setStartTimestamp(online.getStartTimeStamp());
        session.setLastAccessTime(online.getLastAccessTime());
        session.setTimeout(online.getExpireTime());;
        return session;
    }

    /**
     * 删除会话
     * @param sessionId
     */
    public void deleteSession(Serializable sessionId){
        onlineService.deleteOnlineById(String.valueOf(sessionId));
    }
}
