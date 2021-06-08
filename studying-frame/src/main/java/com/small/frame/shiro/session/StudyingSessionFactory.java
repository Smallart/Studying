package com.small.frame.shiro.session;

import com.small.frame.shiro.session.domain.OnlineSession;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义Session工厂
 * @author Liang
 */
public class StudyingSessionFactory extends SimpleSessionFactory {
    /**
     * 创建Session
     * @param initData
     * @return
     */
    @Override
    public Session createSession(SessionContext initData) {
        OnlineSession session = new OnlineSession();
        if (initData!=null&&initData instanceof WebSessionContext){
            WebSessionContext sessionContext = (WebSessionContext)initData;
            String agent = ((HttpServletRequest)sessionContext.getServletRequest()).getHeader("User-Agent");
            UserAgent userAgent = UserAgent.parseUserAgentString(agent);
            session.setBrowser(userAgent.getBrowser().getName());
            session.setOs(userAgent.getOperatingSystem().getName());
            session.setHost(initData.getHost());
            session.setStatus("ONLINE");
        }
        return session;
    }
}
