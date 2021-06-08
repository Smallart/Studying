package com.small.frame.shiro.filter;

import com.small.common.constant.ShiroConstants;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 验证码过滤器
 * @author Liang
 */
public class CaptchaFilter extends AuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String captcha = request.getParameter("captcha");
        HttpServletRequest req = (HttpServletRequest) request;
        if (captcha!=null||"".equals(captcha)){
            if (!CaptchaUtil.ver(captcha,req)){
                CaptchaUtil.clear(req);
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setAttribute(ShiroConstants.CURRENT_CAPTCHA.getMsg(),ShiroConstants.CAPTCHA_ERROR.getMsg());
        return true;
    }
}
