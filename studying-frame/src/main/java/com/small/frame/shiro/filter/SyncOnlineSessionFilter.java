package com.small.frame.shiro.filter;

import com.small.frame.shiro.session.StudyingSessionDao;
import com.small.frame.shiro.session.domain.OnlineSession;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 异步将Session信息放入到数据库中
 * 此过滤器放在OnlineSessionFilter后面
 * @author Liang
 */
public class SyncOnlineSessionFilter extends AccessControlFilter {

    private StudyingSessionDao studyingSessionDao;

    public void setStudyingSessionDao(StudyingSessionDao studyingSessionDao) {
        this.studyingSessionDao = studyingSessionDao;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        /**
         * subject.getSession(false);
         */
        OnlineSession session = (OnlineSession)studyingSessionDao.readSession(subject.getSession().getId());
        if (session!=null&&session.getUserId()!=null&&session.getUserId()!=0&&session.getStopTimestamp()==null){
            studyingSessionDao.syncToDb(session);
        }
        return true;
    }

    /**
     * 在这个filter不需要这个方法
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
