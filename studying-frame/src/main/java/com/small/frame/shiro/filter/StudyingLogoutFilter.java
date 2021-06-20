package com.small.frame.shiro.filter;

import com.small.common.base.enitity.SysUser;
import com.small.common.constant.Constants;
import com.small.common.enums.OnlineStatus;
import com.small.common.utils.ShiroUtils;
import com.small.frame.manager.AsyncManager;
import com.small.frame.manager.facotry.AsyncFactory;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Locale;

/**
 * 用户登出
 * @author Liang
 */
public class StudyingLogoutFilter extends LogoutFilter {
    private static final Logger log = LoggerFactory.getLogger(StudyingLogoutFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = this.getSubject(request, response);
        if (this.isPostOnlyLogout() && !WebUtils.toHttp(request).getMethod().toUpperCase(Locale.ENGLISH).equals("POST")) {
            return this.onLogoutRequestNotAPost(request, response);
        } else {
            String redirectUrl = this.getRedirectUrl(request, response, subject);

            try {
                String loginName = ShiroUtils.getLoginName();
                SysUser principal = ShiroUtils.getPrincipal();
                if (principal!=null){
                    principal.setStatus(OnlineStatus.OFF_LINE.getInfo());
                }
                if (loginName!=null){
                    AsyncManager.me().execute(AsyncFactory.syncLoginInfoToDb(loginName, Constants.LOGOUT, ""));
                }
                subject.logout();
            } catch (SessionException var6) {
                log.debug("Encountered session exception during logout.  This can generally safely be ignored.", var6);
            }

            this.issueRedirect(request, response, redirectUrl);
            return false;
        }
    }
}
