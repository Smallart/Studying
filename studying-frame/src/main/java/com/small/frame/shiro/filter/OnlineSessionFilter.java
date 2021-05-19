package com.small.frame.shiro.filter;

import com.small.common.base.enitity.SysUser;
import com.small.common.enums.OnlineStatus;
import com.small.common.utils.ShiroUtils;
import com.small.frame.shiro.session.StudyingSessionDao;
import com.small.frame.shiro.session.domain.OnlineSession;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/***
 * onlineSession
 * @author Liang
 */
public class OnlineSessionFilter extends AccessControlFilter {

    private StudyingSessionDao sessionDao;

    public void setSessionDao(StudyingSessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        if (subject==null||subject.getSession(false)==null){
            return true;
        }
        SysUser sysUser = ShiroUtils.getPrincipal();
        if (sysUser.getStatus().equals(OnlineStatus.OFF_LINE.getInfo())){
            return false;
        }
        // 不能直接使用subject.getSesion(false),这是因为其中的session被包装为其它的Session类型
        OnlineSession session =(OnlineSession)sessionDao.readSession(subject.getSession().getId());
        boolean isGuest = session.getUserId()==null||session.getUserId()==0L;
        if (isGuest){
            session.setUserId(sysUser.getUserId());
            session.setLoginName(sysUser.getLoginName());
            session.setAvater(sysUser.getAvatar());
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        ShiroUtils.loginOut();
        saveRequestAndRedirectToLogin(servletRequest, servletResponse);
        return false;
    }
}
